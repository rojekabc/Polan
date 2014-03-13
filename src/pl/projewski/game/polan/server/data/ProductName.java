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
public enum ProductName {

    /* Fields - ground field is just empty ground. Nothing is growing there */
    GRASS_FIELD, CLOVER_FIELD, WATER_FIELD, ICE_FIELD, SAND_FIELD, SAND_STONE_FIELD, MUD_FIELD, STONE_FIELD, GRANITE_STONE_FIELD, GROUND_FIELD,
    /* Trees */
    OAK_TREE, PINE_TREE, BRICH_TREE,
    /* Plants */
    CALAMUS_PLANT;

    public String getName() {
        return this.name().toLowerCase().replace('_', ' ');
    }

}
