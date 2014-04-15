/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class TickAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        /*
         * Tick without arguments - one tick
         * Tick with number - number of ticks to do
         */
        final User user = ctx.getUser();
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        int numOfTicks = 1;
        if (props != null && !props.isEmpty()) {
            numOfTicks = Integer.valueOf(props.get(0));
            if (numOfTicks <= 0) {
                numOfTicks = 1;
            }
        }
        while (numOfTicks > 0) {
            WorldManager.nextTick(world);
            numOfTicks--;
        }
        return new CommandResponse(CommandResponseStatus.OK);
    }

}
