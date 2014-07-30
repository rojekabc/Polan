/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.response.TimeResponse;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.PolanGameServer;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.WorkGather;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GatherAction extends ACreatureProductAction {

    private static final Log log = LogFactory.getLog(GatherAction.class);

    public boolean checkProductToActOn(Product product) {
        final ProductDefinition productDefinition = ServerData.getInstance().getProductDefinition(product.getName());
        if (productDefinition == null) {
            log.warn("Cannot find product definition [" + product.getName() + "]");
            return false;
        }
        if (!productDefinition.isGatherable()) {
            return false;
        }
        if (product.isGatherLock()) {
            return false;
        }
        return true;
    }

    public static Product findProductToGatherOn(final World world, final Location location, Product lastGatheredProduct, String productFilter) {
        return new GatherAction().findProductToActOn(world, location, lastGatheredProduct, productFilter);
    }
}
