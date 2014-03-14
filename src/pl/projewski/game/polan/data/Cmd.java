/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import pl.projewski.game.polan.data.response.LocationsResponse;
import pl.projewski.game.polan.data.response.ListUsersResponse;
import pl.projewski.game.polan.data.response.ListResponse;
import pl.projewski.game.polan.data.response.HelpResponse;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.LookResponse;
import pl.projewski.game.polan.data.response.TimeResponse;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public enum Cmd {

    QUIT(CommandResponse.class), LOGIN(CommandResponse.class), HELP(HelpResponse.class),
    LISTUSERS(ListUsersResponse.class), CREATEWORLD(CommandResponse.class), CREATETOWN(CommandResponse.class),
    LISTWORLDS(ListResponse.class),
    LISTLOCATIONS(LocationsResponse.class), SAVE(CommandResponse.class), USEWORLD(CommandResponse.class),
    LOOK(LookResponse.class), SELECT(CommandResponse.class),
    GATHER(TimeResponse.class), WALK(TimeResponse.class);
    private Class responseClass;

    Cmd(Class c) {
        this.responseClass = c;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public Cmd fromName(String name) {
        return Cmd.valueOf(name.toUpperCase());
    }
}
