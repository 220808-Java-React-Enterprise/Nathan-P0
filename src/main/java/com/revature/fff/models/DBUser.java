package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.UserDAO;

import java.util.UUID;

public class DBUser extends DBModel {
    private UUID id;
    private String username;
    private String password;
    //FK Transaction.id
    private ForeignKey<DBTransaction> cart = new ForeignKey<>(DBTransaction.class);
    private ForeignKey<DBLocation> preferred = new ForeignKey<>(DBLocation.class);
    private Role role;

    static {
        Database.register(DBUser.class, UserDAO.getInstance());
    }

    public DBUser(UUID id, String username, String password, UUID cart_id, UUID preferred_id, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart.setKey(cart_id);
        this.preferred.setKey(preferred_id);
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

    public ForeignKey<DBTransaction> getCart() {
        return cart;
    }

    public ForeignKey<DBLocation> getPreferred() {
        return preferred;
    }

    public Role getRole() {
        return role;
    }
    
    public void setCart(DBTransaction cart) {
        this.cart.setKey(cart);
    }

    public void setPreferred(DBLocation preferred) {
        this.preferred.setKey(preferred);
    }

    @Override
    public String toString() {
        return "User{" +
                       "id='" + id + '\'' +
                       ", username='" + username + '\'' +
                       ", password='" + password + '\'' +
                       ", cart=" + cart.getKey() +
                       ", preferred=" + preferred.getKey() +
                       ", role=" + role +
                       '}';
    }
}
