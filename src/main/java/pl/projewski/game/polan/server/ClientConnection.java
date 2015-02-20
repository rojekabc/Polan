/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.NamedCommand;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.factor.CommandManager;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ClientConnection implements Runnable {

    private Socket clientSocket = null;
    private ClientContext ctx = null;
    private Log log = LogFactory.getLog(ClientConnection.class);

    ClientConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.ctx = new ClientContext();
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            this.ctx.setOutputStream(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String cmd;
            Gson gson = new Gson();
            log.debug("Start");
            while ((cmd = bufferedReader.readLine()) != null) {
                log.trace("Read line: " + cmd);
                CommandResponse response;
                String responseString;
                final NamedCommand namedCommand = gson.fromJson(cmd, NamedCommand.class);
                try {
                    response = CommandManager.getInstance().runCommand(namedCommand, ctx);
                } catch (Throwable t) {
                    // log failure
                    log.error("Unhandled exception in execution of command !", t);
                    // return error result
                    response = new CommandResponse(CommandResponseStatus.ERROR);
                }

                // TODO
                this.ctx.sendToClient(response);
                if (namedCommand.getCmd() == Cmd.QUIT) {
                    break;
                }
            }
            log.debug("Stop");
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable t) {
            log.fatal("Unexpected error !!!", t);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

}
