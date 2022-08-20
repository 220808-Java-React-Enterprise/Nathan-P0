package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.UserDAO;

import java.util.UUID;

public class User extends DBModel {
    private UUID id;
    private String username;
    private String password;
    //FK Transaction.id
    private ForeignKey<Transaction> cart = new ForeignKey<>(Transaction.class);
    private Role role;

    static {
        Database.register(User.class, UserDAO.getInstance());
    }

    public User(UUID id, String username, String password, UUID cart_id, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart.setKey(cart_id);
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ForeignKey<Transaction> getCart() {
        return cart;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                       "id='" + id + '\'' +
                       ", username='" + username + '\'' +
                       ", password='" + password + '\'' +
                       ", cart=" + cart.getKey() +
                       ", role=" + role +
                       '}';
    }
}
