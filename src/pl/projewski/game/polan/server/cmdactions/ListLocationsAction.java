/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.response.LocationsResponse;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author wro00541
 */
public class ListLocationsAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        if (ctx.getUser().getWorldName() == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        World world = WorldManager.getWorld(ctx.getUser().getWorldName());
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        LocationsResponse response = new LocationsResponse();
        List<Location> locations = world.getLocations();
        if (locations == null) {
            return response;
        }
        ListType listType = null;
        if (props == null || props.isEmpty()) {
            listType = ListType.ALL;
        } else {
            try {
                listType = ListType.valueOf(props.get(0).toUpperCase());
            } catch (IllegalArgumentException ex) {
                listType = ListType.ALL;
            }
        }
        // list all locations (owned and unknown or other player connected to owned)
        for (Location location : locations) {
            if (location.getUsername() != null && location.getUsername().equals(ctx.getUser().getName())) {
                if (listType == ListType.ALL || listType == ListType.OWNED) {
                    response.addLocation(location);
                }
                if (listType != ListType.OWNED) {
                    Set<Integer> connection = location.getConnection();
                    for (Integer locationId : connection) {
                        final Location connectedLocation = WorldManager.getLocation(world, locationId);
                        if (connectedLocation == null) {
                            System.err.println("NOT FIND LOCATION WITH LOCATIONID=" + locationId);
                            continue;
                        }
                        if (listType == ListType.ALL) {
                            response.addLocation(connectedLocation);
                        } else {

                            final String connectedUserName = connectedLocation.getUsername();
                            if ((connectedUserName == null && listType == ListType.UNKNOWN)
                                    || (connectedUserName != null && !connectedUserName.equals(ctx.getUser().getName()) && listType == ListType.ENEMY)) {
                                response.addLocation(connectedLocation);
                            }
                        }
                    }
                }
            }
        }
        return response;
    }

    enum ListType {

        ALL, UNKNOWN, ENEMY, OWNED;
    }

}
