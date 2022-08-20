package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.TransEntryDAO;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class TransEntry extends DBModel {
    UUID id;
    ForeignKey<Transaction> transaction = new ForeignKey<>(Transaction.class);
    ForeignKey<Item> item = new ForeignKey<>(Item.class);
    int price;
    static final Locale locale = new Locale("en", "US");
    static final NumberFormat nf = NumberFormat.getInstance(locale);

    static {
        Database.register(TransEntry.class, TransEntryDAO.getInstance());
    }

    public TransEntry(UUID id, UUID transaction_id, UUID item_id, int price) {
        this.id = id;
        this.transaction.setKey(transaction_id);
        this.item.setKey(item_id);
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ForeignKey<Transaction> getTransaction() {
        return transaction;
    }

    public ForeignKey<Item> getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TransEntry{" +
                       "id=" + id +
                       ", transaction=" + transaction +
                       ", item=" + item +
                       ", price=" + price +
                       '}';
    }
}
