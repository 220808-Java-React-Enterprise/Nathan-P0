package com.revature.fff.services;

import com.revature.fff.dao.ItemDAO;
import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBItem;

import java.util.List;

public class ItemService {
    public static List<DBItem> getItemsForCategory(DBCategory cat) {
        return ItemDAO.getInstance().getCategory(cat);
    } 
}
