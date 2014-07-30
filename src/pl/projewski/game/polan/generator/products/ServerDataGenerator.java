/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.generator.products;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.data.PolanServerConfiguration;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.data.ProductType;
import pl.projewski.game.polan.server.data.ServerData;
import pl.projewski.game.polan.server.data.definition.ActionDefinition;
import pl.projewski.game.polan.server.data.definition.ActionOutResourceDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeElementDefinition;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ServerDataGenerator {

    public static void generateProductsDefinitions() {
        Writer writer = null;
        try {
            Gson gson = GSonUtil.getGSon();
            writer = new OutputStreamWriter(new FileOutputStream(new File(PolanServerConfiguration.PRODUCT_STORE_FILE)));

            ProductDefinition pd = null;
            /* Fields - ground field is just empty ground. Nothing is growing there */
            pd = new ProductDefinition(ProductNames.WATER_FIELD, ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.ICE_FIELD, ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.SAND_FIELD, ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.SANDSTONE_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 80, new ActionOutResourceDefinition(ProductNames.SANDSTONE, 100)));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.MUD_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 30, new ActionOutResourceDefinition(ProductNames.MUD, 100)));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.STONE_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 150, new ActionOutResourceDefinition(ProductNames.STONE, 100)));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GRANIT_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 250, new ActionOutResourceDefinition(ProductNames.SANDSTONE, 100)));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GROUND_FIELD, ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GRAVEL_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 40,
                            new ActionOutResourceDefinition(ProductNames.GRAVEL, 100),
                            new ActionOutResourceDefinition(ProductNames.FLINT, 30)));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.CLAY_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.PICK, 40, new ActionOutResourceDefinition(ProductNames.CLAY, 100)));
            gson.toJson(pd, writer);
            /* Trees */
            pd = new ProductDefinition(ProductNames.OAK_TREE, ProductType.FIELD,
                    new ActionDefinition(ActionNames.GATHER, 22, new ActionOutResourceDefinition(ProductNames.OAK_STICK, 100)),
                    new ActionDefinition(ActionNames.RENEW, 70)
            );
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.PINE_TREE, ProductType.FIELD,
                    new ActionDefinition(ActionNames.GATHER, 20, new ActionOutResourceDefinition(ProductNames.PINE_STICK, 100)),
                    new ActionDefinition(ActionNames.RENEW, 65)
            );
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.BIRCH_TREE, ProductType.FIELD,
                    new ActionDefinition(ActionNames.GATHER, 18, new ActionOutResourceDefinition(ProductNames.BIRCH_STICK, 100)),
                    new ActionDefinition(ActionNames.RENEW, 60)
            );
            gson.toJson(pd, writer);
            /*
             Plants
             Calamus - Tatarak
             Clover - Koniczyna
             */
            pd = new ProductDefinition(ProductNames.CALAMUS_FIELD, ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GRASS_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.GATHER, 8, new ActionOutResourceDefinition(ProductNames.GRASS, 100)),
                    new ActionDefinition(ActionNames.RENEW, 20));
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.CLOVER_FIELD, ProductType.FIELD,
                    new ActionDefinition(ActionNames.GATHER, 10, new ActionOutResourceDefinition(ProductNames.CLOVER, 100)),
                    new ActionDefinition(ActionNames.RENEW, 30));
            gson.toJson(pd, writer);
            /*
             Some resources
             */
            pd = new ProductDefinition(ProductNames.OAK_STICK, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.PINE_STICK, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.BIRCH_STICK, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GRASS, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.CLOVER, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.CALAMUS, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.CLAY, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.GRAVEL, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.FLINT, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.SAND, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.MUD, ProductType.RESOURCE);
            gson.toJson(pd, writer);
            /*
             Some stones
             */
            pd = new ProductDefinition(ProductNames.GRANIT, ProductType.STONE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.STONE, ProductType.STONE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition(ProductNames.SANDSTONE, ProductType.STONE);
            gson.toJson(pd, writer);
            /*
             Some fluids
             */
            pd = new ProductDefinition(ProductNames.WATER, ProductType.FLUID);
            gson.toJson(pd, writer);
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
