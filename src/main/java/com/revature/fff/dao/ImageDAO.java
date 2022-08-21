package com.revature.fff.dao;

import com.revature.fff.models.DBImage;

import java.sql.*;
import java.util.UUID;

public class ImageDAO extends DAO<DBImage> {
    private static ImageDAO instance;
    private PreparedStatement insert;
    private PreparedStatement select;
    private ImageDAO() {
        try {
            Connection conn = Database.getConnection();
            insert = conn.prepareStatement("INSERT INTO images (data) " +
                                               "VALUES (?) RETURNING id", Statement.RETURN_GENERATED_KEYS);
            select = conn.prepareStatement("SELECT * FROM images WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        instance = this;
    }

    public static ImageDAO getInstance() {
        if (instance == null) instance = new ImageDAO();
        return instance;
    }

    @Override
    public UUID put(DBImage image) throws SQLException {
        insert.setString(1, image.getData());
        insert.executeUpdate();
        try (ResultSet rs = insert.getGeneratedKeys()) {
            return rs.next() ? (UUID) rs.getObject("id") : null;
        }
    }

    @Override
    public DBImage getCurrent(UUID id) {
        try {
            select.setObject(1, id);
            try (ResultSet rs = select.executeQuery()) {
                return rs.next() ?
                               new DBImage((UUID) rs.getObject("id"),
                                           rs.getString("data")) :
                               null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
