/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import pl.projewski.game.polan.data.LocationType;
import pl.projewski.game.polan.server.util.RandomElement;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class PolanServerConfiguration {

    public static final int SERVER_SOCKET_PORT = 8686;
    public static final String DATA_STORE_FILE = "polan.data";
    public static final int FIRST_TICK_AFTER_MILISECONDS = 5000;
    public static final int TICK_DELAY_MILISECONDS = 1000;
    // TODO: now let say it's between 10 and 29. It should be depend on type, start and maybe other things (and maybe little more)
    public static final int BIOME_SIZE_MINIMUM = 10;
    public static final int BIOME_SIZE_MAXIMUM = 30;
    public static String PRODUCT_STORE_FILE = "products.data";

    /*
     public static void defineProducts() {
     }
     */
    public static RandomElement<LocationType> getBiomes() {
        RandomElement<LocationType> randomStartLocationType = new RandomElement();
        randomStartLocationType.addRandomElement(LocationType.PLANES, 30);
        randomStartLocationType.addRandomElement(LocationType.FOREST, 20);
        randomStartLocationType.addRandomElement(LocationType.TAIGA, 5);
        randomStartLocationType.addRandomElement(LocationType.TUNDRA, 2);
        randomStartLocationType.addRandomElement(LocationType.DESERT, 5);
        randomStartLocationType.addRandomElement(LocationType.SWAMPS, 5);
        randomStartLocationType.addRandomElement(LocationType.MOUNTAINS, 2);
        randomStartLocationType.addRandomElement(LocationType.HILLS, 1);
        randomStartLocationType.addRandomElement(LocationType.BEACH, 1);
        return randomStartLocationType;
    }

    public static RandomElement<ProductDefinition> getBiomeElements(LocationType biome) {
        RandomElement<ProductDefinition> randomProducts = new RandomElement();
        /*
         switch (biome) {
         case PLANES:
         randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.CLOVER_FIELD, 10);
         randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 5);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 10);
         break;
         case FOREST:
         randomProducts.addRandomElement(ProductDefinition.OAK_TREE, 30);
         randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 30);
         randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 30);
         randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 5);
         randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 5);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 30);
         break;
         case TAIGA:
         randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 30);
         randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 40);
         break;
         case TUNDRA:
         randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
         randomProducts.addRandomElement(ProductDefinition.PINE_TREE, 10);
         randomProducts.addRandomElement(ProductDefinition.ICE_FIELD, 20);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 30);
         break;
         case DESERT:
         randomProducts.addRandomElement(ProductDefinition.SAND_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.SAND_STONE_FIELD, 10);
         break;
         case SWAMPS:
         randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 10);
         randomProducts.addRandomElement(ProductDefinition.CALAMUS_PLANT, 20);
         randomProducts.addRandomElement(ProductDefinition.MUD_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 5);
         randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 5);
         break;
         case MOUNTAINS:
         randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
         randomProducts.addRandomElement(ProductDefinition.STONE_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.GROUND_FIELD, 5);
         break;
         case HILLS:
         randomProducts.addRandomElement(ProductDefinition.STONE_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.GRANITE_STONE_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.BRICH_TREE, 10);
         break;
         case OCEAN:
         randomProducts.addRandomElement(ProductDefinition.WATER_FIELD, 30);
         break;
         case BEACH:
         randomProducts.addRandomElement(ProductDefinition.SAND_FIELD, 30);
         randomProducts.addRandomElement(ProductDefinition.GRASS_FIELD, 1);
         break;
         default:
         throw new AssertionError(biome.name());
         }
         */
        return randomProducts;
    }

}
