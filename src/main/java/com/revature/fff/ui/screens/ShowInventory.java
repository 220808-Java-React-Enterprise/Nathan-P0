package com.revature.fff.ui.screens;

import com.revature.fff.models.DBInventory;
import com.revature.fff.models.DBLocation;
import com.revature.fff.services.ItemService;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Label;
import com.revature.fff.ui.components.Table;

import java.util.List;

public class ShowInventory extends Screen {
    public ShowInventory(ScreenManager sm, DBLocation location) {
        super(sm);
        Label title = new Label("Showing inventory for Store: " + location.getFormattedNumber());
        title.setPosition(2, 5);
        add(title);

        Table inventory = new Table(new int[]{40, 3});
        inventory.setHeaders(new String[]{"Product Name", "Qty"});
        List<DBInventory> entries = ItemService.getInventoryForLocation(location);
        for (DBInventory entry : entries) {
            inventory.addRow(new String[]{entry.getItem().get().getName(), "" + entry.getQuantity()});
        }
        inventory.setPosition(6, 5);
        add(inventory);
        addFocusable(inventory);
        inventory.setHandler(() -> {
            if (inventory.getSelected() >= 0) {
                DBInventory entry = entries.get(inventory.getSelected());
                sm.setScreen(new ShowProduct(sm, entry, entry.getItem().get(), true));
            }
        });
    }
}
