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
import pl.projewski.game.polan.server.data.definition.InputResourceDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author piotrek
 */
public class WorkCraft extends AWorkerWork {

    private static final Log LOG = LogFactory.getLog(WorkCraft.class);

    ProductDefinition productDefinition;
    ActionDefinition craftDefinition;
    List<Long> reservedResources = new ArrayList();

    public WorkCraft(ClientContext ctx, Creature worker, ProductDefinition productDef, ActionDefinition action) {
        super(ctx, productDef.getAction(ActionNames.CRAFT).getTime(), worker);
        this.productDefinition = productDef;
        this.craftDefinition = action;
    }

    public boolean doPlannedWork(World world) {
        Location location = world.getLocation(getWorker().getLocationId());
        // remove reserved products
        for (Long reservedResource : reservedResources) {
            world.removeProduct(reservedResource);
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
        List<InputResourceDefinition> inputResourcesList = new ArrayList(craftDefinition.getInputResources());
        List<String> inputResourceNames = new ArrayList();
        for (InputResourceDefinition inputResource : inputResourcesList) {
            int ammount = inputResource.getAmmount();
            String productName = inputResource.getProductName();
            while (ammount-- > 0) {
                inputResourceNames.add(productName);
            }
        }
        List<Long> resources = location.getResources();
        for (Long resourceId : resources) {
            Product product = world.getProduct(resourceId);
            if (product.isLocked()) {
                continue;
            }
            for (String inputResource : inputResourceNames) {
                if (inputResource.equals(product.getName())) {
                    inputResourceNames.remove(inputResource);
                    product.setLocked(true);
                    reservedResources.add(resourceId);
                    break;
                }
            }
            if (inputResourceNames.isEmpty()) {
                break;
            }
        }
        if (inputResourceNames.isEmpty() == false) {
            LOG.error("Cannot start work. There're no resources. It shouldn't happend !");
        }
    }

    @Override
    public void breakWork(World world) {
        super.breakWork(world);
        // free reserved resources (unlock)
        for (Long reservedResource : reservedResources) {
            Product product = world.getProduct(reservedResource);
            product.setLocked(false);
        }
    }

}
