/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

import java.util.Arrays;
import pl.projewski.game.polan.generator.products.ActionNames;

/**
 *
 * @author piotrek
 */
public class CraftDefinition extends ActionDefinition {

    public CraftDefinition(int actionTime, OutputResourceDefinition produceResource, InputResourceDefinition... inputResources) {
        super(ActionNames.CRAFT, actionTime, Arrays.asList(inputResources), Arrays.asList(produceResource));
    }

}
