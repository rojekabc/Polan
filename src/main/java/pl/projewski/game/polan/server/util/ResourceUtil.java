/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.util;

import java.util.ArrayList;
import java.util.List;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.Product;
import pl.projewski.game.polan.server.data.World;
import pl.projewski.game.polan.server.data.definition.InputResourceDefinition;

/**
 *
 * @author piotrek
 */
public class ResourceUtil {

    public static List<String> getNamesFromInputResourceList(List<InputResourceDefinition> resourceList) {
        List<String> result = null;
        if (resourceList != null && resourceList.isEmpty() == false) {
            result = new ArrayList();
            for (InputResourceDefinition resource : resourceList) {
                int n = resource.getAmmount();
                while (n-- > 0) {
                    result.add(resource.getName());
                }
            }
        }
        return result;
    }

    /**
     * Find and return list of products on location, which may be used. Return null if there is not enough resources.
     *
     * @param world
     * @param location
     * @param resourceList
     * @return
     */
    public static List<Product> findResourcesOnLocation(World world, Location location, List<InputResourceDefinition> resourceList) {
        return findResourcesOnLocation(world, location, resourceList, false);
    }

    public static boolean areResourcesAvialableOnLocation(World world, Location location, List<InputResourceDefinition> resourceList) {
        return findResourcesOnLocation(world, location, resourceList, false) != null;
    }

    /**
     * Find and return list of products on location, which may be used. Return null if there is not enough resources.
     *
     * @param world
     * @param location
     * @param resourceList
     * @param lockResource
     * @return
     */
    public static List<Product> findResourcesOnLocation(World world, Location location, List<InputResourceDefinition> resourceList, boolean lockResource) {
        List<Product> result = new ArrayList();
        List<String> resourceNames = getNamesFromInputResourceList(resourceList);

        List<Long> resources = location.getResources();
        for (Long resourceId : resources) {
            Product product = world.getProduct(resourceId);
            if (product.isLocked()) {
                continue;
            }
            for (String resourceName : resourceNames) {
                if (resourceName.equals(product.getName())) {
                    resourceNames.remove(resourceName);
                    product.setLocked(lockResource);
                    result.add(world.getProduct(resourceId));
                    break;
                }
            }
            if (resourceNames.isEmpty()) {
                return result;
            }
        }
        return null;
    }

}
