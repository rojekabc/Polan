/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.UserManagerFactory;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class LoginAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(final ClientContext ctx, final List<String> props) {
        // TODO: check user login / password
        // TODO: set corresponding user privilages
        if (props == null || props.isEmpty()) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        final String username = props.size() >= 1 ? props.get(0) : null;
        final String userpass = props.size() >= 2 ? props.get(1) : null;
        User u = UserManagerFactory.getUserManager().loginUser(username, userpass);
        if (u != null) {
            ctx.setUser(u);
            return new CommandResponse(CommandResponseStatus.OK);
        } else {
            return new CommandResponse(CommandResponseStatus.ERROR);
        }
    }

}
