/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import java.util.Set;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.WalkWork;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WalkAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        if (props == null || props.size() != 1) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
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
        String arg = props.get(0);
        int destinationLocationId;
        try {
            destinationLocationId = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        boolean hasConnection = false;
        Set<Integer> connections = location.getConnection();
        if (connections != null) {
            for (Integer connectionId : connections) {
                if (connectionId == destinationLocationId) {
                    hasConnection = true;
                    break;
                }
            }
        } else {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }

        if (!hasConnection) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }
        Location destinationLocation = world.getLocation(destinationLocationId);
        if (destinationLocation == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }
        WorldManager.addWork(world, new WalkWork(creature, destinationLocation));
        return new CommandResponse(CommandResponseStatus.OK);
    }

}
