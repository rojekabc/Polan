/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class World {

    // world name
    private String name;
    // world locations
    private Map<Integer, Location> locations;
    // world creatures
    private Map<Integer, Creature> creatures;
    // world products
    private Map<Long, Product> products;
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
            locations = new HashMap();
        }
        locations.put(location.getId(), location);
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

    public Location getLocation(Integer id) {
        if (locations == null) {
            return null;
        }
        return locations.get(id);
    }

    public void addCreature(Creature creature) {
        if (creatures == null) {
            creatures = new HashMap();
        }
        creatures.put(creature.getId(), creature);
    }

    public Creature getCreature(Integer id) {
        if (creatures == null) {
            return null;
        }
        return creatures.get(id);
    }

    public void addProduct(Product product) {
        if (products == null) {
            products = new HashMap();
        }
        products.put(product.getId(), product);
    }

    public Product getProduct(Long id) {
        return products.get(id);
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

    public Collection<Creature> getCreatures() {
        return this.creatures.values();
    }

    public Collection<Location> getLocations() {
        return locations.values();
    }

}
