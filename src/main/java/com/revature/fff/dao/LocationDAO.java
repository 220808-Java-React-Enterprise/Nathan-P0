package com.revature.fff.dao;

import com.revature.fff.models.Location;
import com.revature.fff.models.Role;
import com.revature.fff.models.User;

import java.sql.*;
import java.util.UUID;

public class LocationDAO extends DAO<Location> {
    private static LocationDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private LocationDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO locations (number, address, city, state, zip, manager) " +
                                                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM locations WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static LocationDAO getInstance() {
        if (instance == null) instance = new LocationDAO();
        return instance;
    }

    public UUID put(Location location) throws SQLException {
        insert.setShort(1, location.getNumber());
        insert.setString(2, location.getAddress());
        insert.setString(3, location.getCity());
        insert.setString(4, location.getState());
        insert.setString(5, location.getZip());
        insert.setObject(6, location.getManager().get());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    public Location getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new Location((UUID) rs.getObject("id"),
                                                   rs.getShort("number"),
                                                   rs.getString("address"),
                                                   rs.getString("city"),
                                                   rs.getString("state"),
                                                   rs.getString("zip"),
                                            (UUID) rs.getObject("manager")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
