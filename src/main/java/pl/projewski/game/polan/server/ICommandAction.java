/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server;

import pl.projewski.game.polan.data.User;
import java.util.List;
import java.util.Properties;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.server.data.ClientContext;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public interface ICommandAction {

    public CommandResponse runCommand(final ClientContext ctx, final List<String> props);
}
