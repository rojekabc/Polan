/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.server.factor;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class UserManagerFactory {
    private static IUserManager userManager = null;
    public static IUserManager getUserManager() {
        if (userManager == null) {
            userManager = new SimpleUserManager();
        }
        return userManager;
    }
}
