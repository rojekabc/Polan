/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class GsonCommonAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public JsonElement serialize(T t, Type type, JsonSerializationContext ctx) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(t, type);
        // JsonElement element = ctx.serialize(t);
        if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty("classname", t.getClass().getName());
        }
        return element;
    }

    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        if (element.isJsonObject()) {
            JsonElement parameter = element.getAsJsonObject().get("classname");
            if (parameter != null) {
                String classname = parameter.getAsString();
                try {
                    Class<?> forName = Class.forName(classname);
                    // deserialize without adapter (what about another register's inside this class??)
                    Gson gson = new Gson();
                    return (T) gson.fromJson(element, forName);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GsonCommonAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Gson gson = new Gson();
        return gson.fromJson(element, type);

    }

}
