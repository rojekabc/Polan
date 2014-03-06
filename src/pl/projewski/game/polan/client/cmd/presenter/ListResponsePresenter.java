/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.List;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.ListResponse;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
class ListResponsePresenter extends ResponsePresenter {

    public ListResponsePresenter() {
    }

    @Override
    public void present(CommandResponse response) {
        ListResponse listResponse = (ListResponse) response;
        List<String> list = listResponse.getList();
        for (String string : list) {
            StringBuilder sb = new StringBuilder();
            sb.append("    ");
            sb.append(string);
            System.out.println(sb.toString());
        }
    }

}
