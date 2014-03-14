/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import pl.projewski.game.polan.data.Creature;
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
public class WalkAction implements ICommandAction {

    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        if (props == null || props.size() != 1) {
            return new CommandResponse(CommandResponseStatus.ERROR_WRONG_ARGUMENTS);
        }
        final User user = ctx.getUser();
        if (user == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        World world = WorldManager.getWorld(worldName);
        if (world == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        if (user.getSelectedCreature() == User.NO_CREATURE) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        Creature creature = WorldManager.getCreature(world, user.getSelectedCreature());
        if (creature == null) {
            return new CommandResponse(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        if (!creature.getUserName().equals(user.getName())) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        // TODO: Use pathFinding
        // TODO: Count time need to travel by selected ceature to selected position (update after each partial time position of creature)
        // TODO: If it's exploring do exploring mission on selected territory (and update after that time)
        return new CommandResponse(CommandResponseStatus.OK);
    }

}
