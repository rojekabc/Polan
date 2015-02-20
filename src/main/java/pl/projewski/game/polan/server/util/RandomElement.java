/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class RandomElement<T> {

    private Map<T, Integer> es = new HashMap();

    public void addRandomElement(T element, Integer value) {
        es.put(element, value);
    }

    public T getRandomElement(final Random r) {
        Collection<Integer> values = es.values();
        int sum = 0;
        for (Integer value : values) {
            sum += value.intValue();
        }
        if (sum == 0) {
            return null;
        }
        int random = r.nextInt(sum);
        final Set<Map.Entry<T, Integer>> entrySet = es.entrySet();
        Map.Entry<T, Integer> lastEntry = null;
        for (Map.Entry<T, Integer> entry : entrySet) {
            if (entry.getValue().intValue() == 0) {
                continue;
            }
            lastEntry = entry;
            random -= entry.getValue().intValue();
            if (random <= 0) {
                return entry.getKey();
            }
        }
        return lastEntry != null ? lastEntry.getKey() : null;
    }
}
