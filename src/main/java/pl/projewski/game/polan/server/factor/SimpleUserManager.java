/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.UserPrivilages;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class SimpleUserManager implements IUserManager {

    final List<User> users = new ArrayList();

    public SimpleUserManager() {
    }

    @Override
    public User loginUser(String user, String pass) {
        if (user == null) {
            return null;
        }
        for (User u : users) {
            if (u.getName().equals(user)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public UserPrivilages getUserPrivilages(String user) {
        for (User u : users) {
            if (u.getName().equals(user)) {
                return u.getPrivilages();
            }
        }
        return UserPrivilages.EVERYONE;
    }

    @Override
    public List<User> listUsers() {
        return users;
    }

    public void addUsers(Collection<User> users) {
        if (users == null) {
            return;
        }
        this.users.addAll(users);
    }

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public User getUser(String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
