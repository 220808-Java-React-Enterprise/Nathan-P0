package com.revature.fff;

import com.revature.fff.dao.*;
import com.revature.fff.models.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PopulateDatabase {
    static HashMap<String, UUID> images = new HashMap<>();
    static HashMap<String, UUID> categories = new HashMap<>();
    static HashMap<String, UUID> managers = new HashMap<>();
    static HashMap<String, UUID> locations = new HashMap<>();
    static HashMap<String, UUID> products = new HashMap<>();
    static UUID specialLocation, specialProduct;

    public static void main(String[] args) {
        populateCategories();
        populateProducts();
        populateManagers();
        populateLocations();
        populateInventory();
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

    static void populateCategories() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/categories.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    categories.put(data[1], CategoryDAO.getInstance().put(new DBCategory(null, data[1],
                                                                                         loadImage(data[2]))));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void populateProducts() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/products.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    UUID id = ItemDAO.getInstance().put(new DBItem(null, data[1], data[2], loadImage(data[3]),
                                                                   Integer.parseInt(data[4]), categories.get(data[5])));
                    products.put(data[1], id);
                    if ("Chair 3".equals(data[1])) specialProduct = id;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void populateManagers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/managers.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    managers.put(data[1], UserDAO.getInstance().put(new DBUser(null, data[1], data[2], null,
                                                                               null, Role.MANAGER)));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void populateLocations() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/locations.txt"));
            for (String line : lines) {
                if (line.length() > 1) {
                    String[] data = line.split("" + line.charAt(0));
                    UUID id = LocationDAO.getInstance().put(new DBLocation(null, Short.parseShort(data[1]), data[2],
                                                                           data[3], data[4], data[5],
                                                                           managers.get(data[6])));
                    locations.put(data[1], id);
                    if ("1".equals(data[1])) specialLocation = id;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void populateInventory() {
        Random r = new Random();
        try {
            for (UUID location : locations.values()) {
                for (UUID product : products.values()) {
                    if (location == specialLocation && product == specialProduct)
                        InventoryDAO.getInstance().put(new DBInventory(null, location, product, 0, 0));
                    else InventoryDAO.getInstance().put(new DBInventory(null, location, product, r.nextInt(11), 0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
