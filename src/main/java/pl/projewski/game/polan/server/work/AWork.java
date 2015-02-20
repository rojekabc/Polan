/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.server.data.ClientContext;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public abstract class AWork implements IWork {

    protected int ticks;
    protected ClientContext context;

    protected AWork(ClientContext ctx, int numOfTicks) {
        this.ticks = numOfTicks;
        this.context = ctx;
    }

    @Override
    public boolean decreaseTick() {
        ticks--;
        return ticks <= 0;
    }

    @Override
    public void breakWork() {
    }

}
