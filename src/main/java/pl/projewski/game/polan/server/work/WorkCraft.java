/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.ServerLog;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.data.definition.ActionDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.util.ResourceUtil;

/**
 *
 * @author piotrek
 */
public class WorkCraft extends AWorkerWork {

    private static final Log LOG = LogFactory.getLog(WorkCraft.class);

    ProductDefinition productDefinition;
    ActionDefinition craftDefinition;
    List<Product> reservedResources = new ArrayList();

    public WorkCraft(ClientContext ctx, Creature worker, ProductDefinition productDef, ActionDefinition action) {
        super(ctx, productDef.getAction(ActionNames.CRAFT).getTime(), worker);
        this.productDefinition = productDef;
        this.craftDefinition = action;
    }

    public boolean doPlannedWork(World world) {
        Location location = world.getLocation(getWorker().getLocationId());
        if (reservedResources == null) {
            context.sendToClient(ServerLog.info(world.getWorldTime(),
                    MessageFormat.format("Not enough resources to craft product {0}] at location [{1}]",
                            productDefinition.getName(), location.getId())));
            return true;
        }
        // remove reserved products
        for (Product reservedResource : reservedResources) {
            world.removeProduct(reservedResource.getId());
            location.removeResource(reservedResource);
        }
        // generate crafted product
        Product craftedProduct = WorldManager.generateProcudt(world, productDefinition);
        location.addResource(craftedProduct);
        // free worker
        getWorker().setWorkName(WorkNames.NONE);
        // send log information
        context.sendToClient(ServerLog.info(world.getWorldTime(),
                MessageFormat.format("Crafted product {0} [{1}] at location [{2}]",
                        craftedProduct.getName(), craftedProduct.getId(), location.getId())));
        return true;
    }

    @Override
    public void initWork(World world) {
        // put worker in job
        getWorker().setWorkName(WorkNames.CRAFTING);
        // lock resources for work
        Location location = world.getLocation(getWorker().getLocationId());

        reservedResources = ResourceUtil.findResourcesOnLocation(world, location, craftDefinition.getInputResources(), true);

        if (reservedResources == null) {
            LOG.error("Cannot start work. There're no resources. It shouldn't happend !");
        }
    }

    @Override
    public void breakWork(World world) {
        super.breakWork(world);
        // free reserved resources (unlock)
        for (Product reservedResource : reservedResources) {
            reservedResource.setLocked(false);
        }
    }

}
