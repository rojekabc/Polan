/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

import java.util.List;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ActionDefinition extends BaseDefinition {

    List<OutputResourceDefinition> outputResources;
    List<InputResourceDefinition> inputResources;

    int time;

    public ActionDefinition(String name, int actionTime,
            List<InputResourceDefinition> inputResources,
            List<OutputResourceDefinition> outputResources) {
        super(name);
        this.outputResources = outputResources;
        this.inputResources = inputResources;
        this.time = actionTime;
    }

    public List<OutputResourceDefinition> getProduceResources() {
        return outputResources;
    }

    public int getTime() {
        return time;
    }

    public List<InputResourceDefinition> getInputResources() {
        return inputResources;
    }

}
