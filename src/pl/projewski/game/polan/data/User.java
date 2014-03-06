/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class User {

    public final static int NO_CREATURE = -1;

    private String name;
    private UserPrivilages privilages;
    // current / last user world
    private String worldName;
    // selected creature
    private int selectedCreature = NO_CREATURE;

    public User() {
        this.name = null;
        this.privilages = UserPrivilages.EVERYONE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPrivilages getPrivilages() {
        return privilages;
    }

    public void setPrivilages(UserPrivilages privilages) {
        this.privilages = privilages;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getSelectedCreature() {
        return selectedCreature;
    }

    public void setSelectedCreature(int selectedCreature) {
        this.selectedCreature = selectedCreature;
    }

}
