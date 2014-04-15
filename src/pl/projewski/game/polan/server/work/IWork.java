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
     * Called when planned work is to do.
     *
     * @return true if work has nothing more to do. If there's something more return false - it'll be still at call.
     */
    public boolean doPlannedWork();

    /**
     * decrease a one tick down.
     *
     * @return true if there's something to do
     */
    public boolean decreaseTick();
}
