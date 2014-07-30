/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.ServerLog;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.World;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkRenewGather extends AWork {

    Product productToRenewGather;

    public WorkRenewGather(ClientContext ctx, Product productToRenewGather) {
        super(ctx, ServerData.getInstance().getProductDefinition(productToRenewGather.getName()).getAction(ActionNames.RENEW).getTime());
        this.productToRenewGather = productToRenewGather;
    }

    @Override
    public void initWork() {
    }

    @Override
    public boolean doPlannedWork(World world) {
        productToRenewGather.setLocked(false);
        context.sendToClient(ServerLog.info(world.getWorldTime(), "Renew gather on " + productToRenewGather.getName()));
        return true;
    }

}
