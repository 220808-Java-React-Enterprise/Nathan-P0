package com.revature.fff.dao;

import com.revature.fff.models.DBTransEntry;

import java.sql.*;
import java.util.UUID;

public class TransEntryDAO extends DAO<DBTransEntry> {
    private static TransEntryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private TransEntryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO transentries (transaction, item, price) " +
                                                   "VALUES (?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM transentries WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static TransEntryDAO getInstance() {
        if (instance == null) instance = new TransEntryDAO();
        return instance;
    }

    @Override
    public UUID put(DBTransEntry transEntry) throws SQLException {
        insert.setObject(1, transEntry.getTransaction().getKey());
        insert.setObject(2, transEntry.getItem().getKey());
        insert.setInt(3, transEntry.getPrice());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBTransEntry getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBTransEntry((UUID) rs.getObject("id"),
                                                (UUID) rs.getObject("transaction"),
                                                (UUID) rs.getObject("item"),
                                                rs.getInt("price")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
