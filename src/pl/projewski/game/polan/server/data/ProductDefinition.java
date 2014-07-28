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
public enum ProductDefinition {

    /* Fields - ground field is just empty ground. Nothing is growing there */
    WATER_FIELD(ProductType.FIELD), ICE_FIELD(ProductType.FIELD), SAND_FIELD(ProductType.FIELD), SAND_STONE_FIELD(ProductType.FIELD),
    MUD_FIELD(ProductType.FIELD), STONE_FIELD(ProductType.FIELD), GRANITE_STONE_FIELD(ProductType.FIELD), GROUND_FIELD(ProductType.FIELD),
    GRAVEL_FIELD(ProductType.FIELD), CLAY_FIELD(ProductType.FIELD),
    /* Trees */
    OAK_TREE(ProductType.TREE), PINE_TREE(ProductType.TREE), BRICH_TREE(ProductType.TREE),
    /*
     Plants
     Calamus - Tatarak
     Clover - Koniczyna
     */
    CALAMUS_PLANT(ProductType.PLANT), GRASS_FIELD(ProductType.PLANT), CLOVER_FIELD(ProductType.PLANT),
    /* Simple resource */
    OAK_STICK(ProductType.RESOURCE), PINE_STICK(ProductType.RESOURCE), BRITCH_STICK(ProductType.RESOURCE), GRASS(ProductType.RESOURCE),
    CLOVER(ProductType.RESOURCE), CLAY(ProductType.RESOURCE), GRAVEL(ProductType.RESOURCE), FLINT(ProductType.RESOURCE), SAND(ProductType.RESOURCE),
    /* Stones */
    STONE(ProductType.STONE), GRANITE(ProductType.STONE), SANDSTONE(ProductType.STONE),
    /* Fluids */
    WATER(ProductType.FLUID);

    ProductType type;
    GatherDefinition gather = null;

    static {
        PolanServerConfiguration.defineProducts();
    }

    ProductDefinition(ProductType productType) {
        this.type = productType;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return this.name().toLowerCase().replace('_', ' ');
    }

    public static ProductDefinition getFromName(String name) {
        return ProductDefinition.valueOf(name.toUpperCase().replace(' ', '_'));
    }

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
}
