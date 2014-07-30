/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.generator.products.ActionNames;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.work.IWork;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class PickAction extends ACreatureProductAction {

    private static final Log log = LogFactory.getLog(GatherAction.class);

    public PickAction() {
        super(ActionNames.PICK);
    }

    @Override
    public IWork createWork(ClientContext ctx, Creature creature, Product productToActOn, int howManyTimes, String productFilter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
