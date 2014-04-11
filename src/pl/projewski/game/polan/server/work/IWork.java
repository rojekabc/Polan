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
public interface IWork {

    /**
     * called when planned work is finished
     */
    public void finishWork();

    /**
     * decrease a number of ticks for work and return true if after decrease we get a value 0 or less.
     *
     * @param ticks number of ticks to decrease
     * @return true if 0 or less after decrease
     */
    public boolean decreaseNumberOfTick(int ticks);
}
