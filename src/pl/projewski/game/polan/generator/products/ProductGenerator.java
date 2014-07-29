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
import pl.projewski.game.polan.server.data.GatherDefinition;
import pl.projewski.game.polan.server.data.PolanServerConfiguration;
import pl.projewski.game.polan.server.data.ProductDefinition;
import pl.projewski.game.polan.server.data.ProductType;
import pl.projewski.game.polan.server.data.ServerData;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ProductGenerator {

    public static void main(String[] args) {
        Writer writer = null;
        try {
            Gson gson = GSonUtil.getGSon();
            writer = new OutputStreamWriter(new FileOutputStream(new File(PolanServerConfiguration.PRODUCT_STORE_FILE)));

            ProductDefinition pd = null;
            /* Fields - ground field is just empty ground. Nothing is growing there */
            pd = new ProductDefinition("water field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("ice field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("ice field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("sand field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("sandstone field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("mud field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("stone field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("granit field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("ground field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("gravel field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("clay field", ProductType.FIELD);
            gson.toJson(pd, writer);
            /* Trees */
            pd = new ProductDefinition("oak tree", ProductType.FIELD);
            pd.setGather(new GatherDefinition(22, 70, "oak stick"));
            gson.toJson(pd, writer);
            pd = new ProductDefinition("pine tree", ProductType.FIELD);
            pd.setGather(new GatherDefinition(20, 65, "oak stick"));
            gson.toJson(pd, writer);
            pd = new ProductDefinition("birch tree", ProductType.FIELD);
            pd.setGather(new GatherDefinition(18, 60, "birch stick"));
            gson.toJson(pd, writer);
            /*
             Plants
             Calamus - Tatarak
             Clover - Koniczyna
             */
            pd = new ProductDefinition("calamus field", ProductType.FIELD);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("grass field", ProductType.FIELD);
            pd.setGather(new GatherDefinition(8, 20, "grass"));
            gson.toJson(pd, writer);
            pd = new ProductDefinition("clover field", ProductType.FIELD);
            pd.setGather(new GatherDefinition(10, 30, "clover"));
            gson.toJson(pd, writer);
            /*
             Some resources
             */
            pd = new ProductDefinition("oak stick", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("pine stick", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("birch stick", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("grass", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("clover", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("calamus", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("clay", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("gravel", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("flint", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("sand", ProductType.RESOURCE);
            gson.toJson(pd, writer);
            /*
             Some stones
             */
            pd = new ProductDefinition("granite", ProductType.STONE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("stone", ProductType.STONE);
            gson.toJson(pd, writer);
            pd = new ProductDefinition("sandstone", ProductType.STONE);
            gson.toJson(pd, writer);
            /*
             Some fluids
             */
            pd = new ProductDefinition("water", ProductType.FLUID);
            gson.toJson(pd, writer);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ProductGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        ServerData.getInstance().loadProductDefinitionsFromGsonFile(PolanServerConfiguration.PRODUCT_STORE_FILE);

    }

}
