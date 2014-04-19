/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.projewski.game.polan.data.response;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ServerData {
    private ServerDataType dataType;
    
    ServerData(ServerDataType dataType) {
        this.dataType = dataType;
    }

    public ServerDataType getDataType()
    {
        return dataType;
    }

    public void setDataType(ServerDataType dataType)
    {
        this.dataType = dataType;
    }
}
