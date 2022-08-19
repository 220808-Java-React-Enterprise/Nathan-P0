package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.TransactionDAO;

public class Transaction extends DBModel {
    static {
        Database.register(Transaction.class, new TransactionDAO());
    }

}
