/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data.response;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class CommandResponse extends ServerData {
    private CommandResponseStatus status;

    public CommandResponse(CommandResponseStatus status)
    {
        super(ServerDataType.COMMAND_RESPONSE);
        this.status = status;
    }

    public CommandResponseStatus getStatus()
    {
        return status;
    }
    
}
