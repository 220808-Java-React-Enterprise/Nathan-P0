package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.TransactionDAO;

import java.sql.Timestamp;
import java.util.UUID;

public class Transaction extends DBModel {
    private UUID id;
    private ForeignKey<User> customer = new ForeignKey<>(User.class);
    private ForeignKey<Location> location = new ForeignKey<>(Location.class);
    private boolean cart;
    private Timestamp modified;

    static {
        Database.register(Transaction.class, TransactionDAO.getInstance());
    }

    public Transaction(UUID id, UUID customer_id, UUID location_id, boolean cart, Timestamp modified) {
        this.id = id;
        this.customer.setKey(customer_id);
        this.location.setKey(location_id);
        this.cart = cart;
        this.modified = modified;
    }

    public UUID getId() {
        return id;
    }

    public ForeignKey<User> getCustomer() {
        return customer;
    }

    public ForeignKey<Location> getLocation() {
        return location;
    }

    public boolean isCart() {
        return cart;
    }

    public Timestamp getModified() {
        return modified;
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
