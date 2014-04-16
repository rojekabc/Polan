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
import pl.projewski.game.polan.server.cmdactions.GatherAction;
import pl.projewski.game.polan.server.data.ProductDefinition;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkGather extends AWork {

    Creature worker;
    Product gatherOnProduct;
    // counter > 0 - planned number of things to gather
    // counter == 0 - no more things to gather
    // counter < 0 - neverending work (until break by another work)
    int counter;

    public WorkGather(Creature worker, Product gatherOnProduct, int numberToGather) {
        super(ProductDefinition.getFromName(gatherOnProduct.getName()).getGatherTime());
        this.worker = worker;
        this.gatherOnProduct = gatherOnProduct;
        this.counter = numberToGather;
    }

    @Override
    public void initWork() {
        gatherOnProduct.setGatherLock(true);
        worker.setWorkName(WorkNames.GATHERING);
    }

    @Override
    public boolean doPlannedWork(World world) {
        // get location
        int locationId = worker.getLocationId();
        Location location = world.getLocation(locationId);

        if (gatherOnProduct != null) {
            // append gathered resource to location
            ProductDefinition productDef = ProductDefinition.getFromName(gatherOnProduct.getName());
            ProductDefinition[] gatherResources = productDef.getGatherResources();
            for (ProductDefinition gatherResouurce : gatherResources) {
                location.addResource(WorldManager.generateProcudt(world, gatherResouurce));
            }
            // start renew process
            WorldManager.addWork(world, new WorkRenewGather(gatherOnProduct));
            Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Gather");
            if (counter > 0) {
                counter--;
            }
        }

        if (counter != 0) {
            // try find something to do
            gatherOnProduct = GatherAction.findProductToGatherOn(world, location);
            if (gatherOnProduct != null) {
                // find something new - let's work
                ProductDefinition productDef = ProductDefinition.getFromName(gatherOnProduct.getName());
                initWork();
                this.ticks = productDef.getGatherTime();
            } else {
                // try to find on next tick
                this.ticks = 1;
            }

            return false;
        } else {
            // there's nothing more to do
            worker.setWorkName(WorkNames.NONE);
            return true;
        }
    }

}
