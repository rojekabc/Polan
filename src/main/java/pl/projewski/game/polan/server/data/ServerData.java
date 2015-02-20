/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.data.definition.BiomeDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeElementDefinition;
import pl.projewski.game.polan.server.util.RandomElement;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ServerData {

    private final static Log log = LogFactory.getLog(ServerData.class);
    private static ServerData instance = null;

    private Map<String, ProductDefinition> productDefinition = null;
    private List<BiomeDefinition> biomeDefinition = null;

    public static ServerData getInstance() {
        if (instance == null) {
            instance = new ServerData();
        }
        return instance;
    }

    public void loadProductDefinitionsFromGsonFile(String filename) {
        productDefinition = new HashMap();
        Gson gson = GSonUtil.getGSon();
        InputStreamReader reader = null;
        try {
            final File file = new File(filename);
            if (file.exists()) {
                reader = new InputStreamReader(new FileInputStream(file));
                JsonStreamParser parser = new JsonStreamParser(reader);
                while (parser.hasNext()) {
                    ProductDefinition pd = gson.fromJson(parser.next(), ProductDefinition.class);
                    productDefinition.put(pd.getName(), pd);
                    log.debug("Loaded product " + pd.getName());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }

    public ProductDefinition getProductDefinition(String name) {
        if (productDefinition == null) {
            return null;
        }
        return productDefinition.get(name);
    }

    public void loadBiomeDefinitionsFromGsonFile(String filename) {
        biomeDefinition = new ArrayList();
        Gson gson = GSonUtil.getGSon();
        InputStreamReader reader = null;
        try {
            final File file = new File(filename);
            if (file.exists()) {
                reader = new InputStreamReader(new FileInputStream(file));
                JsonStreamParser parser = new JsonStreamParser(reader);
                while (parser.hasNext()) {
                    BiomeDefinition bd = gson.fromJson(parser.next(), BiomeDefinition.class);
                    biomeDefinition.add(bd);
                    log.debug("Loaded biome " + bd.getName());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public RandomElement<BiomeDefinition> getBiomes() {
        RandomElement<BiomeDefinition> randomStartLocationType = new RandomElement();
        for (BiomeDefinition biome : biomeDefinition) {
            randomStartLocationType.addRandomElement(biome, biome.getWeight());

        }
        return randomStartLocationType;
    }

    public RandomElement<ProductDefinition> getBiomeElements(String biomeName) {
        for (BiomeDefinition biome : biomeDefinition) {
            if (biome.getName().equals(biomeName)) {
                return getBiomeElements(biome);
            }
        }
        return null;
    }

    public RandomElement<ProductDefinition> getBiomeFieldForProduct(String biomeName, ProductDefinition productDefinition) {
        RandomElement<ProductDefinition> randomProducts = new RandomElement();
        for (BiomeDefinition biome : biomeDefinition) {
            if (biome.getName().equals(biomeName)) {
                List<BiomeElementDefinition> biomeElements = biome.getBiomeElements();
                List<String> possibleExistsOn = productDefinition.getPossibleExistsOn();
                if (possibleExistsOn == null) {
                    log.warn("Warn: Empty 'exists On' list for product definition; " + "Biome: " + biomeName + "; ProductDefinition: " + productDefinition.getName());
                }
                for (BiomeElementDefinition biomeElementDefinition : biomeElements) {
                    if (possibleExistsOn.contains(biomeElementDefinition.getName())) {
                        ProductDefinition pd = getProductDefinition(biomeElementDefinition.getName());
                        if (pd == null) {
                            log.warn("Cannot find product [" + biomeElementDefinition.getName() + "]");
                            continue;
                        }
                        randomProducts.addRandomElement(pd, biomeElementDefinition.getWeight());
                    }
                }
                break;
            }
        }

        return randomProducts;
    }

    public RandomElement<ProductDefinition> getBiomeElements(BiomeDefinition biome) {
        RandomElement<ProductDefinition> randomProducts = new RandomElement();
        List<BiomeElementDefinition> biomeElements = biome.getBiomeElements();
        for (BiomeElementDefinition biomeElementDefinition : biomeElements) {
            ProductDefinition pd = getProductDefinition(biomeElementDefinition.getName());
            if (pd == null) {
                log.warn("Cannot find product [" + biomeElementDefinition.getName() + "]");
                continue;
            }
            randomProducts.addRandomElement(pd, biomeElementDefinition.getWeight());
        }

        return randomProducts;
    }

}