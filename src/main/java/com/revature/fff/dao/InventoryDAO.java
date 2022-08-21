package com.revature.fff.dao;

import com.revature.fff.models.DBInventory;

import java.sql.*;
import java.util.UUID;

public class InventoryDAO extends DAO<DBInventory> {
    private static InventoryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private InventoryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO inventory (location, item, quantity) " +
                                               "VALUES (?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM inventory WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static InventoryDAO getInstance() {
        if (instance == null) instance = new InventoryDAO();
        return instance;
    }

    @Override
    public UUID put(DBInventory inventory) throws SQLException {
        insert.setObject(1, inventory.getLocation().getKey());
        insert.setObject(2, inventory.getItem().getKey());
        insert.setInt(3, inventory.getQuantity());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBInventory getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBInventory((UUID) rs.getObject("id"),
                                               (UUID) rs.getObject("location"),
                                               (UUID) rs.getObject("item"),
                                               rs.getInt("quantity"),
                                               rs.getInt("reserved")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
