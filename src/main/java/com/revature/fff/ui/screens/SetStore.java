package com.revature.fff.ui.screens;

import com.revature.fff.models.DBLocation;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.LocationService;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Label;
import com.revature.fff.ui.components.Table;

import java.util.List;

public class SetStore extends Screen {

    public SetStore(ScreenManager sm) {
        super(sm);
        Label select = new Label("Please choose your preferred store.");
        select.setPosition(2, 5);
        add(select);

        Table locations = new Table(new int[]{4, 15, 15, 2, 5, 20});
        locations.setHeaders(new String[]{"No.", "Street", "City", "St", "Zip", "Manager"});
        List<DBLocation> entries = LocationService.getLocations();
        for (DBLocation entry : entries) {
            DBUser manager = entry.getManager().get();
            locations.addRow(new String[]{entry.getFormattedNumber(), entry.getAddress(), entry.getCity(), entry.getState(),
                                          entry.getZip(), manager != null ? manager.getUsername() : "<None>"});
        }
        locations.setPosition(6, 5);
        add(locations);
        addFocusable(locations);
        locations.setHandler(() -> {
            if (locations.getSelected() >= 0)
                UserService.setLocation(entries.get(locations.getSelected()));
                sm.setScreen(new MainMenu(sm));
        });
    }
}
