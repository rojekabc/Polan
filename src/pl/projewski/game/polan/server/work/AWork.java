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

    private int ticks;

    protected AWork(int numOfTicks) {
        this.ticks = numOfTicks;
    }

    @Override
    public boolean decreaseNumberOfTick(int decTicks) {
        if (decTicks >= ticks) {
            ticks = 0;
            return true;
        } else {
            ticks -= decTicks;
            return false;
        }
    }

}
