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
public class ProductDefinition extends BaseDefinition {

    ProductType type = null;
    List<ActionDefinition> actionList = null;
    List<String> possibleExistsOn = null;

    public ProductDefinition(String name, ProductType productType, ActionDefinition[] possibleActionList, String[] possibleExistsOnList) {
        super(name);
        this.type = productType;
        if (possibleActionList != null) {
            actionList = new ArrayList();
            for (ActionDefinition actionDefinition : possibleActionList) {
                actionList.add(actionDefinition);
            }
        }
        if (possibleExistsOnList != null) {
            this.possibleExistsOn = new ArrayList();
            for (String possibleOn : possibleExistsOnList) {
                possibleExistsOn.add(possibleOn);
            }
        }
    }

    public ProductType getType() {
        return type;
    }

    public boolean isActionAble(String actionName) {
        if (actionList == null) {
            return false;
        }
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

    public List<String> getPossibleExistsOn() {
        return this.possibleExistsOn;
    }
}
