package com.revature.fff.dao;

import com.revature.fff.models.Inventory;
import com.revature.fff.models.Item;

import java.sql.*;
import java.util.UUID;

public class ItemDAO extends DAO<Item> {
    private static ItemDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private ItemDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO items (name, description, image, prices, category) " +
                                               "VALUES (?, ?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM items WHERE id = ?");
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
    public UUID put(Item item) throws SQLException {
        insert.setString(1, item.getName());
        insert.setString(2, item.getDesc());
        insert.setObject(3, item.getImage().get());
        insert.setInt(4, item.getPrice());
        insert.setObject(5, item.getCategory().get());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public Item getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new Item((UUID) rs.getObject("id"),
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
}
