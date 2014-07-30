/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.factor;

import pl.projewski.game.polan.server.cmdactions.GatherAction;
import pl.projewski.game.polan.server.cmdactions.WalkAction;
import pl.projewski.game.polan.server.cmdactions.UseWorldAction;
import pl.projewski.game.polan.server.cmdactions.SaveAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.CreatureType;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.NamedCommand;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.UserPrivilages;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.cmdactions.CreateTownAction;
import pl.projewski.game.polan.server.cmdactions.CreateWorldAction;
import pl.projewski.game.polan.server.cmdactions.HelpAction;
import pl.projewski.game.polan.server.cmdactions.ListLocationsAction;
import pl.projewski.game.polan.server.cmdactions.ListUsersAction;
import pl.projewski.game.polan.server.cmdactions.ListWorldsAction;
import pl.projewski.game.polan.server.cmdactions.LoginAction;
import pl.projewski.game.polan.server.cmdactions.LookAction;
import pl.projewski.game.polan.server.cmdactions.PickAction;
import pl.projewski.game.polan.server.cmdactions.QuitAction;
import pl.projewski.game.polan.server.cmdactions.SelectAction;
import pl.projewski.game.polan.server.cmdactions.TickAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class CommandManager {

    private static CommandManager instance;

    public synchronized static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private static Map<Cmd, ICommandAction> getCommands(User user) {
        Map<Cmd, ICommandAction> result = new HashMap();
        UserPrivilages priv = UserPrivilages.EVERYONE;
        if (user != null) {
            priv = user.getPrivilages();
        }
        if (priv.ordinal() >= UserPrivilages.EVERYONE.ordinal()) {
            result.put(Cmd.LOGIN, new LoginAction());
            result.put(Cmd.QUIT, new QuitAction());
            result.put(Cmd.HELP, new HelpAction());
        }
        if (priv.ordinal() >= UserPrivilages.USER.ordinal()) {
            result.put(Cmd.LISTWORLDS, new ListWorldsAction());
            result.put(Cmd.USEWORLD, new UseWorldAction());
            result.put(Cmd.LISTLOCATIONS, new ListLocationsAction());
            result.put(Cmd.SAVE, new SaveAction());
            result.put(Cmd.LOOK, new LookAction());
            result.put(Cmd.SELECT, new SelectAction());
            result.put(Cmd.TICK, new TickAction());
        }
        if (priv.ordinal() >= UserPrivilages.ADMINISTRATOR.ordinal()) {
            result.put(Cmd.LISTUSERS, new ListUsersAction());
            result.put(Cmd.CREATEWORLD, new CreateWorldAction());
            result.put(Cmd.CREATETOWN, new CreateTownAction());
        }
        // if user select creature he can do it's set of command
        if (user != null && user.getSelectedCreature() != User.NO_CREATURE) {
            String worldName = user.getWorldName();
            if (worldName == null) {
                return result;
            }
            World world = WorldManager.getWorld(worldName);
            if (world == null) {
                return result;
            }
            Creature creature = world.getCreature(user.getSelectedCreature());
            if (creature == null) {
                return result;
            }
            CreatureType type = creature.getType();
            List<Cmd> creatureCommands = CreatureType.getCreatureCommands(type);
            for (Cmd creatureCmd : creatureCommands) {
                switch (creatureCmd) {
                    case GATHER:
                        result.put(Cmd.GATHER, new GatherAction());
                        break;
                    case WALK:
                        result.put(Cmd.WALK, new WalkAction());
                        break;
                    case PICK:
                        result.put(Cmd.PICK, new PickAction());
                        break;
                }
            }
        }

        return result;
    }

    public CommandResponse runCommand(NamedCommand command, ClientContext ctx) {
        Map<Cmd, ICommandAction> commandList = getCommands(ctx.getUser());
        if (commandList == null) {
            Logger.getLogger(CommandManager.class.getSimpleName()).warning("No command list");
            return new CommandResponse(CommandResponseStatus.ERROR);
        }
        ICommandAction cmdAction = commandList.get(command.getCmd());
        if (cmdAction == null) {
            Logger.getLogger(CommandManager.class.getSimpleName()).warning("Command not found on list");
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        return cmdAction.runCommand(ctx, command.getParameters());
    }

    public List<Cmd> listCommandsForUser(User user) {
        UserPrivilages priv = UserPrivilages.EVERYONE;
        if (user != null) {
            priv = user.getPrivilages();
        }
        List<Cmd> result = new ArrayList();
        for (Cmd entry : getCommands(user).keySet()) {
            result.add(entry);
        }
        // if user select creature he can do it's set of command
        if (user != null && user.getSelectedCreature() != User.NO_CREATURE) {
            String worldName = user.getWorldName();
            if (worldName == null) {
                return result;
            }
            World world = WorldManager.getWorld(worldName);
            if (world == null) {
                return result;
            }
            Creature creature = world.getCreature(user.getSelectedCreature());
            if (creature == null) {
                return result;
            }
            CreatureType type = creature.getType();
            List<Cmd> creatureCommands = CreatureType.getCreatureCommands(type);
            if (creatureCommands != null) {
                result.addAll(creatureCommands);
            }
        }
        return result;
    }

}
