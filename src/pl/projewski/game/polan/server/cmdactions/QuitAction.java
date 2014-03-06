/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import java.util.Properties;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.ClientContext;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class QuitAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(final ClientContext ctx, final List<String> props) {
        return new CommandResponse(CommandResponseStatus.OK);
    }

}
