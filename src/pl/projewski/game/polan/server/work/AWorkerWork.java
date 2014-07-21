/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.server.data.ClientContext;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public abstract class AWorkerWork extends AWork {

    private Creature worker;

    public AWorkerWork(ClientContext ctx, int numOfTicks, Creature worker) {
        super(ctx, numOfTicks);
        this.worker = worker;
    }

    public Creature getWorker() {
        return this.worker;
    }

}
