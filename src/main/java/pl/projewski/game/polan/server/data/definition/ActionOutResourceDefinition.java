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
public class ActionOutResourceDefinition extends BaseDefinition {

    private int precentage;

    public ActionOutResourceDefinition(String productName, int precentage) {
        super(productName);
        this.precentage = precentage;
    }

    public int getPrecentage() {
        return precentage;
    }

}
