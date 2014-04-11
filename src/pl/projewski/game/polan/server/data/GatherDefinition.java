/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
class GatherDefinition {

    ProductDefinition[] gatherResources = null;
    int gatherTime = 1;
    int gatherRenewTime = 1;

    GatherDefinition(int gatherTime, int gatherRenewTime, ProductDefinition... gatherResources) {
        this.gatherTime = gatherTime;
        this.gatherRenewTime = gatherRenewTime;
        this.gatherResources = gatherResources;
    }

    public ProductDefinition[] getGatherResources() {
        return gatherResources;
    }

    public int getGatherTime() {
        return gatherTime;
    }

    public int getGatherRenewTime() {
        return gatherRenewTime;
    }

}
