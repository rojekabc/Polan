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
    WATER_FIELD(ProductType.FLUID), ICE_FIELD(ProductType.FIELD), SAND_FIELD(ProductType.FIELD), SAND_STONE_FIELD(ProductType.FIELD),
    MUD_FIELD(ProductType.FIELD), STONE_FIELD(ProductType.FIELD), GRANITE_STONE_FIELD(ProductType.FIELD), GROUND_FIELD(ProductType.FIELD),
    /* Trees */
    OAK_TREE(ProductType.TREE), PINE_TREE(ProductType.TREE), BRICH_TREE(ProductType.TREE),
    /* Plants */
    CALAMUS_PLANT(ProductType.PLANT), GRASS_FIELD(ProductType.PLANT), CLOVER_FIELD(ProductType.PLANT),
    /* Simple resource */
    OAK_STICK(ProductType.RESOURCE), PINE_STICK(ProductType.RESOURCE), BRITCH_STICK(ProductType.RESOURCE), GRASS(ProductType.RESOURCE),
    CLOVER(ProductType.RESOURCE);

    ProductType type;
    boolean gatherable = false;
    ProductDefinition[] gatherElements = null;

    static {
        OAK_TREE.gatherable = true;
        OAK_TREE.gatherElements = new ProductDefinition[]{OAK_STICK};
        PINE_TREE.gatherable = true;
        PINE_TREE.gatherElements = new ProductDefinition[]{PINE_STICK};
        BRICH_TREE.gatherable = true;
        BRICH_TREE.gatherElements = new ProductDefinition[]{BRITCH_STICK};
        GRASS_FIELD.gatherable = true;
        GRASS_FIELD.gatherElements = new ProductDefinition[]{GRASS};
        CLOVER_FIELD.gatherable = true;
        CLOVER_FIELD.gatherElements = new ProductDefinition[]{CLOVER};
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
        return gatherable;
    }

    public ProductDefinition[] getGatherElements() {
        return gatherElements;
    }
}
