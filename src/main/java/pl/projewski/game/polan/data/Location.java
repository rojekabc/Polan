/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class Location implements ProductContainer {

    // id of generated location for world
    private int id;
    // is location known by user
    private boolean knownByUser;
    // connection to next location with distance
    private Set<Integer> connection;
    // type of location
    private String type;
    // global area size (units of location)
    private int size;
    // user, to which belongs
    private String username;
    // elements on map
    private List<Long> elements;
    // generaed resources
    private List<Long> resources;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
            elements = new ArrayList();
        }
        elements.add(element.getId());
    }

    public void removeElement(Product element) {
        if (elements == null) {
            return;
        }
        elements.remove(element.getId());
        if (elements.isEmpty()) {
            elements = null;
        }
    }

    public List<Long> getElements() {
        return elements;
    }

    public void addResource(Product resource) {
        if (resources == null) {
            resources = new ArrayList();
        }
        resources.add(resource.getId());
    }

    public List<Long> getResources() {
        return resources;
    }

    public void removeResource(Product resource) {
        removeResource(resource.getId());
    }

    public void removeResource(Long resourceId) {
        if (resources == null) {
            return;
        }
        resources.remove(resourceId);
        if (resources.isEmpty()) {
            resources = null;
        }
    }

}
