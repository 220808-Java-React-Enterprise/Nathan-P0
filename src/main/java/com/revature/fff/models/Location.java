package com.revature.fff.models;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.ForeignKey;
import com.revature.fff.dao.UserDAO;

public class Location extends DBModel {
    private String id;
    private short number;
    private String address;
    private String city;
    private String state;
    private short zip;
    private ForeignKey<User> manager = new ForeignKey<>(User.class);

    static {
        //Database.register(Location.class, new LocationDAO());
    }

    public Location(String id, short number, String address, String city, String state, short zip, String manager_id) {
        this.id = id;
        this.number = number;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.manager.setKey(manager_id);
    }

    public String getId() {
        return id;
    }

    public short getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public short getZip() {
        return zip;
    }

    public ForeignKey<User> getManager() {
        return manager;
    }

    @Override
    public String toString() {
        return "Location{" +
                       "id='" + id + '\'' +
                       ", number=" + number +
                       ", address='" + address + '\'' +
                       ", city='" + city + '\'' +
                       ", state='" + state + '\'' +
                       ", zip=" + zip +
                       ", manager=" + manager +
                       '}';
    }
}
