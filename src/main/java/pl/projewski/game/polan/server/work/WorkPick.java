/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import java.util.List;
import java.util.Random;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.ServerLog;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.cmdactions.ACreatureProductAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.data.definition.OutputResourceDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorkPick extends AWorkerWork {

    Product pickOnProduct;
    // counter > 0 - planned number of things to gather
    // counter == 0 - no more things to gather
    // counter < 0 - neverending work (until break by another work)
    int counter;
    String filter;

    public WorkPick(ClientContext ctx, Creature worker, Product gatherOnProduct, int numberToGather, String filter) {
        super(ctx, ServerData.getInstance().getProductDefinition(gatherOnProduct.getName()).getAction(ActionNames.PICK).getTime(), worker);
        this.pickOnProduct = gatherOnProduct;
        this.counter = numberToGather;
        this.filter = filter;
    }

    @Override
    public boolean doPlannedWork(World world) {
        // get location
        int locationId = getWorker().getLocationId();
        Location location = world.getLocation(locationId);

        if (pickOnProduct != null) {
            // append gathered resource to location
            ProductDefinition productDef = ServerData.getInstance().getProductDefinition(pickOnProduct.getName());
            List<OutputResourceDefinition> resources = productDef.getAction(ActionNames.PICK).getProduceResources();
            Random random = new Random();
            for (OutputResourceDefinition resource : resources) {
                if (random.nextInt(100) < resource.getPrecentage()) {
                    location.addResource(WorldManager.generateProcudt(world, ServerData.getInstance().getProductDefinition(resource.getName())));
                }
            }
            pickOnProduct.setLocked(false);
            if (context == null) {
                LogFactory.getLog(this.getClass()).warn("Context is null");
                return false;
            }
            context.sendToClient(ServerLog.info(world.getWorldTime(), "Pick on " + pickOnProduct.getName() + " [" + pickOnProduct.getId() + "]"));
            if (counter > 0) {
                counter--;
            }
        }

        if (counter != 0) {
            // try find something to do
            pickOnProduct = ACreatureProductAction.findProductToActOn(ActionNames.PICK, world, location, pickOnProduct, filter);
            if (pickOnProduct != null) {
                // find something new - let's work
                ProductDefinition productDef = ServerData.getInstance().getProductDefinition(pickOnProduct.getName());
                initWork(world);
                this.ticks = productDef.getAction(ActionNames.PICK).getTime();
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

    @Override
    public void initWork(World world) {
        pickOnProduct.setLocked(true);
        getWorker().setWorkName(WorkNames.PICKING);
    }

}
