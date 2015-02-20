/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class CreateWorldAction implements ICommandAction {

    // <worldname>
    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        if (props == null || props.size() < 1) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        if (WorldManager.createNewWorld(props.get(0)) != null) {
            return new CommandResponse(CommandResponseStatus.OK);
        } else {
            return new CommandResponse(CommandResponseStatus.ERROR);
        }
    }

}
