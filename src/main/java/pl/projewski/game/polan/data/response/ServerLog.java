/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data.response;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ServerLog extends ServerData {
    
    long worldTime;
    String information;
    ServerLogLevel level;

    private ServerLog(ServerLogLevel level, long time, String info)
    {
        super(ServerDataType.SERVER_LOG);
        this.worldTime = time;
        this.information = info;
        this.level = level;
    }

    public long getWorldTime()
    {
        return worldTime;
    }

    public String getInformation()
    {
        return information;
    }

    public ServerLogLevel getLevel()
    {
        return level;
    }
    
    public static ServerLog info(long time, String info) {
        return new ServerLog(ServerLogLevel.INFO, time, info);
    }
    
}
