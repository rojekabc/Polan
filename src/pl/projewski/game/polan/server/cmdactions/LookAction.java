/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.LookResponse;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class LookAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(final ClientContext ctx, final List<String> props) {
        final User user = ctx.getUser();
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }

        int locationId = -1;
        if (props == null || props.size() < 1) {
            if (user.getSelectedCreature() == User.NO_CREATURE) {
                return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
            }
            Creature creature = world.getCreature(user.getSelectedCreature());
            locationId = creature.getLocationId();
        } else if (props.size() == 1) {
            locationId = Integer.valueOf(props.get(0));
        }
        final Location location = world.getLocation(locationId);
        if (!location.isKnownByUser() || !location.getUsername().equals(user.getName())) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        final LookResponse lookResponse = new LookResponse();
        lookResponse.setLocation(location);
        lookResponse.setHuman(WorldManager.findHumans(world, locationId));
        final List<Long> elements = location.getElements();
        if (elements != null) {
            for (Long id : elements) {
                lookResponse.addProduct(world.getProduct(id));
            }
        }
        final List<Long> resources = location.getResources();
        if (resources != null) {
            for (Long id : resources) {
                lookResponse.addProduct(world.getProduct(id));
            }
        }
        return lookResponse;
    }

}
