/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import java.util.List;
import java.util.Set;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.factor.UserManagerFactory;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author wro00541
 */
public class SaveData {

    private List<User> users;
    private Set<World> worlds;

    public void prepareData() {
        users = UserManagerFactory.getUserManager().listUsers();
        worlds = WorldManager.listWorlds();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<World> getWorlds() {
        return worlds;
    }

    public void setWorlds(Set<World> worlds) {
        this.worlds = worlds;
    }

}
