/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import com.sun.istack.internal.logging.Logger;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.ServerLog;
import pl.projewski.game.polan.server.cmdactions.GatherAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkGather extends AWorkerWork {

    Product gatherOnProduct;
    // counter > 0 - planned number of things to gather
    // counter == 0 - no more things to gather
    // counter < 0 - neverending work (until break by another work)
    int counter;

    public WorkGather(ClientContext ctx, Creature worker, Product gatherOnProduct, int numberToGather) {
        super(ctx, ServerData.getInstance().getProductDefinition(gatherOnProduct.getName()).getGatherTime(), worker);
        this.gatherOnProduct = gatherOnProduct;
        this.counter = numberToGather;
    }

    @Override
    public void initWork() {
        gatherOnProduct.setGatherLock(true);
        getWorker().setWorkName(WorkNames.GATHERING);
    }

    @Override
    public boolean doPlannedWork(World world) {
        // get location
        int locationId = getWorker().getLocationId();
        Location location = world.getLocation(locationId);

        if (gatherOnProduct != null) {
            // append gathered resource to location
            ProductDefinition productDef = ServerData.getInstance().getProductDefinition(gatherOnProduct.getName());
            ProductDefinition[] gatherResources = productDef.getGatherResources();
            for (ProductDefinition gatherResouurce : gatherResources) {
                location.addResource(WorldManager.generateProcudt(world, gatherResouurce));
            }
            // start renew process
            WorldManager.addWork(world, new WorkRenewGather(context, gatherOnProduct));
            if (context == null) {
                Logger.getLogger(this.getClass()).warning("Context is null");
                return false;
            }
            context.sendToClient(ServerLog.info(world.getWorldTime(), "Gather on " + gatherOnProduct.getName() + " [" + gatherOnProduct.getId() + "]"));
            if (counter > 0) {
                counter--;
            }
        }

        if (counter != 0) {
            // try find something to do
            gatherOnProduct = GatherAction.findProductToGatherOn(world, location, gatherOnProduct);
            if (gatherOnProduct != null) {
                // find something new - let's work
                ProductDefinition productDef = ServerData.getInstance().getProductDefinition(gatherOnProduct.getName());
                initWork();
                this.ticks = productDef.getGatherTime();
            } else {
                // try to find on next tick
                this.ticks = 1;
            }

            return false;
        } else {
            // there's nothing more to do
            getWorker().setWorkName(WorkNames.NONE);
            return true;
        }
    }

}
