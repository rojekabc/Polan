/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class BiomeElementDefinition extends BaseDefinition {

    private int weight;

    public BiomeElementDefinition(String name, int weight) {
        super(name);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
