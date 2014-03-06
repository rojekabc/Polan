/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import java.util.Collection;
import pl.projewski.game.polan.server.data.World;
import java.util.List;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.UserPrivilages;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public interface IUserManager {

    public User loginUser(String user, String pass);

    public UserPrivilages getUserPrivilages(String user);

    public List<User> listUsers();

    public void addUsers(Collection<User> users);

    public void addUser(User user);

    public User getUser(String userName);
}
