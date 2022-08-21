package com.revature.fff.dao;

import com.revature.fff.models.DBTransaction;

import java.sql.*;
import java.util.UUID;

public class TransactionDAO extends DAO<DBTransaction> {
    private static TransactionDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private TransactionDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO transactions (customer, location, cart, modified) " +
                                               "VALUES (?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static TransactionDAO getInstance() {
        if (instance == null) instance = new TransactionDAO();
        return instance;
    }

    @Override
    public UUID put(DBTransaction transaction) throws SQLException {
        insert.setObject(1, transaction.getCustomer().getKey());
        insert.setObject(2, transaction.getLocation().getKey());
        insert.setBoolean(3, transaction.isCart());
        insert.setTimestamp(4, transaction.getModified());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBTransaction getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBTransaction((UUID) rs.getObject("id"),
                                                 (UUID) rs.getObject("customer"),
                                                 (UUID) rs.getObject("location"),
                                                 rs.getBoolean("cart"),
                                                 rs.getTimestamp("modified")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
