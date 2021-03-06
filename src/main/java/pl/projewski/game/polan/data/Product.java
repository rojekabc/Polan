/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.ArrayList;
import pl.projewski.game.polan.server.data.ProductType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class Product implements ProductContainer {

    Map<ProductProperty, String> properties = new HashMap();
    private long id;
    List<Long> elementList;

    public Product(long id) {
        this.id = id;
    }

    // let say that 1 it's equivalent of 100g
    private int unitCapacity;

    public String getName() {
        return properties.get(ProductProperty.NAME);
    }

    public void setName(String name) {
        properties.put(ProductProperty.NAME, name);
    }

    public void addProperty(ProductProperty property, String value) {
        properties.put(property, value);
    }

    public boolean isLocked() {
        String value = properties.get(ProductProperty.LOCK);
        return value != null && Boolean.parseBoolean(value);
    }

    public String setLocked(boolean value) {
        return properties.put(ProductProperty.LOCK, Boolean.toString(value));
    }

    public long getId() {
        return id;
    }

    public void addElement(Product p) {
        if (elementList == null) {
            elementList = new ArrayList();
        }
        elementList.add(Long.valueOf(p.getId()));
    }

    public List<Long> getElements() {
        return elementList;
    }
}
