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
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.CreatureType;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.NamedCommand;
import pl.projewski.game.polan.server.Command;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.UserPrivilages;
import pl.projewski.game.polan.server.cmdactions.CreateTownAction;
import pl.projewski.game.polan.server.cmdactions.CreateWorldAction;
import pl.projewski.game.polan.server.cmdactions.HelpAction;
import pl.projewski.game.polan.server.cmdactions.ListLocationsAction;
import pl.projewski.game.polan.server.cmdactions.ListUsersAction;
import pl.projewski.game.polan.server.cmdactions.ListWorldsAction;
import pl.projewski.game.polan.server.cmdactions.LoginAction;
import pl.projewski.game.polan.server.cmdactions.LookAction;
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
    private Map<Cmd, Command> commandList;

    private CommandManager() {
        registerCommands();
    }

    public synchronized static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    private void registerCommands() {
        commandList = new HashMap();
        commandList.put(Cmd.LOGIN, new Command(new LoginAction(), UserPrivilages.EVERYONE));
        commandList.put(Cmd.QUIT, new Command(new QuitAction(), UserPrivilages.EVERYONE));
        commandList.put(Cmd.HELP, new Command(new HelpAction(), UserPrivilages.EVERYONE));
        commandList.put(Cmd.LISTUSERS, new Command(new ListUsersAction(), UserPrivilages.ADMINISTRATOR));
        commandList.put(Cmd.CREATEWORLD, new Command(new CreateWorldAction(), UserPrivilages.ADMINISTRATOR));
        commandList.put(Cmd.CREATETOWN, new Command(new CreateTownAction(), UserPrivilages.ADMINISTRATOR));
        commandList.put(Cmd.LISTWORLDS, new Command(new ListWorldsAction(), UserPrivilages.USER));
        commandList.put(Cmd.USEWORLD, new Command(new UseWorldAction(), UserPrivilages.USER));
        commandList.put(Cmd.LISTLOCATIONS, new Command(new ListLocationsAction(), UserPrivilages.USER));
        commandList.put(Cmd.SAVE, new Command(new SaveAction(), UserPrivilages.USER));
        commandList.put(Cmd.LOOK, new Command(new LookAction(), UserPrivilages.USER));
        commandList.put(Cmd.SELECT, new Command(new SelectAction(), UserPrivilages.USER));
        // commandList.put(Cmd.WALK, new Command(new WalkAction(), UserPrivilages.USER));
        // commandList.put(Cmd.GATHER, new Command(new GatherAction(), UserPrivilages.USER));
        commandList.put(Cmd.TICK, new Command(new TickAction(), UserPrivilages.USER));
    }

    public CommandResponse runCommand(NamedCommand command, ClientContext ctx) {
        if (commandList == null) {
            return new CommandResponse(CommandResponseStatus.ERROR);
        }
        Command cmd = commandList.get(command.getCmd());
        if (cmd == null) {
            return new CommandResponse(CommandResponseStatus.ERROR);
        }
        if (ctx.getUser() == null) {
            if (cmd.getPrivilages() != UserPrivilages.EVERYONE) {
                return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
            }
        } else if (cmd.getPrivilages().ordinal() > ctx.getUser().getPrivilages().ordinal()) {
            return new CommandResponse(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        return cmd.getAction().runCommand(ctx, command.getParameters());
    }

    public List<Cmd> listCommandsForUser(User user) {
        UserPrivilages priv = UserPrivilages.EVERYONE;
        if (user != null) {
            priv = user.getPrivilages();
        }
        List<Cmd> result = new ArrayList();
        for (Map.Entry<Cmd, Command> entry : commandList.entrySet()) {
            if (entry.getValue().getPrivilages().ordinal() <= priv.ordinal()) {
                result.add(entry.getKey());
            }
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
