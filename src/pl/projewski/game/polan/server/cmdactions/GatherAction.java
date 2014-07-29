/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

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
import pl.projewski.game.polan.server.data.ProductDefinition;
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
        Product productToGather = findProductToGatherOn(world, location, null);
        if (productToGather == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_WORK_POSSIBLE);
        }
        int howManyTimes = 1;
        if (props != null && !props.isEmpty()) {
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
        }
        final ProductDefinition productDefinition = ServerData.getInstance().getProductDefinition(productToGather.getName());
        WorldManager.addWork(world, new WorkGather(ctx, creature, productToGather, howManyTimes));
        return new TimeResponse(productDefinition.getGatherTime());
    }

    public static Product findProductToGatherOn(final World world, final Location location, Product lastGatheredProduct) {
        Product result = null;
        List<Long> elements = location.getElements();

        for (Long productId : elements) {
            if (lastGatheredProduct != null) {
                if (lastGatheredProduct.getId() == productId) {
                    lastGatheredProduct = null;
                }
                continue;
            }
            Product product = world.getProduct(productId);
            final ProductDefinition productDefinition = ServerData.getInstance().getProductDefinition(product.getName());
            if (productDefinition == null) {
                log.warn("Cannot find product definition [" + product.getName() + "]");
                break;
            }
            if (productDefinition.isGatherable()) {
                if (product.isGatherLock()) {
                    continue;
                }
                result = product;
                break;
            }
        }
        return result;
    }

}
