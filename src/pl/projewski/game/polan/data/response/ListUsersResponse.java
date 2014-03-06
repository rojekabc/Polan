/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data.response;

import pl.projewski.game.polan.data.response.CommandResponse;
import java.util.ArrayList;
import java.util.List;
import pl.projewski.game.polan.data.User;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ListUsersResponse extends CommandResponse {
    private List<User> usersList;
    public ListUsersResponse(final List<User> users) {
        super(CommandResponseStatus.OK);
        this.usersList = users;
    }
    
    public void addUser(User user) {
        if ( user == null )
        {
            return;
        }
        if ( usersList == null )
        {
            usersList = new ArrayList();
        }
        usersList.add(user);
    }

    public List<User> getUsersList()
    {
        return usersList;
    }
    
}
