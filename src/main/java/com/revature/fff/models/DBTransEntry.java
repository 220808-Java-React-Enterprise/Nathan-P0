package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.TransEntryDAO;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class DBTransEntry extends DBModel {
    UUID id;
    ForeignKey<DBTransaction> transaction = new ForeignKey<>(DBTransaction.class);
    ForeignKey<DBItem> item = new ForeignKey<>(DBItem.class);
    int quantity;
    Price price;
    static final Locale locale = new Locale("en", "US");
    static final NumberFormat nf = NumberFormat.getInstance(locale);

    static {
        Database.register(DBTransEntry.class, TransEntryDAO.getInstance());
    }

    public DBTransEntry(UUID id, UUID transaction_id, UUID item_id, int quantity, int price) {
        this.id = id;
        this.transaction.setKey(transaction_id);
        this.item.setKey(item_id);
        this.quantity = quantity;
        this.price = new Price(price);
    }

    public UUID getId() {
        return id;
    }

    public ForeignKey<DBTransaction> getTransaction() {
        return transaction;
    }

    public ForeignKey<DBItem> getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public Price getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return "TransEntry{" +
                       "id=" + id +
                       ", transaction=" + transaction +
                       ", item=" + item +
                       ", quantity=" + price +
                       ", price=" + price +
                       '}';
    }
}
