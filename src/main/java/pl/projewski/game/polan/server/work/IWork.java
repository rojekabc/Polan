/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.server.data.World;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public interface IWork {

    /**
     * Called when planned work is to do.
     *
     * @param world world on which work will be done
     * @return true if work has nothing more to do. If there's something more return false - it'll be still at call.
     */
    public boolean doPlannedWork(World world);

    /**
     * decrease a one tick down.
     *
     * @return true if there's something to do
     */
    public boolean decreaseTick();

    /**
     * Initialize settings for a work to do. It it's called when work is added to pool. It doesn't do any work. In
     * mostly cases it should be used to reserve resources need to do planned work.
     *
     * @param world world on which work will be done
     */
    public void initWork(World world);

    /**
     * Break work before finish.
     */
    public void breakWork(World world);
}
