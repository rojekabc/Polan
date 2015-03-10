/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;

/**
 *
 * @author piotrek
 */
public class WorkCraft extends AWorkerWork {
    private WorkCraft(ClientContext ctx, Creature worker, ProductDefinition productDef) {
        super(ctx, productDef.getAction(ActionNames.CRAFT).getTime(), worker);
    }

    public WorkCraft(ClientContext ctx, Creature worker, String productName) {
        this(ctx, worker, ServerData.getInstance().getProductDefinition(productName));
    }

    @Override
    public boolean doPlannedWork(World world) {
        return true;
    }

    @Override
    public void initWork() {
        // put worker in job
        getWorker().setWorkName(WorkNames.CRAFTING);
    }
    
}
