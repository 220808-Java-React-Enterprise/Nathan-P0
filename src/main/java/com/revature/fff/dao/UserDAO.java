package com.revature.fff.dao;

import com.revature.fff.models.Role;
import com.revature.fff.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IDAO<User> {
    PreparedStatement insert;
    PreparedStatement select;
    PreparedStatement selectByAuth;

    public UserDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            select = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            selectByAuth = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(User user) {
        try {
            insert.setString(1, user.getUsername());
            insert.setString(2, user.getPassword());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(String id) {
        try {
            select.setString(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                    new User(rs.getString("id"),
                             rs.getString("username"),
                             rs.getString("password"),
                             rs.getString("cart"),
                             Role.valueOf(rs.getString("access"))) :
                    null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getByUsernameAndPassword(String username, String password) {
        try {
            selectByAuth.setString(1, username);
            selectByAuth.setString(2, password);
            try (ResultSet rs = selectByAuth.executeQuery()) {
                return rs.next() ?
                    new User(rs.getString("id"),
                             rs.getString("username"),
                             rs.getString("password"),
                             rs.getString("cart"),
                             Role.valueOf(rs.getString("access"))) :
                    null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
