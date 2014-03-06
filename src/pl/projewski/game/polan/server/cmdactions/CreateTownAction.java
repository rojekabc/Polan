/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.UserManagerFactory;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class CreateTownAction implements ICommandAction {
    // <worldname> <username>

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        if (props == null || props.size() < 2) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        final String worldName = props.get(0);
        if (worldName == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        final World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        final User user = UserManagerFactory.getUserManager().getUser(props.get(1));
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        final Location town = WorldManager.createTown(world, user);
        if (town != null) {
            return new CommandResponse(CommandResponseStatus.OK);
        } else {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
    }

}
