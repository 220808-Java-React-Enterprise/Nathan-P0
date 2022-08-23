package com.revature.fff.services;

import com.revature.fff.dao.*;
import com.revature.fff.models.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class TransactionService {

    public static List<DBTransEntry> getEntriesForTransaction(DBTransaction transaction) {
        return TransEntryDAO.getInstance().getForTransaction(transaction);
    }

    public static DBTransaction getTransaction(boolean cart) {
        try {
            DBUser user = UserService.getActiveUser();
            if (user == null) return null;
            if (user.getPreferred().getKey() == null) throw new NoStore("No store set!");
            DBTransaction transaction;
            if (cart) {
                UUID cart_id = user.getCart().getKey();
                if (cart_id == null) {
                    cart_id = TransactionDAO.getInstance().put(
                            new DBTransaction(null, user.getId(), user.getPreferred().getKey(), cart,
                                              Timestamp.from(Instant.now())));
                }
                transaction = TransactionDAO.getInstance().get(cart_id);
                UserDAO.getInstance().setCart(user, transaction);
            } else {
                UUID id = TransactionDAO.getInstance().put(
                        new DBTransaction(null, user.getId(), user.getPreferred().getKey(), cart,
                                          Timestamp.from(Instant.now())));
                transaction = TransactionDAO.getInstance().get(id);
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addItem(DBTransaction transaction, DBItem item, int quantity) {
        try {
            TransEntryDAO.getInstance().put(new DBTransEntry(null, transaction.getId(), item.getId(), quantity,
                                                             item.getPrice().value()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<DBTransEntry> finalize(DBTransaction transaction) {
        DBUser user = UserService.getActiveUser();
        HashMap<UUID, DBInventory> inventory = new HashMap<>();
        HashSet<DBTransEntry> nostock = new HashSet<>();
        DBLocation location = transaction.getLocation().get();
        List<DBTransEntry> entries = TransEntryDAO.getInstance().getForTransaction(transaction);
        for (DBTransEntry entry : entries) {
            DBInventory inv = InventoryDAO.getInstance().get(location, entry.getItem().get());
            DBInventory cached = inventory.get(inv.getId());
            if (cached != null) inv = cached;
            else inventory.put(inv.getId(), inv);
            if (inv.getReserved() + entry.getQuantity() > inv.getQuantity()) nostock.add(entry);
            else inv.addReserved(entry.getQuantity());
        }
        if (!nostock.isEmpty()) {
            List<DBTransEntry> ret = new ArrayList<>();
            ret.addAll(nostock);
            return ret;
        }
        for (DBInventory inv : inventory.values()) {
            InventoryDAO.getInstance().update(inv, inv.getQuantity() - inv.getReserved());
        }
        if (transaction.isCart()) {
            UserDAO.getInstance().setCart(user, null);
            TransactionDAO.getInstance().removeCart(transaction);
            transaction.setCart(false);
        }
        return null;
    }

    public static void removeItems(Iterable<DBTransEntry> entries) {
        for (DBTransEntry entry : entries) {
            TransEntryDAO.getInstance().remove(entry);
        }
    }

    public static void cancel(DBTransaction transaction) {
        List<DBTransEntry> entries = TransEntryDAO.getInstance().getForTransaction(transaction);
        removeItems(entries);
        if (!transaction.isCart()) TransactionDAO.getInstance().remove(transaction);
    }

    public static List<DBTransaction> getTransactionHistory() {
        DBUser user = UserService.getActiveUser();
        return TransactionDAO.getInstance().getForUser(user);
    }
}