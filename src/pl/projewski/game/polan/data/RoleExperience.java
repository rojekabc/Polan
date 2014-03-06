/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data;

import java.util.Map;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class RoleExperience {

    private int exp;
    private Map<Product, Integer> productExp;

    public int getExp() {
        return exp;
    }

    public int getProductExperience(Product p) {
        if (productExp == null) {
            return 0;
        }
        Integer pexp = productExp.get(p);
        if (pexp == null) {
            return 0;
        }
        return pexp.intValue();
    }

}
