/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
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

    public WorkGather(int ticks, World world, Creature worker, Product gatherOnProduct) {
        super(ticks);
        this.world = world;
        this.worker = worker;
        this.gatherOnProduct = gatherOnProduct;
    }

    @Override
    public void finishWork() {
        // get location
        int locationId = worker.getLocationId();
        Location location = WorldManager.getLocation(world, locationId);
        // append gathered resource to location
        ProductDefinition productDef = ProductDefinition.getFromName(gatherOnProduct.getName());
        ProductDefinition[] gatherResources = productDef.getGatherResources();
        for (ProductDefinition gatherResouurce : gatherResources) {
            location.addResource(WorldManager.generateProcudt(world, gatherResouurce));
        }
        // start renew process
        WorldManager.addWork(world, new WorkRenewGather(productDef.getGatherRenewTime()));
    }

}
