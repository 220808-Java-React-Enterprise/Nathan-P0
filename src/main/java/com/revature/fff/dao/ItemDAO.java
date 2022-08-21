package com.revature.fff.dao;

import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemDAO extends DAO<DBItem> {
    private static ItemDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement selectCat;

    private ItemDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO items (name, description, image, price, category) " +
                                               "VALUES (?, ?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM items WHERE id = ?");
            selectCat = conn.prepareStatement("SELECT * FROM items WHERE category = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static ItemDAO getInstance() {
        if (instance == null) instance = new ItemDAO();
        return instance;
    }

    @Override
    public UUID put(DBItem item) throws SQLException {
        insert.setString(1, item.getName());
        insert.setString(2, item.getDesc());
        insert.setObject(3, item.getImage().getKey());
        insert.setInt(4, item.getPrice());
        insert.setObject(5, item.getCategory().getKey());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBItem getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBItem((UUID) rs.getObject("id"),
                                          rs.getString("name"),
                                          rs.getString("description"),
                                          (UUID) rs.getObject("image"),
                                          rs.getInt("price"),
                                          (UUID) rs.getObject("category")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<DBItem> getCategory(DBCategory cat) {
        ArrayList<DBItem> results = new ArrayList<>();
        try {
            selectCat.setObject(1, cat.getId());
            try (ResultSet rs = selectCat.executeQuery()) {
                while (rs.next())
                    results.add(new DBItem((UUID) rs.getObject("id"),
                                           rs.getString("name"),
                                           rs.getString("description"),
                                           (UUID) rs.getObject("image"),
                                           rs.getInt("price"),
                                           (UUID) rs.getObject("category")));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
