package com.revature.fff.services;

import com.revature.fff.dao.LocationDAO;
import com.revature.fff.models.DBLocation;

import java.util.List;

public class LocationService {
    public static List<DBLocation> getLocations() {
        return LocationDAO.getInstance().getAll();
    }
}
