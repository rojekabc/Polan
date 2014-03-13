/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.HashMap;
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
        List<Product> elements = location.getElements();
        if (elements != null && !elements.isEmpty()) {
            sb.append(" * Elements").appendNewLine();
            Map<String, Integer> productCounter = new HashMap();
            for (Product product : elements) {
                Integer cnt = productCounter.get(product.getName());
                if (cnt == null) {
                    productCounter.put(product.getName(), Integer.valueOf(1));
                } else {
                    productCounter.put(product.getName(), ++cnt);
                }
            }
            Set<Map.Entry<String, Integer>> elementSet = productCounter.entrySet();
            for (Map.Entry<String, Integer> entry : elementSet) {
                sb.append("    ").append(entry.getKey()).append(" x").append(entry.getValue().intValue()).appendNewLine();
            }
        }
        System.out.println(sb.toString());
    }

}
