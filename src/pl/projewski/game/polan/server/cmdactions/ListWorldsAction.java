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
import pl.projewski.game.polan.data.response.ListResponse;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ListWorldsAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        List<String> result = new ArrayList();
        Set<World> listWorlds = WorldManager.listWorlds();
        if (listWorlds != null) {
            for (World world : listWorlds) {
                result.add(world.getName());
            }
        }
        return new ListResponse(result);
    }

}
