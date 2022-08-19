package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.TransactionDAO;

public class Transaction extends DBModel {
    {
        Database.register(getClass(), new TransactionDAO());
    }

}
