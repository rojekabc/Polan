/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class PolanServerConfiguration {

    public static final int SERVER_SOCKET_PORT = 8686;
    public static final String DATA_STORE_FILE = "polan.data";
    public static String PRODUCT_STORE_FILE = "products.data";
    public static String BIOME_STORE_FILE = "biomes.data";

    public static final int FIRST_TICK_AFTER_MILISECONDS = 5000;
    public static final int TICK_DELAY_MILISECONDS = 1000;
    // TODO: now let say it's between 10 and 29. It should be depend on type, start and maybe other things (and maybe little more)
    public static final int BIOME_SIZE_MINIMUM = 10;
    public static final int BIOME_SIZE_MAXIMUM = 30;

}
