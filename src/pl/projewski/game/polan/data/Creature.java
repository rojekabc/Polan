/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class Creature {

    private int id;
    private String name;
    private Map<Role, RoleExperience> role;
    private int maxCapacity;
    private String userName;
    private int locationId;
    private Role actualRole;
    private CreatureType type;
    private Map<SlotType, Product> slots;

    public Creature(final String name, final String username, final CreatureType type) {
        this.name = name;
        this.userName = username;
        this.type = type;
    }

    public void assignRole(final Role role) {
        if (this.role == null) {
            this.role = new HashMap();
        }
        if (this.role.containsKey(role)) {
            return;
        }
        RoleExperience roleExp = new RoleExperience();
        this.role.put(role, roleExp);
    }

    public String getName() {
        return name;
    }

    public Map<Role, RoleExperience> getRole() {
        return role;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getUserName() {
        return userName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getActualRole() {
        return actualRole;
    }

    public void setActualRole(Role actualRole) {
        this.actualRole = actualRole;
    }

    public CreatureType getType() {
        return type;
    }

    public void setType(CreatureType type) {
        this.type = type;
    }

    public Set<SlotType> listSlots() {
        if (slots == null) {
            return null;
        }
        return slots.keySet();
    }

    public void addSlot(SlotType slot) {
        if (slots == null) {
            slots = new HashMap();
        }
        if (slots.containsKey(slot)) {
            return;
        }
        slots.put(slot, null);
    }

    /**
     * If slot not exists or is not empty do nothing
     *
     * @param slot
     * @param p
     */
    public void useSlot(SlotType slot, Product p) {
        if (slots == null) {
            return;
        }
        if (slots.containsKey(slot)) {
            if (slots.get(slot) != null) {
                return;
            }
            slots.put(slot, p);
        }
    }

    public Product getSlotUse(SlotType slot) {
        if (slots == null) {
            return null;
        }
        return slots.get(slot);
    }

}
