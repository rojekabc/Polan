/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.TimeResponse;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.PolanGameServer;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.WorkGather;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GatherAction implements ICommandAction {

    private static final Log log = LogFactory.getLog(GatherAction.class);

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        final User user = ctx.getUser();
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        if (user.getSelectedCreature() == User.NO_CREATURE) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        Creature creature = world.getCreature(user.getSelectedCreature());
        if (creature == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        if (!creature.getUserName().equals(user.getName())) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        int locationId = creature.getLocationId();
        Location location = world.getLocation(locationId);
        if (location == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }

        int howManyTimes = 1;
        String productFilter = null;
        if (props != null && !props.isEmpty()) {
            // get how many times
            String arg = props.get(0);
            try {
                howManyTimes = Integer.parseInt(arg);
                if (howManyTimes <= 0) {
                    howManyTimes = 1;
                }
            } catch (NumberFormatException e) {
                if (arg.equals("*")) {
                    // do neverending work of gathering all you can find
                    howManyTimes = -1;
                }
            }
            // get filter on names
            for (int i = 1; i < props.size(); i++) {
                if (productFilter != null) {
                    productFilter += " ";
                }
                productFilter += props.get(i);
            }
        }

        Product productToGather = findProductToGatherOn(world, location, null, productFilter);
        if (productToGather == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_WORK_POSSIBLE);
        }
        final ProductDefinition productDefinition = ServerData.getInstance().getProductDefinition(productToGather.getName());
        WorldManager.addWork(world, new WorkGather(ctx, creature, productToGather, howManyTimes, productFilter));
        return new TimeResponse(productDefinition.getGatherTime());
    }

    private static boolean checkProductToGatherOn(Product product) {

        final ProductDefinition productDefinition = ServerData.getInstance().getProductDefinition(product.getName());
        if (productDefinition == null) {
            log.warn("Cannot find product definition [" + product.getName() + "]");
            return false;
        }
        if (productDefinition.isGatherable()) {
            if (product.isGatherLock()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static Product findProductToGatherOn(final World world, final Location location, Product lastGatheredProduct, String productFilter) {
        Product result = null;
        List<Long> elements = location.getElements();

        // find begin from last one
        Iterator<Long> iterator = elements.iterator();
        if (lastGatheredProduct != null) {
            while (iterator.hasNext()) {
                if (lastGatheredProduct.getId() == iterator.next()) {
                    break;
                }
            }
        }
        // find from last one to end
        while (iterator.hasNext()) {
            Long id = iterator.next();
            Product product = world.getProduct(id);
            if (checkProductToGatherOn(product)) {
                return product;
            }

        }
        // find from begin to last one
        if (lastGatheredProduct != null) {
            iterator = elements.iterator();
            while (iterator.hasNext()) {
                Long id = iterator.next();
                if (lastGatheredProduct.getId() == id) {
                    break;
                }
                Product product = world.getProduct(id);
                if (checkProductToGatherOn(product)) {
                    return product;
                }
            }
        }

        return result;
    }

}
