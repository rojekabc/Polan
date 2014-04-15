/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import com.sun.istack.internal.logging.Logger;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.server.data.World;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkRenewGather extends AWork {

    Product productToRenewGather;
    World world;

    public WorkRenewGather(int ticks, World world, Product productToRenewGather) {
        super(ticks);
        this.productToRenewGather = productToRenewGather;
        this.world = world;
    }

    @Override
    public boolean doPlannedWork() {
        productToRenewGather.setGatherLock(false);
        Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Renew gather");
        return true;
    }

}
