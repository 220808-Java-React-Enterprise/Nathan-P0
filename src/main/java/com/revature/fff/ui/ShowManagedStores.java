package com.revature.fff.ui;

import com.revature.fff.models.DBLocation;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.LocationService;
import com.revature.fff.services.UserService;

import java.util.List;

public class ShowManagedStores extends Screen {

    public ShowManagedStores(ScreenManager sm) {
        super(sm);
        Label select = new Label("Select a store");
        select.setPosition(2, 5);
        components.add(select);

        Table locations = new Table(new int[]{4, 20, 20, 2, 5, 10});
        locations.setHeaders(new String[]{"No.", "Street", "City", "St", "Zip", "Manager"});
        List<DBLocation> entries = LocationService.getLocationsForManager(UserService.getActiveUser());
        for (DBLocation entry : entries) {
            DBUser manager = entry.getManager().get();
            locations.addRow(new String[]{"" + entry.getNumber(), entry.getAddress(), entry.getCity(), entry.getState(),
                    entry.getZip(), manager != null ? manager.getUsername() : "<None>"});
        }
        locations.setPosition(6, 5);
        components.add(locations);
        addFocusable(locations);
        locations.setHandler(() -> {
            if (locations.getSelected() >= 0)
                UserService.setLocation(entries.get(locations.getSelected()));
            sm.setScreen(new ShowInventory(sm, entries.get(locations.getSelected())));
        });
    }
}
