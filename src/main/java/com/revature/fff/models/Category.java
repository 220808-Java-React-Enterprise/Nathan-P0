package com.revature.fff.models;

import com.revature.fff.dao.CategoryDAO;
import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;

import java.util.UUID;

public class Category extends DBModel {
    UUID id;
    String name;
    ForeignKey<Image> image = new ForeignKey<>(Image.class);

    static  {
        Database.register(Category.class, CategoryDAO.getInstance());
    }

    public Category(UUID id, String name, UUID image_id) {
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

    public ForeignKey<Image> getImage() {
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
