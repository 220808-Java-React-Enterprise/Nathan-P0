package com.revature.fff.services;

import com.revature.fff.dao.InventoryDAO;
import com.revature.fff.dao.ItemDAO;
import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBInventory;
import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBLocation;

import java.util.List;

public class ItemService {
    public static List<DBItem> getItemsForCategory(DBCategory cat) {
        return ItemDAO.getInstance().getForCategory(cat);
    }

    public static List<DBInventory> getInventoryForLocation(DBLocation location) {
        return InventoryDAO.getInstance().getForLocation(location);
    }

    public static List<DBInventory> getInventoryForCategory(DBCategory category, DBLocation location) {
        return InventoryDAO.getInstance().getForCategoryAndLocation(category, location);
    }
    
    public static void updateInventory(DBInventory inventory, int quantity) {
        InventoryDAO.getInstance().update(inventory, quantity);
    }
}
