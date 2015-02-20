/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.util;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GsonContainer {

    private static String type;
    private static Object object;

    public GsonContainer(String t, Object o) {
        this.type = t;
        this.object = o;
    }

    public static String getType() {
        return type;
    }

    public static Object getObject() {
        return object;
    }

}
