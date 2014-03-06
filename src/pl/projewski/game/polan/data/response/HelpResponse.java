/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data.response;

import pl.projewski.game.polan.data.response.CommandResponse;
import java.util.List;
import pl.projewski.game.polan.data.Cmd;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class HelpResponse extends CommandResponse {
    private List<Cmd> cmdList;

    public HelpResponse(CommandResponseStatus status) {
        super(status);
    }
    
    public HelpResponse(List<Cmd> cmdList)
    {
        super(CommandResponseStatus.OK);
        this.cmdList = cmdList;
    }

    public List<Cmd> getCmdList()
    {
        return cmdList;
    }
    
}
