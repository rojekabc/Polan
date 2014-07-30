/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

import java.util.ArrayList;
import java.util.List;
import pl.projewski.game.polan.server.data.ProductType;

/**
 *
 * It's rather product definition.
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ProductDefinition {

    ProductType type = null;
    String name = null;
    List<ActionDefinition> actionList = null;

    public ProductDefinition(String name, ProductType productType, ActionDefinition... possibleActionList) {
        this.type = productType;
        this.name = name;
        actionList = new ArrayList();
        for (ActionDefinition actionDefinition : possibleActionList) {
            actionList.add(actionDefinition);
        }
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isActionAble(String actionName) {
        for (ActionDefinition actionDefinition : actionList) {
            if (actionDefinition.getName().equals(actionName)) {
                return true;
            }
        }
        return false;
    }

    public ActionDefinition getAction(String actionName) {
        for (ActionDefinition actionDefinition : actionList) {
            if (actionDefinition.getName().equals(actionName)) {
                return actionDefinition;
            }
        }
        return null;
    }
}
