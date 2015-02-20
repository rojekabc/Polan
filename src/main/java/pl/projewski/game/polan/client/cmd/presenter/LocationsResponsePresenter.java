/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.Collection;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.response.LocationsResponse;
import pl.projewski.game.polan.data.util.DataStringBuilder;

/**
 *
 * @author wro00541
 */
class LocationsResponsePresenter extends ResponsePresenter {

    @Override
    public void present(CommandResponse response) {
        LocationsResponse locationsResponse = (LocationsResponse) response;
        Collection<Location> locations = locationsResponse.getLocations();
        for (Location location : locations) {
            DataStringBuilder sb = new DataStringBuilder();
            sb.append(location);
            System.out.println(sb.toString());
        }
    }

}
