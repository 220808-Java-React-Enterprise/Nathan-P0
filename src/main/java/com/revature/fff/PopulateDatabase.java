package com.revature.fff;

import com.revature.fff.dao.CategoryDAO;
import com.revature.fff.dao.ImageDAO;
import com.revature.fff.dao.ItemDAO;
import com.revature.fff.models.DBCategory;
import com.revature.fff.models.DBImage;
import com.revature.fff.models.DBItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PopulateDatabase {
    static HashMap<String, UUID> images = new HashMap<>();
    static HashMap<String, UUID> categories = new HashMap<>();
    public static void main(String[] args) {
        populateCategories();
        populateProducts();
    }
    
    static UUID loadImage(String name) {
        UUID image_id = images.get(name);
        if (image_id == null) {
            List<String> imagedata = null;
            try {
                imagedata = Files.readAllLines(Paths.get("src/main/resources/images", name));
                StringBuilder sb = new StringBuilder();
                for (String s : imagedata) sb.append(s);
                image_id = ImageDAO.getInstance().put(new DBImage(null, sb.toString()));
                images.put(name, image_id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return image_id;
    }
    static void  populateCategories() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/categories.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    categories.put(data[1], CategoryDAO.getInstance().put(new DBCategory(null, data[1], loadImage(data[2]))));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    static void  populateProducts() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/products.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    ItemDAO.getInstance().put(new DBItem(null, data[1], data[2], loadImage(data[3]),
                                                         Integer.parseInt(data[4]), categories.get(data[5])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
