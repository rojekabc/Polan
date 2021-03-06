/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.UserPrivilages;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.data.PolanServerConfiguration;
import pl.projewski.game.polan.server.data.SaveData;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.factor.CommandManager;
import pl.projewski.game.polan.server.factor.UserManagerFactory;
import pl.projewski.game.polan.server.factor.WorldManager;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class PolanGameServer implements Runnable {

    private ServerSocket serverSocket;
    private boolean isWork;
    private static final Log log = LogFactory.getLog(PolanGameServer.class);

    public PolanGameServer() throws IOException {
        this.serverSocket = new ServerSocket(PolanServerConfiguration.SERVER_SOCKET_PORT);
        this.isWork = true;
    }

    public static void loadData() {
        GSonUtil.registerNewCommonClass(World.class);
        Gson gson = GSonUtil.getGSon();
        try {
            log.info("Loading products ...");
            ServerData.getInstance().loadProductDefinitionsFromGsonFile(PolanServerConfiguration.PRODUCT_STORE_FILE);
            log.info("Loading products ... finished");
            log.info("Loading biomes ...");
            ServerData.getInstance().loadBiomeDefinitionsFromGsonFile(PolanServerConfiguration.BIOME_STORE_FILE);
            log.info("Loading biomes ... finished");
            final File saveFile = new File(PolanServerConfiguration.DATA_STORE_FILE);
            if (saveFile.exists()) {
                final SaveData saveData = gson.fromJson(new InputStreamReader(new FileInputStream(saveFile)), SaveData.class);
                UserManagerFactory.getUserManager().addUsers(saveData.getUsers());
                WorldManager.addWorlds(saveData.getWorlds());
                final StringBuilder sb = new StringBuilder();
                sb
                        .append("Loaded ")
                        .append(saveData.getUsers() != null ? saveData.getUsers().size() : 0)
                        .append(" users and ")
                        .append(saveData.getWorlds() != null ? saveData.getWorlds().size() : 0)
                        .append(" worlds");
                log.info(sb.toString());
            } else {
                log.info("No saved data");
                User user = new User();
                user.setName("user");
                user.setPrivilages(UserPrivilages.USER);
                UserManagerFactory.getUserManager().addUser(user);
                user = new User();
                user.setName("admin");
                user.setPrivilages(UserPrivilages.ADMINISTRATOR);
                UserManagerFactory.getUserManager().addUser(user);
            }
        } catch (FileNotFoundException ex) {
            log.warn("Problem while loading saved data", ex);
        }

    }

    @Override
    public void run() {
        log.info("Server starting ...");
        CommandManager.getInstance();
        // load Data
        PolanGameServer.loadData();
        // create world auto-tick
        TimerTask worldTickerTask = new TimerTask() {
            @Override
            public void run() {
                Set<World> listWorlds = WorldManager.listWorlds();
                for (World world : listWorlds) {
                    WorldManager.nextTick(world);
                }
            }
        };
        // sending auto-tick
        Timer timer = new Timer();
        timer.schedule(worldTickerTask, PolanServerConfiguration.FIRST_TICK_AFTER_MILISECONDS, PolanServerConfiguration.TICK_DELAY_MILISECONDS);
        log.info("Server started.");
        // catch connections
        while (isWork) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (SocketException ex) {
                if (serverSocket.isClosed()) {
                    break;
                } else {
                    log.error("", ex);
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
            if (clientSocket != null) {
                createClientConnection(clientSocket);
            }
        }
        System.out.println("Server stoping ...");
        timer.cancel();
        System.out.println("Server stopped.");
    }

    private void createClientConnection(Socket clientSocket) {
        ClientConnection clientConnection = new ClientConnection(clientSocket);
        new Thread(clientConnection).start();
    }

    public void stopWorking() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            log.error("", ex);
        }
    }
}
