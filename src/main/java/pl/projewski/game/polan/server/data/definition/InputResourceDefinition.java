/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data.definition;

/**
 *
 * @author piotrek
 */
public class InputResourceDefinition extends BaseDefinition {

    private int ammount;

    public InputResourceDefinition(String productName, int ammount) {
        super(productName);
        this.ammount = ammount;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

}
