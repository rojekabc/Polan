/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.response;

import pl.projewski.game.polan.data.response.CommandResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pl.projewski.game.polan.data.Location;

/**
 *
 * @author wro00541
 */
public class LocationsResponse extends CommandResponse {

    private Set<Location> locationList = new HashSet();

    public LocationsResponse() {
        super(CommandResponseStatus.OK);
    }

    public void addLocation(Location location) {
        locationList.add(location);
    }

    public Collection<Location> getLocations() {
        return locationList;
    }

}
