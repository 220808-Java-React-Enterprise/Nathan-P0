package com.revature.fff.dao;

import com.revature.fff.models.DBModel;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Database {
    private static Database instance;
    private Connection conn;
    private final Properties props = new Properties();
    static HashMap<Class<? extends DBModel>, DAO<? extends DBModel>> daos = new HashMap<>();
    public static void register(Class<? extends DBModel> c, DAO<? extends DBModel> d) {
        daos.put(c,d);
    }

    public static DAO<? extends DBModel> getForType(Class<? extends DBModel> c) {
        return daos.get(c);
    }

    private Database() {
        try {
            props.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    private Connection getConnection0() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
        }
        if (conn == null) throw new RuntimeException("Could not connect to the database.");
        return conn;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection0();
    }
}
