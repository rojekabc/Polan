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
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkGather extends AWork {

    World world; // TODO : in some super class as context
    Creature worker;
    Product gatherOnProduct;
    int counter;

    public WorkGather(int ticks, World world, Creature worker, Product gatherOnProduct, int numberToGather) {
        super(ticks);
        this.world = world;
        this.worker = worker;
        this.gatherOnProduct = gatherOnProduct;
        this.counter = numberToGather;
    }

    @Override
    public boolean doPlannedWork() {
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
            WorldManager.addWork(world, new WorkRenewGather(productDef.getGatherRenewTime(), world, gatherOnProduct));
            Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Gather");
            counter--;
        }

        if (counter > 0) {
            // try find something to do
            gatherOnProduct = GatherAction.findProductToGatherOn(world, location);
            if (gatherOnProduct != null) {
                // find something new - let's work
                ProductDefinition productDef = ProductDefinition.getFromName(gatherOnProduct.getName());
                gatherOnProduct.setGatherLock(true);
                this.ticks = productDef.getGatherTime();
            } else {
                // try to find on next tick
                this.ticks = 1;
            }

            return false;
        } else {
            // there's nothing more to do
            return true;
        }
    }

}
