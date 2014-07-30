/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

import pl.projewski.game.polan.server.data.ServerData;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ActionDefinition {

    String name;
    ActionOutResourceDefinition[] resources;
    int time;
    int renewTime;

    public ActionDefinition(String name, int actionTime, ActionOutResourceDefinition... produceResources) {
        this.name = name;
        this.resources = produceResources;
        this.time = actionTime;
        this.renewTime = renewTime;
    }

    public ActionOutResourceDefinition[] getProduceResources() {
        return resources;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

}
