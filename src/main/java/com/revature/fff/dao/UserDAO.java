package com.revature.fff.dao;

import com.revature.fff.models.Role;
import com.revature.fff.models.DBUser;

import java.sql.*;
import java.util.UUID;

public class UserDAO extends DAO<DBUser> {
    private static UserDAO instance;
    PreparedStatement insert;
    PreparedStatement select;
    PreparedStatement selectByAuth;

    private UserDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO users (username, password) " +
                                               "VALUES (?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            selectByAuth = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
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
                               Role.valueOf(rs.getString("access"))) :
                    null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
