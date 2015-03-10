/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public enum CreatureType {

    HUMAN;

    public static List<Cmd> getCreatureCommands(final CreatureType type) {
        List<Cmd> result = new ArrayList();
        switch (type) {
            case HUMAN:
                result.add(Cmd.GATHER);
                result.add(Cmd.WALK);
                result.add(Cmd.PICK);
                result.add(Cmd.CRAFT);
                break;
            default:
                break;
        }
        return result;
    }
}
