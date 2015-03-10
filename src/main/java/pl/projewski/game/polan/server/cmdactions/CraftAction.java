/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.TimeResponse;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.definition.ActionDefinition;
import pl.projewski.game.polan.server.data.definition.InputResourceDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.WorkCraft;

/**
 *
 * @author piotrek
 */
public class CraftAction extends ACreatureAction {

    private static final Log LOG = LogFactory.getLog(CraftAction.class);

    public CraftAction() {
        super(ActionNames.CRAFT);
    }

    @Override
    public CommandResponse runCommand() {
        // check product we want to craft
        if (productFilter == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        List<ProductDefinition> productDefinitions = ServerData.getInstance().getProductDefinitionsByFilter(
                productFilter, ActionNames.CRAFT);
        if (productDefinitions == null || productDefinitions.isEmpty()) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        ProductDefinition productToCraft = productDefinitions.get(0);
        LOG.debug("Product to craft is: " + productToCraft.getName());
        // check there are resources to do this action
        ActionDefinition action = productToCraft.getAction(ActionNames.CRAFT);
        List<InputResourceDefinition> inputResourcesList = new ArrayList(action.getInputResources());
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
                    break;
                }
            }
            if (inputResourceNames.isEmpty()) {
                // start craft work
                WorldManager.addWork(world, new WorkCraft(ctx, creature, productToCraft, action));
                return new TimeResponse(action.getTime());
            }
        }
        // cannot start work, there's not enough resources
        return new CommandResponse(CommandResponseStatus.ERROR_NO_WORK_POSSIBLE);
    }

}
