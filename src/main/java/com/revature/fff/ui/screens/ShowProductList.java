package com.revature.fff.ui.screens;

import com.revature.fff.models.*;
import com.revature.fff.services.ItemService;
import com.revature.fff.services.UserService;
import com.revature.fff.ui.ScreenManager;
import com.revature.fff.ui.components.Button;

import java.util.List;

public class ShowProductList extends Screen {
    DBCategory category;
    public ShowProductList(ScreenManager sm, DBCategory cat) {
        super(sm);
        category = cat;
        List<DBInventory> items;
        DBUser user = UserService.getActiveUser();
        DBLocation location = user.getPreferred().get();
        if (location == null) sm.setScreen(new SetStore(sm));
        if (category != null) {
            items = ItemService.getInventoryForCategory(category, location);
            int row = 3;
            for (DBInventory inv : items) {
                DBItem item = inv.getItem().get();
                Button b = new Button(this, item.getName() + " " + item.getDisplayPrice(),
                                      () -> { sm.setScreen(new ShowProduct(sm, inv, item,false)); });
                b.setPosition(row, 5);
                add(b);
                addFocusable(b);
                row++;
                if (row > 22) break;
            }
        }
    }
    
    
}
