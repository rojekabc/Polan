/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.server;

import pl.projewski.game.polan.data.UserPrivilages;
import pl.projewski.game.polan.data.NamedCommand;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class Command {
    private ICommandAction action;
    private UserPrivilages privilages;

    public Command(ICommandAction action, UserPrivilages privilages)
    {
        this.action = action;
        this.privilages = privilages;
    }

    public ICommandAction getAction()
    {
        return action;
    }

    public UserPrivilages getPrivilages()
    {
        return privilages;
    }
    
    
}
