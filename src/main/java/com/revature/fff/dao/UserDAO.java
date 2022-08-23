package com.revature.fff.dao;

import com.revature.fff.models.DBLocation;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.Role;
import com.revature.fff.models.DBUser;

import java.sql.*;
import java.util.UUID;

public class UserDAO extends DAO<DBUser> {
    private static UserDAO instance;
    PreparedStatement insert;
    PreparedStatement select;
    PreparedStatement selectByAuth;
    PreparedStatement updateCart;
    PreparedStatement updateLocation;

    private UserDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO users (username, password, access) " +
                                               "VALUES (?, ?, ?::role) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            selectByAuth = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            updateCart = conn.prepareStatement("UPDATE users SET cart=? WHERE id = ?");
            updateLocation = conn.prepareStatement("UPDATE users SET preferred=? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static UserDAO getInstance() {
        if (instance == null) instance = new UserDAO();
        return instance;
    }

    public UUID put(DBUser user) throws SQLException {
        insert.setString(1, user.getUsername());
        insert.setString(2, user.getPassword());
        insert.setString(3, user.getRole().toString());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    public DBUser getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                    new DBUser((UUID) rs.getObject("id"),
                               rs.getString("username"),
                               rs.getString("password"),
                               (UUID) rs.getObject("cart"),
                               (UUID) rs.getObject("preferred"),
                               Role.valueOf(rs.getString("access"))) :
                    null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DBUser getByUsernameAndPassword(String username, String password) {
        try {
            selectByAuth.setString(1, username);
            selectByAuth.setString(2, password);
            try (ResultSet rs = selectByAuth.executeQuery()) {
                return rs.next() ?
                    new DBUser((UUID) rs.getObject("id"),
                               rs.getString("username"),
                               rs.getString("password"),
                               (UUID) rs.getObject("cart"),
                               (UUID) rs.getObject("preferred"),
                               Role.valueOf(rs.getString("access"))) :
                    null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setCart(DBUser user, DBTransaction cart) {
        try {
            updateCart.setObject(1, cart != null ? cart.getId() : null);
            updateCart.setObject(2, user.getId());
            updateCart.executeUpdate();
            user.setCart(cart);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLocation(DBUser user, DBLocation location) {
        try {
            updateLocation.setObject(1, location.getId());
            updateLocation.setObject(2, user.getId());
            updateLocation.executeUpdate();
            user.setPreferred(location);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
