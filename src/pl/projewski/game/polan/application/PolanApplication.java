/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.application;

import java.io.IOException;
import pl.projewski.game.polan.client.cmd.PolanGameClientCmd;
import pl.projewski.game.polan.server.PolanGameServer;

/**
 *
 * @author piotrek
 */
public class PolanApplication
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        // start server
        PolanGameServer polanGameServer = new PolanGameServer();
        new Thread(polanGameServer).start();
        // start client
        PolanGameClientCmd polanGameClient = new PolanGameClientCmd();
        polanGameClient.start();
        // stop server
        polanGameServer.stopWorking();
    }
}
