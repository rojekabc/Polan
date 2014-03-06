/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.client.cmd.presenter;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.ListUsersResponse;
import pl.projewski.game.polan.data.User;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ListUsersResponsePresenter extends ResponsePresenter {

    @Override
    public void present(CommandResponse response)
    {
        final ListUsersResponse listUsersResponse = (ListUsersResponse) response;
        final List<User> usersList = listUsersResponse.getUsersList();
        for (User user : usersList)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("    ");
            sb.append(user.getName());
            sb.append(" : ");
            sb.append(user.getPrivilages().name());
            System.out.println(sb.toString());
        }
    }

}
