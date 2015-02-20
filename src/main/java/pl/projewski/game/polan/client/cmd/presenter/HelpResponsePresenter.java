/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.List;
import pl.projewski.game.polan.data.Cmd;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.HelpResponse;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class HelpResponsePresenter extends ResponsePresenter {

    @Override
    public void present(CommandResponse response) {
        HelpResponse helpResponse = (HelpResponse) response;
        List<Cmd> cmdList = helpResponse.getCmdList();
        System.out.println("Available commands:");
        int len = 0;
        for (Cmd cmd : cmdList) {
            System.out.print("    ");
            len += 4;
            final String cmdName = cmd.name().toLowerCase();
            System.out.print(cmdName);
            len += cmdName.length();
            if (len >= 75) {
                System.out.println();
                len = 0;
            } else {
                System.out.print(" ");
                len++;
            }
        }
        System.out.println();
    }

}
