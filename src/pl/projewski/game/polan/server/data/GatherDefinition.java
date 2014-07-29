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
public class GatherDefinition {

    String[] gatherResources = null;
    int gatherTime = 1;
    int gatherRenewTime = 1;

    public GatherDefinition(int gatherTime, int gatherRenewTime, String... gatherResources) {
        this.gatherTime = gatherTime;
        this.gatherRenewTime = gatherRenewTime;
        this.gatherResources = gatherResources;
    }

    public ProductDefinition[] getGatherResources() {
        ProductDefinition[] result = new ProductDefinition[gatherResources.length];
        for (int i = 0; i < gatherResources.length; i++) {
            result[i] = ServerData.getInstance().getProductDefinition(gatherResources[i]);
        }
        return result;
    }

    public int getGatherTime() {
        return gatherTime;
    }

    public int getGatherRenewTime() {
        return gatherRenewTime;
    }

}
