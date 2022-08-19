package com.revature.fff.models;

import com.revature.fff.dao.Database;

public class Image extends DBModel {
    private String id;
    private String data;

    static {
        //Database.register(Image.class, new ImageDAO());
    }

    public Image(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
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
