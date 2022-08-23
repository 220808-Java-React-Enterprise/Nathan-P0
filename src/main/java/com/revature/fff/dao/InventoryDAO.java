package com.revature.fff.dao;

import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBInventory;
import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBLocation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryDAO extends DAO<DBInventory> {
    private static InventoryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement selectLocation;
    private PreparedStatement selectCatLocation;
    private PreparedStatement selectLocationItem;
    private PreparedStatement update;
    private InventoryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO inventory (location, item, quantity) " +
                                               "VALUES (?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM inventory WHERE id = ?");
            selectLocation = conn.prepareStatement("SELECT * FROM inventory JOIN items ON items.id = item " + 
                                                       "WHERE location = ? ORDER BY items.name");
            selectCatLocation = conn.prepareStatement("SELECT * FROM inventory JOIN items ON items.id = item " + 
                                                          "WHERE location = ? AND items.category = ? ORDER BY items.name");
            selectLocationItem = conn.prepareStatement("SELECT * FROM inventory WHERE location = ? AND item = ?");
            update = conn.prepareStatement("UPDATE inventory SET quantity=?, reserved=0 WHERE id = ?");
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

    public DBInventory get(DBLocation location, DBItem item) {
        try {
            selectLocationItem.setObject(1, location.getId());
            selectLocationItem.setObject(2, item.getId());
            try (ResultSet rs = selectLocationItem.executeQuery()) {
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

    public List<DBInventory> getForLocation(DBLocation location) {
        ArrayList<DBInventory> results = new ArrayList<>();
        try {
            selectLocation.setObject(1, location.getId());
            try (ResultSet rs = selectLocation.executeQuery()) {
                while (rs.next())
                    results.add(new DBInventory((UUID) rs.getObject("id"),
                                                (UUID) rs.getObject("location"),
                                                (UUID) rs.getObject("item"),
                                                       rs.getInt("quantity"),
                                                       rs.getInt("reserved")));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DBInventory> getForCategoryAndLocation(DBCategory category, DBLocation location) {
        ArrayList<DBInventory> results = new ArrayList<>();
        try {
            selectCatLocation.setObject(1, location.getId());
            selectCatLocation.setObject(2, category.getId());
            try (ResultSet rs = selectCatLocation.executeQuery()) {
                while (rs.next())
                    results.add(new DBInventory((UUID) rs.getObject("id"),
                                                (UUID) rs.getObject("location"),
                                                (UUID) rs.getObject("item"),
                                                rs.getInt("quantity"),
                                                rs.getInt("reserved")));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void update(DBInventory inventory, int quantity) {
        try {
            update.setInt(1, quantity);
            update.setObject(2, inventory.getId());
            update.executeUpdate();
            cache.remove(inventory.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
