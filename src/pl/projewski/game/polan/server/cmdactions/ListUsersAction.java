/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.ListUsersResponse;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.UserManagerFactory;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ListUsersAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        return new ListUsersResponse(UserManagerFactory.getUserManager().listUsers());
    }

}
