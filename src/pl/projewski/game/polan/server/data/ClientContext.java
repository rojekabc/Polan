/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.response.ServerData;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ClientContext {

    private User user;
    /* Stream connected to server */
    private OutputStream outputStream;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOutputStream(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }
    
    public void sendToClient(final ServerData data) {
        try
        {
            if ( outputStream == null ) {
                return;
            }
            Gson gson = new Gson();
            String responseString = gson.toJson(data);
            LogFactory.getLog(ClientContext.class).trace("Send response: " + responseString);
            outputStream.write((responseString + "\r").getBytes());
            outputStream.flush();
        }
        catch (IOException ex)
        {
            Logger.getLogger(ClientContext.class.getName()).log(Level.SEVERE, "Cannot send to client", ex);
        }
    }

}
