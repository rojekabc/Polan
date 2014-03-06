/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.HelpResponse;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.CommandManager;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class HelpAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(final ClientContext ctx, final List<String> props) {
        List<Cmd> commandList = CommandManager.getInstance().listCommandsForUser(ctx.getUser());
        return new HelpResponse(commandList);
    }

}
