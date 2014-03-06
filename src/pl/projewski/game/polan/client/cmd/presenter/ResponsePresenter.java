/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.polan.client.cmd.presenter;

import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.polan.data.Location;
import pl.projewski.game.polan.data.response.CommandResponse;
import pl.projewski.game.polan.data.response.HelpResponse;
import pl.projewski.game.polan.data.response.ListResponse;
import pl.projewski.game.polan.data.response.ListUsersResponse;
import pl.projewski.game.polan.data.response.LocationsResponse;
import pl.projewski.game.polan.data.response.LookResponse;

/**
 *
 * @author Piotr Rojewski <piotr4rojewski@google.com>
 */
public abstract class ResponsePresenter {

    private static HashMap<Class, ResponsePresenter> presenters;

    public static void presentResponse(CommandResponse response) {
        switch (response.getStatus()) {
            case OK:
                ResponsePresenter presenter = getPresenters().get(response.getClass());
                if (presenter != null) {
                    presenter.present(response);
                } else {
                    LogFactory.getLog(ResponsePresenter.class).info("Not defined presenter for "
                            + response.getClass().getSimpleName());
                }
                break;
            default:
                System.out.println(response.getStatus().name());

        }
    }

    protected static HashMap<Class, ResponsePresenter> getPresenters() {
        if (presenters == null) {
            presenters = new HashMap();
            presenters.put(HelpResponse.class, new HelpResponsePresenter());
            presenters.put(CommandResponse.class, new CommandResponsePresenter());
            presenters.put(ListUsersResponse.class, new ListUsersResponsePresenter());
            presenters.put(ListResponse.class, new ListResponsePresenter());
            presenters.put(LocationsResponse.class, new LocationsResponsePresenter());
            presenters.put(LookResponse.class, new LookResponsePresenter());
        }
        return presenters;
    }

    public abstract void present(CommandResponse response);

    private StringBuilder addLocationBaseInfo(final StringBuilder sb, final Location location) {
        return sb;
    }
}
