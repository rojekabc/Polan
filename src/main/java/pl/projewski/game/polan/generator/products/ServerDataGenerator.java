/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.generator.products;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.data.PolanServerConfiguration;
import pl.projewski.game.polan.server.data.ProductType;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.definition.ActionDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeElementDefinition;
import pl.projewski.game.polan.server.data.definition.CraftDefinition;
import pl.projewski.game.polan.server.data.definition.InputResourceDefinition;
import pl.projewski.game.polan.server.data.definition.OutputResourceDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ServerDataGenerator {

    private static final Gson gson = GSonUtil.getGSon();
    private static Writer writer = null;

    private static void add(ProductDefinition... pds) {
        for (ProductDefinition pd : pds) {
            gson.toJson(pd, writer);
        }
    }

    public static void generateProductsDefinitions() {
        try {
            writer = new OutputStreamWriter(new FileOutputStream(new File(PolanServerConfiguration.PRODUCT_STORE_FILE)));

            /* Fields - ground field is just empty ground. Nothing is growing there */
            add(
                    new ProductDefinition(ProductNames.WATER_FIELD, ProductType.FIELD, null, null),
                    new ProductDefinition(ProductNames.ICE_FIELD, ProductType.FIELD, null, null),
                    new ProductDefinition(ProductNames.SAND_FIELD, ProductType.FIELD, null, null),
                    new ProductDefinition(ProductNames.SANDSTONE_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(
                                        ActionNames.PICK, 80, null,
                                        Arrays.asList(new OutputResourceDefinition(ProductNames.SANDSTONE, 100)))
                            }, null),
                    new ProductDefinition(ProductNames.MUD_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.PICK, 30, null, Arrays.asList(new OutputResourceDefinition(ProductNames.MUD, 100)))
                            }, null),
                    new ProductDefinition(ProductNames.STONE_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.PICK, 150, null, Arrays.asList(new OutputResourceDefinition(ProductNames.STONE, 100)))
                            }, null),
                    new ProductDefinition(ProductNames.GRANIT_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.PICK, 250, null, Arrays.asList(new OutputResourceDefinition(ProductNames.SANDSTONE, 100)))
                            }, null),
                    new ProductDefinition(ProductNames.GROUND_FIELD, ProductType.FIELD, null, null),
                    new ProductDefinition(ProductNames.GRAVEL_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.PICK, 40, null, Arrays.asList(
                                                new OutputResourceDefinition(ProductNames.GRAVEL, 100),
                                                new OutputResourceDefinition(ProductNames.FLINT, 30)))
                            }, null),
                    new ProductDefinition(ProductNames.CLAY_FIELD, ProductType.FIELD,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.PICK, 40, null, Arrays.asList(new OutputResourceDefinition(ProductNames.CLAY, 100)))
                            }, null)
            );
            /* Trees */
            add(
                    new ProductDefinition(ProductNames.OAK_TREE, ProductType.TREE,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 22, null, Arrays.asList(new OutputResourceDefinition(ProductNames.OAK_STICK, 100))),
                                new ActionDefinition(ActionNames.RENEW, 70, null, null)
                            },
                            new String[]{ProductNames.GROUND_FIELD}
                    ),
                    new ProductDefinition(ProductNames.PINE_TREE, ProductType.TREE,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 20, null, Arrays.asList(new OutputResourceDefinition(ProductNames.PINE_STICK, 100))),
                                new ActionDefinition(ActionNames.RENEW, 65, null, null)
                            },
                            new String[]{ProductNames.GROUND_FIELD}
                    ),
                    new ProductDefinition(ProductNames.BIRCH_TREE, ProductType.TREE,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 18, null, Arrays.asList(new OutputResourceDefinition(ProductNames.BIRCH_STICK, 100))),
                                new ActionDefinition(ActionNames.RENEW, 60, null, null)
                            },
                            new String[]{ProductNames.GROUND_FIELD}
                    )
            );
            /*
             Plants
             Calamus - Tatarak
             Clover - Koniczyna
             */
            add(
                    new ProductDefinition(ProductNames.CALAMUS_FIELD, ProductType.PLANT,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 20, null, Arrays.asList(new OutputResourceDefinition(ProductNames.CALAMUS, 100))),
                                new ActionDefinition(ActionNames.RENEW, 50, null, null)
                            },
                            new String[]{ProductNames.MUD_FIELD}),
                    new ProductDefinition(ProductNames.GRASS_FIELD, ProductType.PLANT,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 8, null, Arrays.asList(new OutputResourceDefinition(ProductNames.GRASS, 100))),
                                new ActionDefinition(ActionNames.RENEW, 20, null, null)
                            },
                            new String[]{ProductNames.MUD_FIELD, ProductNames.GROUND_FIELD}),
                    new ProductDefinition(ProductNames.CLOVER_FIELD, ProductType.PLANT,
                            new ActionDefinition[]{
                                new ActionDefinition(ActionNames.GATHER, 10, null, Arrays.asList(new OutputResourceDefinition(ProductNames.CLOVER, 100))),
                                new ActionDefinition(ActionNames.RENEW, 30, null, null)
                            },
                            new String[]{ProductNames.GROUND_FIELD, ProductNames.MUD_FIELD})
            );
            /*
             Some resources
             */
            add(
                    new ProductDefinition(ProductNames.OAK_STICK, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.PINE_STICK, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.BIRCH_STICK, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.GRASS, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.CLOVER, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.CALAMUS, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.CLAY, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.GRAVEL, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.FLINT, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.SAND, ProductType.RESOURCE, null, null),
                    new ProductDefinition(ProductNames.MUD, ProductType.RESOURCE, null, null)
            );
            /*
             Some stones
             */
            add(
                    new ProductDefinition(ProductNames.GRANIT, ProductType.STONE, null, null),
                    new ProductDefinition(ProductNames.STONE, ProductType.STONE, null, null),
                    new ProductDefinition(ProductNames.SANDSTONE, ProductType.STONE, null, null));
            /*
             Some fluids
             */
            add(
                    new ProductDefinition(ProductNames.WATER, ProductType.FLUID, null, null)
            );
            /*
             Some hand craftable items
             */
            add(
                    new ProductDefinition((ProductNames.GRASS_TWINE), ProductType.RESOURCE,
                            new ActionDefinition[]{
                                new CraftDefinition(
                                        5,
                                        new OutputResourceDefinition(ProductNames.GRASS_TWINE, 100),
                                        new InputResourceDefinition(ProductNames.GRASS, 3))
                            }, null)
            );
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerDataGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

    public static void generateBiomesDefinitions() {
        Writer writer = null;
        try {
            Gson gson = GSonUtil.getGSon();
            writer = new OutputStreamWriter(new FileOutputStream(new File(PolanServerConfiguration.BIOME_STORE_FILE)));

            BiomeDefinition bd = null;
            bd = new BiomeDefinition(BiomeNames.PLANES, 30,
                    new BiomeElementDefinition(ProductNames.GRASS_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.CLOVER_FIELD, 10),
                    new BiomeElementDefinition(ProductNames.WATER_FIELD, 5),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 10),
                    new BiomeElementDefinition(ProductNames.CLAY_FIELD, 5)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.FOREST, 20,
                    new BiomeElementDefinition(ProductNames.OAK_TREE, 30),
                    new BiomeElementDefinition(ProductNames.PINE_TREE, 30),
                    new BiomeElementDefinition(ProductNames.BIRCH_TREE, 30),
                    new BiomeElementDefinition(ProductNames.GRASS_FIELD, 5),
                    new BiomeElementDefinition(ProductNames.WATER_FIELD, 5),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 30)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.TUNDRA, 5,
                    new BiomeElementDefinition(ProductNames.PINE_TREE, 30),
                    new BiomeElementDefinition(ProductNames.BIRCH_TREE, 10),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 40)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.TAIGA, 3,
                    new BiomeElementDefinition(ProductNames.PINE_TREE, 10),
                    new BiomeElementDefinition(ProductNames.BIRCH_TREE, 10),
                    new BiomeElementDefinition(ProductNames.ICE_FIELD, 20),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 30)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.DESERT, 5,
                    new BiomeElementDefinition(ProductNames.SAND_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.SANDSTONE_FIELD, 10)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.SWAMPS, 5,
                    new BiomeElementDefinition(ProductNames.WATER_FIELD, 10),
                    new BiomeElementDefinition(ProductNames.CALAMUS_FIELD, 20),
                    new BiomeElementDefinition(ProductNames.MUD_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 5),
                    new BiomeElementDefinition(ProductNames.GRASS_FIELD, 5),
                    new BiomeElementDefinition(ProductNames.CLAY_FIELD, 5)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.MOUNTAINS, 2,
                    new BiomeElementDefinition(ProductNames.BIRCH_TREE, 10),
                    new BiomeElementDefinition(ProductNames.STONE_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.GROUND_FIELD, 5)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.HILLS, 2,
                    new BiomeElementDefinition(ProductNames.BIRCH_TREE, 30),
                    new BiomeElementDefinition(ProductNames.GRANIT_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.STONE_FIELD, 10)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.OCEAN, 0,
                    new BiomeElementDefinition(ProductNames.WATER_FIELD, 30)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.BEACH, 1,
                    new BiomeElementDefinition(ProductNames.SAND_FIELD, 40),
                    new BiomeElementDefinition(ProductNames.GRASS_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.GRAVEL_FIELD, 10),
                    new BiomeElementDefinition(ProductNames.CLAY_FIELD, 10)
            );
            gson.toJson(bd, writer);
            bd = new BiomeDefinition(BiomeNames.RIVER, 5,
                    new BiomeElementDefinition(ProductNames.WATER_FIELD, 40),
                    new BiomeElementDefinition(ProductNames.SAND_FIELD, 30),
                    new BiomeElementDefinition(ProductNames.GRASS_FIELD, 10),
                    new BiomeElementDefinition(ProductNames.GRAVEL_FIELD, 15),
                    new BiomeElementDefinition(ProductNames.CLAY_FIELD, 15)
            );
            gson.toJson(bd, writer);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerDataGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

    public static void main(String[] args) {
        generateProductsDefinitions();
        generateBiomesDefinitions();
        // check loading
        ServerData.getInstance().loadProductDefinitionsFromGsonFile(PolanServerConfiguration.PRODUCT_STORE_FILE);
        ServerData.getInstance().loadBiomeDefinitionsFromGsonFile(PolanServerConfiguration.BIOME_STORE_FILE);

    }

}
