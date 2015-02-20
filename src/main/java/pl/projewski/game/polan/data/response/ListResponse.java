/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.data.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public class ListResponse extends CommandResponse {

    private List<String> entries;

    public ListResponse() {
        super(CommandResponseStatus.OK);
    }

    public ListResponse(final Collection<String> col) {
        super(CommandResponseStatus.OK);
        if (col != null) {
            add(col);
        }
    }

    public final void add(final Collection<String> col) {
        if (entries == null) {
            entries = new ArrayList();
        }
        entries.addAll(col);
    }

    public final void add(final String s) {
        if (entries == null) {
            entries = new ArrayList();
        }
        entries.add(s);
    }

    public List<String> getList() {
        return this.entries;
    }

}
