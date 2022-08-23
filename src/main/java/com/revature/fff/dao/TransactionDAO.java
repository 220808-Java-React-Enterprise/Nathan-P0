package com.revature.fff.dao;

import com.revature.fff.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDAO extends DAO<DBTransaction> {
    private static TransactionDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private PreparedStatement updateCart;
    private PreparedStatement selectUser;
    private PreparedStatement remove;
    private TransactionDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO transactions (customer, location, cart, modified) " +
                                               "VALUES (?, ?, ?, ?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
            updateCart = conn.prepareStatement("UPDATE transactions SET cart = ? WHERE id = ?");
            selectUser = conn.prepareStatement("SELECT * FROM transactions WHERE customer = ?");
            remove = conn.prepareStatement("DELETE * FROM transactions WHERE id = ?");
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

    public void removeCart(DBTransaction transaction) {
        try {
            if (transaction.isCart()) {
                updateCart.setBoolean(1, false);
                updateCart.setObject(2, transaction.getId());
                updateCart.executeUpdate();
                transaction.setCart(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DBTransaction> getForUser(DBUser user) {
        ArrayList<DBTransaction> results = new ArrayList<>();
        try {
            selectUser.setObject(1, user.getId());
            try (ResultSet rs = selectUser.executeQuery()) {
                while (rs.next())
                    results.add(new DBTransaction((UUID) rs.getObject("id"),
                                                  (UUID) rs.getObject("customer"),
                                                  (UUID) rs.getObject("location"),
                                                         rs.getBoolean("cart"),
                                                         rs.getTimestamp("modified")));
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(DBTransaction transaction) {
        try {
            remove.setObject(1, transaction.getId());
            remove.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
