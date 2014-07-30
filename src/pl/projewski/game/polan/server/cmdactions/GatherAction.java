/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ClientContext;
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
