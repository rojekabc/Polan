/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.client.cmd.presenter.ResponsePresenter;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.NamedCommand;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class PolanGameClientCmd {

    Log log = LogFactory.getLog(PolanGameClientCmd.class);
    Gson gson;
    OutputStream outputStream;
    BufferedReader streamReader;

    public PolanGameClientCmd() {
        gson = new Gson();
    }

    private synchronized CommandResponse sendCommand(NamedCommand namedCommand) throws IOException {
        String line = gson.toJson(namedCommand);
        log.trace("Send command: " + line);
        // TODO:
        outputStream.write((line + "\r").getBytes());
        outputStream.flush();
        // Read response
        String responseString = streamReader.readLine();
        log.trace("Read response: " + responseString);
        return (CommandResponse) gson.fromJson(
                responseString, namedCommand.getCmd().getResponseClass());

    }

    public void start() {
        try {
            Socket socket = new Socket("localhost", 8686);
            outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            streamReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            log.info("Started command line client");
            System.out.print("> ");
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split(" ");
                Cmd cmd;
                try {
                    cmd = Cmd.valueOf(split[0].toUpperCase());
                } catch (IllegalArgumentException expected) {
                    System.out.println("Unknown command");
                    continue;
                }
                NamedCommand namedCommand = new NamedCommand(cmd);
                for (int i = 1; i < split.length; i++) {
                    namedCommand.addParameter(split[i]);
                }
                ResponsePresenter.presentResponse(sendCommand(namedCommand));
                // TODO:
                if (namedCommand.getCmd() == Cmd.QUIT) {
                    break;
                }
                System.out.print("> ");
            }
            log.info("Stop command line client.");
        } catch (IOException ex) {
            Logger.getLogger(PolanGameClientCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
