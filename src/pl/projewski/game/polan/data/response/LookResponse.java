/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import pl.projewski.game.polan.data.Creature;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class LookResponse extends CommandResponse {

    private Location location;
    private List<Creature> human;
    private Map<Long, Product> product;

    public LookResponse() {
        super(CommandResponseStatus.OK);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Creature> getHuman() {
        return human;
    }

    public void setHuman(List<Creature> human) {
        this.human = human;
    }

    public void addHuman(final Creature human) {
        if (this.human == null) {
            this.human = new ArrayList();
        }
        this.human.add(human);
    }

    public Product getProduct(final Long id) {
        return product.get(id);
    }

    public void addProduct(final Product product) {
        this.product.put(product.getId(), product);
    }

}
