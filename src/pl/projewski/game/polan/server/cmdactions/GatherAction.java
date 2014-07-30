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
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.PolanGameServer;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.IWork;
import pl.projewski.game.polan.server.work.WorkGather;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GatherAction extends ACreatureProductAction {

    private static final Log log = LogFactory.getLog(GatherAction.class);

    public GatherAction() {
        super(ActionNames.GATHER);
    }

    @Override
    public IWork createWork(ClientContext ctx, Creature creature, Product productToActOn, int howManyTimes, String productFilter) {
        return new WorkGather(ctx, creature, productToActOn, howManyTimes, productFilter);
    }
}
