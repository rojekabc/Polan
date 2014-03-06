/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.LookResponse;
import pl.projewski.game.polan.data.util.DataStringBuilder;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
class LookResponsePresenter extends ResponsePresenter {

    @Override
    public void present(CommandResponse response) {
        final LookResponse lookResponse = (LookResponse) response;
        final DataStringBuilder sb = new DataStringBuilder();
        final Location location = lookResponse.getLocation();
        sb.append(location);
        sb.appendNewLine();
        // location.get
        final List<Creature> humans = lookResponse.getHuman();
        if (humans != null && !humans.isEmpty()) {
            sb.append(" * Creatures").appendNewLine();
            for (Creature human : humans) {
                sb.append("    "); // tab
                sb.append(human, true);
                sb.appendNewLine();
            }
        }
        Map<Product, Integer> elements = location.getElements();
        if (elements != null && !elements.isEmpty()) {
            sb.append(" * Elements").appendNewLine();
            Set<Map.Entry<Product, Integer>> elementSet = elements.entrySet();
            for (Map.Entry<Product, Integer> entry : elementSet) {
                Product element = entry.getKey();
                sb.append("    ").append(element.name()).append(" x").append(entry.getValue().intValue()).appendNewLine();
            }
        }
        System.out.println(sb.toString());
    }

}
