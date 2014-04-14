/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Product;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkRenewGather extends AWork {

    Product productToRenewGather;

    public WorkRenewGather(int ticks, Product productToRenewGather) {
        super(ticks);
        this.productToRenewGather = productToRenewGather;
    }

    @Override
    public void finishWork() {
        productToRenewGather.setGatherLock(false);
    }

}
