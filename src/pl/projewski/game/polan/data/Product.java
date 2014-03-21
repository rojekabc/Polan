/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import pl.projewski.game.polan.server.data.ProductType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class Product {

    Map<ProductProperty, String> properties = new HashMap();

    // let say that 1 it's equivalent of 100g
    private int unitCapacity;

    public String getName() {
        return properties.get(ProductProperty.NAME);
    }

    public void setName(String name) {
        properties.put(ProductProperty.NAME, name);
    }

    public ProductType getType() {
        return ProductType.valueOf(properties.get(ProductProperty.TYPE));
    }

    public void setType(ProductType type) {
        properties.put(ProductProperty.TYPE, type.name());
    }

    public void addProperty(ProductProperty property, String value) {
        properties.put(property, value);
    }
}
