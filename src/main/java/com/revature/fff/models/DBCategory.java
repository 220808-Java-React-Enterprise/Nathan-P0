package com.revature.fff.models;

import com.revature.fff.dao.CategoryDAO;
import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;

import java.util.UUID;

public class DBCategory extends DBModel {
    UUID id;
    String name;
    ForeignKey<DBImage> image = new ForeignKey<>(DBImage.class);

    static  {
        Database.register(DBCategory.class, CategoryDAO.getInstance());
    }

    public DBCategory(UUID id, String name, UUID image_id) {
        this.id = id;
        this.name = name;
        this.image.setKey(image_id);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ForeignKey<DBImage> getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Category{" +
                       "id='" + id + '\'' +
                       ", name='" + name + '\'' +
                       ", image=" + image +
                       '}';
    }
}
