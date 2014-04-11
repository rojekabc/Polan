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

    // TODO: Use Queue element, not a list
    private List<IWork> works = new ArrayList();

    void addWork(IWork work) {
        works.add(work);
    }

    // TODO: It's generally nothing now
    // - only do first work from a list
    public void doPlanedWorks() {
        if (works.isEmpty()) {
            return;
        }
        IWork work = works.get(0);
        work.finishWork();
        works.remove(0);
    }

}
