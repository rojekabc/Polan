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
public enum ProductName {

    /* Fields - ground field is just empty ground. Nothing is growing there */
    WATER_FIELD(ProductType.FLUID), ICE_FIELD(ProductType.FIELD), SAND_FIELD(ProductType.FIELD), SAND_STONE_FIELD(ProductType.FIELD),
    MUD_FIELD(ProductType.FIELD), STONE_FIELD(ProductType.FIELD), GRANITE_STONE_FIELD(ProductType.FIELD), GROUND_FIELD(ProductType.FIELD),
    /* Trees */
    OAK_TREE(ProductType.TREE), PINE_TREE(ProductType.TREE), BRICH_TREE(ProductType.TREE),
    /* Plants */
    CALAMUS_PLANT(ProductType.PLANT), GRASS_FIELD(ProductType.PLANT), CLOVER_FIELD(ProductType.PLANT),
    /* Simple resource */
    OAK_STICK(ProductType.RESOURCE), PINE_STICK(ProductType.RESOURCE), BRITCH_STICK(ProductType.RESOURCE);

    ProductType type;

    ProductName(ProductType productType) {
        this.type = productType;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return this.name().toLowerCase().replace('_', ' ');
    }

}
