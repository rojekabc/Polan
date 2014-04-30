/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import java.util.ArrayList;
import java.util.List;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.work.IWork;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorldWorkQueue {

    // As an array list - this allow to keep order of incoming works to do
    private List<IWork> works = new ArrayList();

    void addWork(IWork work) {
        works.add(work);
    }

    /**
     * Do planned works, next tick is done.
     *
     * @param numberOfTicks
     */
    public void doPlanedWorks(World world) {
        if (works.isEmpty()) {
            return;
        }
        List<IWork> todo = new ArrayList();
        for (IWork work : works) {
            if (work.decreaseTick()) {
                todo.add(work);
            }
        }
        for (IWork work : todo) {
            if (work.doPlannedWork(world)) {
                works.remove(work);
            }
        }
    }

}
