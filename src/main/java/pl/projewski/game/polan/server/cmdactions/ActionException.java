/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import pl.projewski.game.polan.data.response.CommandResponseStatus;

/**
 *
 * @author piotrek
 */
public class ActionException extends Exception {
    private CommandResponseStatus status;
    
    ActionException(final CommandResponseStatus status) {
        this.status = status;
    }

    public CommandResponseStatus getStatus() {
        return status;
    }
}
