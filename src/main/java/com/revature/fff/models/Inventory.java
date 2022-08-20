package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.InventoryDAO;

import java.util.UUID;

public class Inventory extends DBModel {
    UUID id;
    ForeignKey<Location> location = new ForeignKey<>(Location.class);
    ForeignKey<Item> item = new ForeignKey<>(Item.class);
    int quantity;
    int reserved;

    static  {
        Database.register(Inventory.class, InventoryDAO.getInstance());
    }

    public Inventory(UUID id, UUID location_id, UUID item_id, int quantity, int reserved) {
        this.id = id;
        this.location.setKey(location_id);
        this.item.setKey(item_id);
        this.quantity = quantity;
        this.reserved = reserved;
    }

    public UUID getId() {
        return id;
    }

    public ForeignKey<Location> getLocation() {
        return location;
    }

    public ForeignKey<Item> getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                       "id=" + id +
                       ", location=" + location +
                       ", item=" + item +
                       ", quantity=" + quantity +
                       ", reserved=" + reserved +
                       '}';
    }
}
