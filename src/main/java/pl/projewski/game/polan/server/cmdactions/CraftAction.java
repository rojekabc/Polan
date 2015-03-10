/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.TimeResponse;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;

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
        LOG.info("Product to craft is: " + productToCraft.getName());
        return new TimeResponse(0);
    }

}
