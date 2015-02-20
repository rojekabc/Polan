/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class BiomeDefinition extends BaseDefinition {

    private int randomWeight;
    private List<BiomeElementDefinition> biomeElementRandomWeight;

    public BiomeDefinition(String name, int randomWeight, BiomeElementDefinition... elements) {
        super(name);
        this.randomWeight = randomWeight;
        if (elements != null && elements.length > 0) {
            this.biomeElementRandomWeight = new ArrayList();
            for (BiomeElementDefinition biomeElementDefinition : elements) {
                this.biomeElementRandomWeight.add(biomeElementDefinition);
            }
        }
    }

    public int getWeight() {
        return this.randomWeight;
    }

    public List<BiomeElementDefinition> getBiomeElements() {
        return this.biomeElementRandomWeight;
    }

}
