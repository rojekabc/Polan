/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.data;

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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.data.definition.BiomeDefinition;
import pl.projewski.game.polan.server.data.definition.BiomeElementDefinition;
import pl.projewski.game.polan.server.data.definition.ProductDefinition;
import pl.projewski.game.polan.server.util.RandomElement;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ServerData {

    private final static Log LOG = LogFactory.getLog(ServerData.class);
    private static ServerData instance = null;

    private Map<String, ProductDefinition> productDefinitionMap = null;
    private List<BiomeDefinition> biomeDefinitionList = null;

    public static ServerData getInstance() {
        if (instance == null) {
            instance = new ServerData();
        }
        return instance;
    }

    public int sizeProductDefinitions() {
        return productDefinitionMap == null ? 0 : productDefinitionMap.size();
    }

    public int sizeBiomeDefinitions() {
        return biomeDefinitionList == null ? 0 : biomeDefinitionList.size();
    }

    public void loadProductDefinitionsFromGsonFile(String filename) {
        productDefinitionMap = new HashMap();
        Gson gson = GSonUtil.getGSon();
        InputStreamReader reader = null;
        try {
            final File file = new File(filename);
            if (file.exists()) {
                reader = new InputStreamReader(new FileInputStream(file));
                JsonStreamParser parser = new JsonStreamParser(reader);
                while (parser.hasNext()) {
                    ProductDefinition pd = gson.fromJson(parser.next(), ProductDefinition.class);
                    productDefinitionMap.put(pd.getName(), pd);
                    LOG.debug("Loaded product " + pd.getName());
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(reader);
        }

    }

    public ProductDefinition getProductDefinition(String name) {
        if (productDefinitionMap == null) {
            return null;
        }
        return productDefinitionMap.get(name);
    }

    public List<ProductDefinition> getProductDefinitionsByFilter(String filter) {
        return getProductDefinitionsByFilter(filter, null);
    }

    public List<ProductDefinition> getProductDefinitionsByFilter(final String filter, final String possibleAction) {
        List<ProductDefinition> result = new ArrayList();
        if (productDefinitionMap != null && filter != null) {
            Set<Map.Entry<String, ProductDefinition>> entrySet = productDefinitionMap.entrySet();
            for (Map.Entry<String, ProductDefinition> entry : entrySet) {
                if (entry.getKey().contains(filter)) {
                    if (possibleAction == null || entry.getValue().isActionAble(possibleAction)) {
                        result.add(entry.getValue());
                    }
                }
            }
        }
        return result;
    }

    public void loadBiomeDefinitionsFromGsonFile(String filename) {
        biomeDefinitionList = new ArrayList();
        Gson gson = GSonUtil.getGSon();
        InputStreamReader reader = null;
        try {
            final File file = new File(filename);
            if (file.exists()) {
                reader = new InputStreamReader(new FileInputStream(file));
                JsonStreamParser parser = new JsonStreamParser(reader);
                while (parser.hasNext()) {
                    BiomeDefinition bd = gson.fromJson(parser.next(), BiomeDefinition.class);
                    biomeDefinitionList.add(bd);
                    LOG.debug("Loaded biome " + bd.getName());
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
        for (BiomeDefinition biome : biomeDefinitionList) {
            randomStartLocationType.addRandomElement(biome, biome.getWeight());

        }
        return randomStartLocationType;
    }

    public RandomElement<ProductDefinition> getBiomeElements(String biomeName) {
        for (BiomeDefinition biome : biomeDefinitionList) {
            if (biome.getName().equals(biomeName)) {
                return getBiomeElements(biome);
            }
        }
        return null;
    }

    public RandomElement<ProductDefinition> getBiomeFieldForProduct(String biomeName, ProductDefinition productDefinition) {
        RandomElement<ProductDefinition> randomProducts = new RandomElement();
        for (BiomeDefinition biome : biomeDefinitionList) {
            if (biome.getName().equals(biomeName)) {
                List<BiomeElementDefinition> biomeElements = biome.getBiomeElements();
                List<String> possibleExistsOn = productDefinition.getPossibleExistsOn();
                if (possibleExistsOn == null) {
                    LOG.warn("Warn: Empty 'exists On' list for product definition; " + "Biome: " + biomeName + "; ProductDefinition: " + productDefinition.getName());
                }
                for (BiomeElementDefinition biomeElementDefinition : biomeElements) {
                    if (possibleExistsOn.contains(biomeElementDefinition.getName())) {
                        ProductDefinition pd = getProductDefinition(biomeElementDefinition.getName());
                        if (pd == null) {
                            LOG.warn("Cannot find product [" + biomeElementDefinition.getName() + "]");
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
                LOG.warn("Cannot find product [" + biomeElementDefinition.getName() + "]");
                continue;
            }
            randomProducts.addRandomElement(pd, biomeElementDefinition.getWeight());
        }

        return randomProducts;
    }

}
