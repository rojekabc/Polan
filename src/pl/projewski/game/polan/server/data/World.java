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

    private String name;
    private List<Location> locations;
    private List<Creature> humans;
    private int locationId;
    private int humanId;

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

    public List<Location> getLocations() {
        return locations;
    }

    public void addHuman(Creature human) {
        if (humans == null) {
            humans = new ArrayList();
        }
        humans.add(human);
    }

    public List<Creature> getHumans() {
        return humans;
    }

}
