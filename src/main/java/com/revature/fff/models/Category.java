package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;

public class Category extends DBModel {
    String id;
    String name;
    ForeignKey<Image> image = new ForeignKey<>(Image.class);

    static  {
        //Database.register(Category.class, new CategoryDAO());
    }

    public Category(String id, String name, ForeignKey<Image> image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
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
