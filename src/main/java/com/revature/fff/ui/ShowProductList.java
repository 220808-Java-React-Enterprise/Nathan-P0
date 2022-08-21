package com.revature.fff.ui;

import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBItem;
import com.revature.fff.services.ItemService;

import java.util.List;

public class ShowProductList extends Screen {
    DBCategory category;
    public ShowProductList(ScreenManager sm, DBCategory cat) {
        super(sm);
        category = cat;
        List<DBItem> items;
        if (category != null) {
            items = ItemService.getItemsForCategory(category);
            int row = 3;
            for (DBItem item : items) {
                Button b = new Button(this, item.getName() + " " + item.getDisplayPrice(), 
                                      () -> { sm.setScreen(new ShowProduct(sm, item)); });
                b.setPosition(row, 5);
                components.add(b);
                addFocusable(b);
                row++;
                if (row > 22) break;
            }
        }
    }
    
    
}
