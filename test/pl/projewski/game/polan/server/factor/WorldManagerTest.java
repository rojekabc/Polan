/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.projewski.game.polan.client.cmd.presenter.ResponsePresenter;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.LookResponse;
import pl.projewski.game.polan.server.PolanGameServer;
import pl.projewski.game.polan.server.cmdactions.LookAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class WorldManagerTest {

    World world = null;
    User user = null;

    @Before
    public void before() {
        PolanGameServer.loadData();
        world = WorldManager.createNewWorld("test-world");
        user = UserManagerFactory.getUserManager().getUser("user");
        user.setWorldName("test-world");
        WorldManager.createTown(world, user);
        // user.setSelectedCreature(selectedCreature);
    }

    @Test
    public void testExploreNewLocation() {
        int selectedCreature = user.getSelectedCreature();
        Creature creature = world.getCreature(selectedCreature);
        Location location = world.getLocation(creature.getLocationId());
        // Location location = new Location();
        // Assert.assertNotNull(user);
        // WorldManager.exploreNewLocation(world, location, false, user);
        List<Long> elements = location.getElements();
        Assert.assertNotNull(elements);
        for (Long elementId : elements) {
            Product product = world.getProduct(elementId);
            Assert.assertNotNull(product);
            List<Long> elementList = product.getElements();
            if (elementList != null) {
                for (Long id : elementList) {
                    Product p = world.getProduct(id);
                    Assert.assertNotNull(p);
                }
            }
        }

        LookAction action = new LookAction();
        ClientContext clientCtx = new ClientContext();
        clientCtx.setUser(user);
        CommandResponse runCommand = action.runCommand(clientCtx, null);
        ResponsePresenter.presentResponse(runCommand);
    }
}
