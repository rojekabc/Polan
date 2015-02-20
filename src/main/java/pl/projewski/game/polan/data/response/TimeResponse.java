/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.response;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class TimeResponse extends CommandResponse {

    int timeInSeconds;

    public TimeResponse(int timeInSeconds) {
        super(CommandResponseStatus.OK);
        this.timeInSeconds = timeInSeconds;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

}
