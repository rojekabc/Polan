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
public class ActionOutResourceDefinition {

    private String productName;
    private int precentage;

    public ActionOutResourceDefinition(String productName, int precentage) {
        this.productName = productName;
        this.precentage = precentage;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrecentage() {
        return precentage;
    }

}
