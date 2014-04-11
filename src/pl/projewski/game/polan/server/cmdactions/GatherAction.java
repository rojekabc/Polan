/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.TimeResponse;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.ProductDefinition;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.WorkGather;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GatherAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        // TODO: Gather with seperated options (select elements which we want gather) (select how many times we want do gather)
        // FIXME: Currently just only gather first of element, which find
        // No argument - just gather random element one time
        // argument one as number - gather random element one time
        // argument one as string - gather selected element one time
        // argument as string:number - gather selected element x numbers
        // if selected element cannot be gathered it's omited
        // if as argument is * than creature will collect without end (* or string:*)
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
        Creature creature = WorldManager.getCreature(world, user.getSelectedCreature());
        if (creature == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        if (!creature.getUserName().equals(user.getName())) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        int locationId = creature.getLocationId();
        Location location = WorldManager.getLocation(world, locationId);
        if (location == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }
        Product productToGather = null;
        List<Product> elements = location.getElements();
        for (Product product : elements) {
            ProductDefinition productDefinition = ProductDefinition.getFromName(product.getName());
            if (productDefinition.isGatherable()) {
                // TODO: Check gather timer, that product is possible to gather.
                // FIXME: Currently just gather and select as gathered
                String gatherTimer = product.getGatherTimer();
                if (gatherTimer == null) {
                    productToGather = product;
                    break;
                }
            }
        }
        if (productToGather == null) {
            // TODO: if worker has still work to do than set up a little wait time to next try of gather
            return new TimeResponse(0);
        }
        // TODO:
        //  - worker - set action and timer for gathering
        //  - product - set gather timer, which should say when it'll renew
        //  - after gathering action worker should put element in some container
        // Currently only lock, that it was gathered
        productToGather.setGatherTimer(1);
        WorldManager.addWork(world, new WorkGather(world, creature, productToGather));
        return new TimeResponse(0);
    }

}
