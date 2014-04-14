/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import java.util.ArrayList;
import java.util.List;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class World {

    // world name
    private String name;
    // world locations
    private List<Location> locations;
    // world creatures
    private List<Creature> creatures;
    // world id's counters
    private int locationId;
    private int humanId;
    private long productId;
    // world time counter
    private long worldTime;

    public World() {
    }

    public World(String name) {
        this.name = name;
        this.locationId = 0;
        this.humanId = 0;
    }

    public void addLocation(Location location) {
        if (locations == null) {
            locations = new ArrayList();
        }
        locations.add(location);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int generateNewLocationId() {
        return locationId++;
    }

    public int generateNewHumanId() {
        return humanId++;
    }

    public long generateProductId() {
        return productId++;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addCreature(Creature human) {
        if (creatures == null) {
            creatures = new ArrayList();
        }
        creatures.add(human);
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public long getWorldTime() {
        return worldTime;
    }

    public void setWorldTime(long worldTime) {
        this.worldTime = worldTime;
    }

    public void nextWorldTime() {
        this.worldTime++;
    }

    public void addWorldTime(int toAdd) {
        this.worldTime += toAdd;
    }

}
