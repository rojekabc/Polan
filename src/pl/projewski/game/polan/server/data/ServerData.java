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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.util.GSonUtil;

/**
 *
 * @version $Revision$
 * @author rojewski.piotr
 */
public class ServerData {

    private final Log log = LogFactory.getLog(ServerData.class);
    private static ServerData instance = null;

    private Map<String, ProductDefinition> productDefinition = null;

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

}
