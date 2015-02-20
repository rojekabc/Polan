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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.client.cmd.presenter.ResponsePresenter;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.NamedCommand;
import pl.projewski.game.polan.data.response.ServerData;
import pl.projewski.game.polan.data.response.ServerDataType;
import pl.projewski.game.polan.data.response.ServerLog;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class PolanGameClientCmd {

    Log log = LogFactory.getLog(PolanGameClientCmd.class);
    Gson gson;
    OutputStream outputStream;
    BufferedReader streamReader;
    NamedCommand waitForResponse = null;
    Thread serverReaderThread;

    public PolanGameClientCmd() {
        gson = new Gson();
    }

    private synchronized void sendCommand(NamedCommand namedCommand) throws IOException {
        String line = gson.toJson(namedCommand);
        log.trace("Send command: " + line);
        // set command waiting for response
        waitForResponse = namedCommand;
        // send data to server
        outputStream.write((line + "\r").getBytes());
        outputStream.flush();
    }

    public void start() {
        try {
            Socket socket = new Socket("localhost", 8686);
            outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            streamReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            
            Runnable readFromServer = new Runnable() {

                @Override
                public void run()
                {
                    try {
                        log.info("Started client reader");
                        String serverline;
                        while ( (serverline = streamReader.readLine()) != null ) {
                            log.trace("Read from server: " + serverline);
                            ServerData serverData = gson.fromJson(serverline, ServerData.class);
                            if ( serverData.getDataType() == ServerDataType.COMMAND_RESPONSE ) {
                                // get command response
                                // TODO: synchronized
                                if ( waitForResponse != null ) {
                                    ResponsePresenter.presentResponse((CommandResponse) gson.fromJson(
                                        serverline, waitForResponse.getCmd().getResponseClass()));
                                    waitForResponse = null;
                                    System.out.print("> ");
                                } else {
                                    Logger.getLogger(PolanGameClientCmd.class.getName()).log(Level.WARNING,
                                            "Cannot find correct command for response");
                                }
                            } else if ( serverData.getDataType() == ServerDataType.SERVER_LOG ) {
                                // get some log data from server
                                ServerLog serverLog = (ServerLog)gson.fromJson(serverline, ServerLog.class);
                                StringBuilder sb = new StringBuilder();
                                if ( waitForResponse == null ) {
                                    sb.append('\r');
                                }
                                sb.append('[').append(serverLog.getLevel()).append(':').
                                        append(serverLog.getWorldTime()).append("] ");
                                sb.append(serverLog.getInformation());
                                if ( waitForResponse == null ) {
                                    sb.append("\r> ");
                                }
                                System.out.print(sb.toString());
                            }
                        }
                        log.info("Stop client reader");                        
                    } catch (IOException ex) {
                        Logger.getLogger(PolanGameClientCmd.class.getName()).log(Level.SEVERE, null, ex);                        
                    }
                }
            };
            serverReaderThread = new Thread(readFromServer);
            serverReaderThread.start();

            log.info("Started command line client");
            System.out.print("> ");
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                // Don't allow to do command - working on another task
                if ( waitForResponse != null ) {
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
                sendCommand(namedCommand);
                // TODO: let's think it's ok
                if (namedCommand.getCmd() == Cmd.QUIT) {
                    break;
                }
            }
            log.info("Stop command line client.");
        } catch (IOException ex) {
            Logger.getLogger(PolanGameClientCmd.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            serverReaderThread.interrupt();
        }
    }

}
