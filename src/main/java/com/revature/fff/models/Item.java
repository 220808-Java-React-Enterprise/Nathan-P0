package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.ItemDAO;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class Item extends DBModel {
    UUID id;
    String name;
    String desc;
    ForeignKey<Image> image = new ForeignKey<>(Image.class);
    int price;
    ForeignKey<Category> category = new ForeignKey<>(Category.class);
    static final Locale locale = new Locale("en", "US");
    static final NumberFormat nf = NumberFormat.getInstance(locale);

    static {
        Database.register(Item.class, ItemDAO.getInstance());
    }

    public Item(UUID id, String name, String desc, UUID image_id, int price, UUID category_id) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image.setKey(image_id);
        this.price = price;
        this.category.setKey(category_id);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ForeignKey<Image> getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public ForeignKey<Category> getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Item{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", desc='" + desc + '\'' +
                       ", image=" + image +
                       ", price=" + nf.format(price / 100) +
                       ", category=" + category +
                       '}';
    }
}
