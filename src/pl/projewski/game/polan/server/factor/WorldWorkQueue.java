/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import java.util.ArrayList;
import java.util.List;
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
     * Do planned works, which are in range of selected number of ticks.
     *
     * @param numberOfTicks
     */
    public void doPlanedWorks(int numberOfTicks) {
        if (works.isEmpty()) {
            return;
        }
        List<IWork> todo = new ArrayList();
        for (IWork work : works) {

        }
        IWork work = works.get(0);
        work.finishWork();
        works.remove(0);
    }

}
