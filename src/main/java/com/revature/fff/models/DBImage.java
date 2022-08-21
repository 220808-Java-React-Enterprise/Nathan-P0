package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ImageDAO;

import java.util.UUID;

public class DBImage extends DBModel {
    private UUID id;
    private String data;

    static {
        Database.register(DBImage.class, ImageDAO.getInstance());
    }

    public DBImage(UUID id, String data) {
        this.id = id;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Image{" +
                       "id='" + id + '\'' +
                       ", data='" + data + '\'' +
                       '}';
    }
}
