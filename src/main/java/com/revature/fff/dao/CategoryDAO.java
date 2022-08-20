package com.revature.fff.dao;

import com.revature.fff.models.Category;
import com.revature.fff.models.Image;

import java.sql.*;
import java.util.UUID;

public class CategoryDAO extends DAO<Category> {
    private static CategoryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private CategoryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO categories (name, image) " +
                                               "VALUES (?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM categories WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static CategoryDAO getInstance() {
        if (instance == null) instance = new CategoryDAO();
        return instance;
    }

    @Override
    public UUID put(Category category) throws SQLException {
        insert.setString(1, category.getName());
        insert.setObject(1, category.getImage().get());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public Category getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new Category((UUID) rs.getObject("id"),
                                                   rs.getString("name"),
                                            (UUID) rs.getObject("image")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
