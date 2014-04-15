/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public abstract class AWork implements IWork {

    protected int ticks;

    protected AWork(int numOfTicks) {
        this.ticks = numOfTicks;
    }

    @Override
    public boolean decreaseTick() {
        ticks--;
        return ticks <= 0;
    }

}
