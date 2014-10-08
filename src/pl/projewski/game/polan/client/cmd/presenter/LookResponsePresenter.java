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
        final List<Creature> creatures = lookResponse.getCreatures();
        if (creatures != null && !creatures.isEmpty()) {
            sb.append(" * Creatures").appendNewLine();
            for (Creature creature : creatures) {
                sb.append("    "); // tab
                sb.append(creature, true);
                sb.appendNewLine();
            }
        }
        List<Long> elements = location.getElements();
        if (elements != null && !elements.isEmpty()) {
            sb.append(" * Elements").appendNewLine();
            Map<String, Integer> productCounter = new HashMap();
            for (Long productId : elements) {
                Product product = lookResponse.getProduct(productId);
                String productName = null;
                List<Long> productElements = product.getElements();
                if (productElements == null || productElements.isEmpty()) {
                    productName = product.getName();
                } else {
                    boolean first = true;
                    productName = product.getName();
                    productName += " with ";
                    for (Long productElementId : productElements) {
                        if (!first) {
                            productName += ", ";
                        }
                        productName += lookResponse.getProduct(productElementId).getName();
                        first = false;
                    }
                }
                Integer cnt = productCounter.get(productName);
                if (cnt == null) {
                    productCounter.put(productName, Integer.valueOf(1));
                } else {
                    productCounter.put(productName, ++cnt);
                }
            }
            Set<Map.Entry<String, Integer>> elementSet = productCounter.entrySet();
            for (Map.Entry<String, Integer> entry : elementSet) {
                sb.append("    ").append(entry.getKey()).append(" x").append(entry.getValue().intValue()).appendNewLine();
            }
        }
        List<Long> resources = location.getResources();
        if (resources != null && !resources.isEmpty()) {
            sb.append(" * Resources").appendNewLine();
            Map<String, Integer> productCounter = new HashMap();
            for (Long resourceId : resources) {
                Product resource = lookResponse.getProduct(resourceId);
                Integer cnt = productCounter.get(resource.getName());
                if (cnt == null) {
                    productCounter.put(resource.getName(), Integer.valueOf(1));
                } else {
                    productCounter.put(resource.getName(), ++cnt);
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
