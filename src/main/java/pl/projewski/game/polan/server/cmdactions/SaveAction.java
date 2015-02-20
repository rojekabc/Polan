/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.server.cmdactions;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.CommandResponseStatus;
import pl.projewski.game.polan.data.User;
import pl.projewski.game.polan.data.util.GSonUtil;
import pl.projewski.game.polan.server.ICommandAction;
import pl.projewski.game.polan.server.data.ClientContext;
import pl.projewski.game.polan.server.data.SaveData;

/**
 *
 * @author wro00541
 */
public class SaveAction implements ICommandAction {

    public SaveAction() {
    }

    @Override
    public CommandResponse runCommand(final ClientContext ctx, List<String> props) {
        Writer writer = null;
        try {
            SaveData saveData = new SaveData();
            saveData.prepareData();
            Gson gson = GSonUtil.getGSon();
            writer = new OutputStreamWriter(new FileOutputStream(new File("polan.data")));
            gson.toJson(saveData, writer);
            writer.flush();
            return new CommandResponse(CommandResponseStatus.OK);
        } catch (IOException ex) {
            return new CommandResponse(CommandResponseStatus.ERROR);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}
