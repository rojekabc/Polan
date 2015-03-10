/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author piotrek
 */
public abstract class ACreatureAction  implements ICommandAction {
    private static final Log log = LogFactory.getLog(ACreatureProductAction.class);
    protected String actionName;
    protected World world;
    protected Creature creature;
    protected Location location;
    protected String productFilter = null;
    protected int howManyTimes = 1;
    protected ClientContext ctx;

    protected ACreatureAction(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Run command after base context interpretation and arguments interpretation
     * @return 
     */
    public abstract CommandResponse runCommand();
    
    @Override
    public CommandResponse runCommand(ClientContext ctx, List<String> props) {
        this.ctx = ctx;
        try {
            baseContextCheck(ctx);
        } catch (ActionException e) {
            return new CommandResponse(e.getStatus());
        }
        interpretArguments(props);
        return runCommand();
    }
    
    /**
     * Get base elements to work on from ClientContext
     * @param ctx
     * @throws ActionException 
     */
    protected void baseContextCheck(ClientContext ctx) throws ActionException {
        final User user = ctx.getUser();
        if (user == null) {
            throw new ActionException(CommandResponseStatus.ERROR_UNKNOWN_USER);
        }
        String worldName = user.getWorldName();
        world = WorldManager.getWorld(worldName);
        if (world == null) {
            throw new ActionException(CommandResponseStatus.ERROR_UNKNOWN_WORLD);
        }
        if (user.getSelectedCreature() == User.NO_CREATURE) {
            throw new ActionException(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        creature = world.getCreature(user.getSelectedCreature());
        if (creature == null) {
            throw new ActionException(CommandResponseStatus.ERROR_UNKNOWN_CREATURE);
        }
        if (!creature.getUserName().equals(user.getName())) {
            throw new ActionException(CommandResponseStatus.ERROR_NO_RIGHTS);
        }
        int locationId = creature.getLocationId();
        location = world.getLocation(locationId);
        if (location == null) {
            throw new ActionException(CommandResponseStatus.ERROR_UNKNOWN_LOCATION);
        }
    }
    
    /**
     * Interpret arguments in idea of sending as:
     *  - [empty] - do one on default action with products
     *  - [number] [product] - do number times on product (can be filter)
     *  - [number] - do number times on default action with products
     *  - [product] - do one time on product (can be filter)
     * @param props 
     */
    protected void interpretArguments(List<String> props) {
        if (props != null && !props.isEmpty()) {
            // get how many times
            String arg = props.get(0);
            try {
                howManyTimes = Integer.parseInt(arg);
                if (howManyTimes <= 0) {
                    howManyTimes = 1;
                }
            } catch (NumberFormatException e) {
                if (arg.equals("*")) {
                    // do neverending work on all you can find
                    howManyTimes = -1;
                } else {
                    // it's not a number - it's a product name/filter
                    productFilter = props.get(0);
                }
            }
            // get filter on names
            for (int i = 1; i < props.size(); i++) {
                if (productFilter != null) {
                    productFilter += " ";
                }
                if (productFilter == null) {
                    productFilter = "";
                }
                productFilter += props.get(i);
            }
        }
    }

    
}
