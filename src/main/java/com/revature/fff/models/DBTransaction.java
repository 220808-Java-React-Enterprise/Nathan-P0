package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.TransactionDAO;

import java.sql.Timestamp;
import java.util.UUID;

public class DBTransaction extends DBModel {
    private UUID id;
    private ForeignKey<DBUser> customer = new ForeignKey<>(DBUser.class);
    private ForeignKey<DBLocation> location = new ForeignKey<>(DBLocation.class);
    private boolean cart;
    private Timestamp modified;

    static {
        Database.register(DBTransaction.class, TransactionDAO.getInstance());
    }

    public DBTransaction(UUID id, UUID customer_id, UUID location_id, boolean cart, Timestamp modified) {
        this.id = id;
        this.customer.setKey(customer_id);
        this.location.setKey(location_id);
        this.cart = cart;
        this.modified = modified;
    }

    public UUID getId() {
        return id;
    }

    public ForeignKey<DBUser> getCustomer() {
        return customer;
    }

    public ForeignKey<DBLocation> getLocation() {
        return location;
    }

    public boolean isCart() {
        return cart;
    }

    public Timestamp getModified() {
        return modified;
    }
    
    public void setCart(boolean b) {
        cart = b;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                       "id=" + id +
                       ", customer=" + customer +
                       ", location=" + location +
                       ", cart=" + cart +
                       ", modified=" + modified +
                       '}';
    }
}
