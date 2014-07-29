/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

/**
 *
 * It's rather product definition.
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ProductDefinition {

    ProductType type;
    String name;
    GatherDefinition gather = null;

//    static {
//        PolanServerConfiguration.defineProducts();
//    }
    public ProductDefinition(String name, ProductType productType) {
        this.type = productType;
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
        // return this.name().toLowerCase().replace('_', ' ');
    }

//    public static ProductDefinition getFromName(String name) {
//        return ProductDefinition.valueOf(name.toUpperCase().replace(' ', '_'));
//    }
    public boolean isGatherable() {
        return gather != null;
    }

    public ProductDefinition[] getGatherResources() {
        if (gather == null) {
            return null;
        }
        return gather.getGatherResources();
    }

    public int getGatherTime() {
        if (gather == null) {
            return -1;
        }
        return gather.getGatherTime();
    }

    public int getGatherRenewTime() {
        if (gather == null) {
            return -1;
        }
        return gather.getGatherRenewTime();
    }

    public void setGather(GatherDefinition gather) {
        this.gather = gather;
    }
}
