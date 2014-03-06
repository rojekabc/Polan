/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
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
        if (props == null || props.size() < 1) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        int locationId = Integer.valueOf(props.get(0));
        final User user = ctx.getUser();
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        Location location = WorldManager.getLocation(world, locationId);
        if (!location.isKnownByUser() || !location.getUsername().equals(user.getName())) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        final LookResponse lookResponse = new LookResponse();
        lookResponse.setLocation(location);
        lookResponse.setHuman(WorldManager.findHumans(world, locationId));
        return lookResponse;
    }

}
