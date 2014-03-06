/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class NamedCommand {
    private Cmd cmd;
    private List<String> parameters;
    
    public NamedCommand(Cmd name) {
        this.cmd = name;
    }

    public Cmd getCmd()
    {
        return cmd;
    }

    public void setCmd(Cmd name)
    {
        this.cmd = name;
    }

    public List<String> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<String> parameters)
    {
        this.parameters = parameters;
    }
    
    public void addParameter(String parameter) {
        if ( parameter == null || parameter.isEmpty() )
            return;
        if ( this.parameters == null )
            this.parameters = new ArrayList();
        this.parameters.add(parameter);
    }
    
}
