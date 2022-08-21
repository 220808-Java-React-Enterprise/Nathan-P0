package com.revature.fff.dao;

import com.revature.fff.models.DBCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryDAO extends DAO<DBCategory> {
    private static CategoryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement selectAll;
    private CategoryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO categories (name, image) " +
                                               "VALUES (?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM categories WHERE id = ?");
            selectAll = conn.prepareStatement("SELECT * FROM categories");
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
    public UUID put(DBCategory category) throws SQLException {
        insert.setString(1, category.getName());
        insert.setObject(2, category.getImage().getKey());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBCategory getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBCategory((UUID) rs.getObject("id"),
                                              rs.getString("name"),
                                              (UUID) rs.getObject("image")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DBCategory> getAll() {
        ArrayList<DBCategory> results = new ArrayList<>();
        try (ResultSet rs = selectAll.executeQuery()) {
            while(rs.next())
               results.add(new DBCategory((UUID) rs.getObject("id"),
                                          rs.getString("name"),
                                          (UUID) rs.getObject("image")));
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
