/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.response;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public enum CommandResponseStatus {

    OK,
    ERROR,
    ERROR_WRONG_ARGUMENTS,
    ERROR_UNKNOWN_WORLD,
    ERROR_UNKNOWN_LOCATION,
    ERROR_UNKNOWN_USER,
    ERROR_NO_RIGHTS;

}
