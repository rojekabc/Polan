/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public enum Product {

    MILK, GRASSFIELD, CLOVERFIELD, OAKTREE, PINETREE, BRICHTREE, STONEFIELD, SANDFIELD, WATERFIELD, MUDFIELD, GRANITEFIELD, ICEFIELD, SANDSTONE;
    // let say that 1 it's equivalent of 100g
    private int unitCapacity;
    private MatterState matterState;
}
