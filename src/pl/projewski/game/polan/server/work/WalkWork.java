/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.work;

import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.ServerLog;
import pl.projewski.game.polan.server.data.ClientContext;
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
public class WalkWork extends AWorkerWork {

    private Location destination;

    public WalkWork(ClientContext context, Creature creature, Location destinationLocation) {
        // TODO: More depened and planned walk / explore time
        super(context, destinationLocation.isKnownByUser() ? 20 : 40, creature);
        this.destination = destinationLocation;
    }

    @Override
    public boolean doPlannedWork(World world) {
        User user = UserManagerFactory.getUserManager().getUser(getWorker().getUserName());
        if (!destination.isKnownByUser()) {
            context.sendToClient(ServerLog.info(world.getWorldTime(), "Explore new location " + destination.getId()));
            // Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Explore new location " + destination.getId());
            WorldManager.exploreNewLocation(world, destination, false, user);
        }
        context.sendToClient(ServerLog.info(world.getWorldTime(), "Move creature to new location " + destination.getId()));
        // Logger.getLogger(this.getClass()).info("[" + world.getWorldTime() + "] Move creature to new location " + destination.getId());
        getWorker().setLocationId(destination.getId());
        getWorker().setWorkName(WorkNames.NONE);
        return true;
    }

    @Override
    public void initWork() {
        if (destination.isKnownByUser()) {
            getWorker().setWorkName(WorkNames.WALK);
        } else {
            getWorker().setWorkName(WorkNames.EXPLORE);
        }

    }

}
