package com.revature.fff.services;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.TransEntryDAO;
import com.revature.fff.dao.TransactionDAO;
import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBTransEntry;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.DBUser;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    
    public static List<DBTransEntry> getEntriesForTransaction(DBTransaction transaction) {
        return TransEntryDAO.getInstance().getForTransaction(transaction);
    }
    
    public static DBTransaction getTransaction(boolean cart) {
        try {
            DBUser user = UserService.getActiveUser();
            if (user == null) return null;
            DBTransaction transaction;
            if (cart) {
                UUID cart_id = user.getCart().getKey();
                if (cart_id == null) {
                    cart_id = TransactionDAO.getInstance().put(
                            new DBTransaction(null, user.getId(), user.getPreferred().getKey(), cart, Timestamp.from(Instant.now())));
                }
                transaction = TransactionDAO.getInstance().get(cart_id);
                UserDAO.getInstance().setCart(user, transaction);
            }
            else  {
                UUID id = TransactionDAO.getInstance().put(
                    new DBTransaction(null, user.getId(), user.getPreferred().getKey(), cart, Timestamp.from(Instant.now())));
                transaction = TransactionDAO.getInstance().get(id);
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void addItem(DBTransaction transaction, DBItem item, int quantity) {
        try {
            TransEntryDAO.getInstance().put(new DBTransEntry(null, transaction.getId(), item.getId(), quantity, item.getPrice().value()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void finalize(DBTransaction transaction) {
        DBUser user = UserService.getActiveUser();
        if (user.getPreferred().getKey() == null) throw new InvalidInput("No store set!");
        if (transaction.isCart()) {
            UserDAO.getInstance().setCart(user, null);
            TransactionDAO.getInstance().removeCart(transaction);
            transaction.setCart(false);
        }
    }
    
    public static List<DBTransaction> getTransactionHistory() {
        DBUser user = UserService.getActiveUser();
        return TransactionDAO.getInstance().getForUser(user);
    }
}
