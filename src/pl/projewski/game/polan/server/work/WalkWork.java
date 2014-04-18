/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import com.sun.istack.internal.logging.Logger;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.WorkNames;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.UserManagerFactory;
import pl.projewski.game.polan.server.factor.WorldManager;
import pl.projewski.game.polan.server.work.IWork;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WalkWork extends AWork {

    private Location destination;
    private Creature creature;

    public WalkWork(Creature creature, Location destinationLocation) {
        // TODO: More depened and planned walk / explore time
        super(destinationLocation.isKnownByUser() ? 20 : 40);
        this.creature = creature;
        this.destination = destinationLocation;
    }

    @Override
    public boolean doPlannedWork(World world) {
        User user = UserManagerFactory.getUserManager().getUser(creature.getUserName());
        if (!destination.isKnownByUser()) {
            Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Explore new location " + destination.getId());
            WorldManager.exploreNewLocation(world, destination, false, user);
        }
        Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Move creature to new location " + destination.getId());
        creature.setLocationId(destination.getId());
        creature.setWorkName(WorkNames.NONE);
        return true;
    }

    @Override
    public void initWork() {
        if (destination.isKnownByUser()) {
            creature.setWorkName(WorkNames.WALK);
        } else {
            creature.setWorkName(WorkNames.EXPLORE);
        }

    }

}
