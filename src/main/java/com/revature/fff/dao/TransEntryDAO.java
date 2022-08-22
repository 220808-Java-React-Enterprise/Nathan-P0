package com.revature.fff.dao;

import com.revature.fff.models.DBItem;
import com.revature.fff.models.DBTransEntry;
import com.revature.fff.models.DBTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransEntryDAO extends DAO<DBTransEntry> {
    private static TransEntryDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement selectTransaction;
    private TransEntryDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO transentries (transaction, item, quantity, price) " +
                                                   "VALUES (?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM transentries WHERE id = ?");
            selectTransaction = conn.prepareStatement("SELECT * FROM transentries WHERE transaction = ?");

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
        insert.setInt(3, transEntry.getQuantity());
        insert.setInt(4, transEntry.getPrice().value());
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
                                                       rs.getInt("quantity"),
                                                       rs.getInt("price")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DBTransEntry> getForTransaction(DBTransaction transaction) {
        ArrayList<DBTransEntry> results = new ArrayList<>();
        try {
            selectTransaction.setObject(1, transaction.getId());
            try (ResultSet rs = selectTransaction.executeQuery()) {
                while (rs.next())
                    results.add(new DBTransEntry((UUID) rs.getObject("id"),
                                                 (UUID) rs.getObject("transaction"),
                                                 (UUID) rs.getObject("item"),
                                                        rs.getInt("quantity"),
                                                        rs.getInt("price")));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
