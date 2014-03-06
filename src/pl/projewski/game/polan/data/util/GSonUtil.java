/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GSonUtil {

    private static GsonBuilder builder = new GsonBuilder();

    public static Gson getGSon() {
        return builder.create();
    }

    public static <T> void registerNewCommonClass(Class<T> c) {
        builder.registerTypeAdapter(c, new GsonCommonAdapter<T>());
    }
}
