package com.revature.fff.ui;

import com.revature.fff.models.DBInventory;
import com.revature.fff.models.DBLocation;
import com.revature.fff.models.DBUser;
import com.revature.fff.services.ItemService;
import com.revature.fff.services.LocationService;
import com.revature.fff.services.UserService;

import java.util.List;

public class ShowInventory extends Screen {
    public ShowInventory(ScreenManager sm, DBLocation location) {
        super(sm);
        Label title = new Label("Showing inventory for Store: " + location.getFormattedNumber());
        title.setPosition(2, 5);
        components.add(title);

        Table inventory = new Table(new int[]{40, 3});
        inventory.setHeaders(new String[]{"Product Name", "Qty"});
        List<DBInventory> entries = ItemService.getInventoryForLocation(location);
        for (DBInventory entry : entries) {
            inventory.addRow(new String[]{entry.getItem().get().getName(), "" + entry.getQuantity()});
        }
        inventory.setPosition(6, 5);
        components.add(inventory);
        addFocusable(inventory);
        inventory.setHandler(() -> {
            if (inventory.getSelected() >= 0) {
                DBInventory entry = entries.get(inventory.getSelected());
                sm.setScreen(new ShowProduct(sm, entry, entry.getItem().get(), true));
            }
        });
    }
}
