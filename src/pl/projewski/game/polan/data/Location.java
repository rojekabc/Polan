/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class Location {

    // id of generated location for world
    private int id;
    // is location known by user
    private boolean knownByUser;
    // connection to next location with distance
    private Set<Integer> connection;
    // type of location
    private LocationType type;
    // global area size (units of location)
    private int size;
    // user, to which belongs
    private String username;
    // elements on map
    private Map<Product, Integer> elements;

    public boolean isKnownByUser() {
        return knownByUser;
    }

    public void setKnownByUser(boolean knownByUser) {
        this.knownByUser = knownByUser;
    }

    public Set<Integer> getConnection() {
        return connection;
    }

    public void addConnections(List<Location> connections) {
        for (Location location : connections) {
            addConnection(location);
        }
    }

    public void addConnection(Location location) {
        if (this.connection == null) {
            this.connection = new HashSet();
        }
        this.connection.add(location.getId());
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addElement(Product element) {
        if (elements == null) {
            elements = new HashMap();
        }
        Integer get = elements.get(element);
        if (get == null) {
            elements.put(element, 1);
        } else {
            elements.put(element, ++get);
        }
    }

    public void removeElement(Product element) {
        if (elements == null) {
            return;
        }
        Integer get = elements.get(element);
        if (get == null) {
            return;
        }
        get--;
        if (get == 0) {
            elements.remove(element);
        } else {
            elements.put(element, get);
        }
        if (elements.isEmpty()) {
            elements = null;
        }
    }

    public Map<Product, Integer> getElements() {
        return elements;
    }

}
