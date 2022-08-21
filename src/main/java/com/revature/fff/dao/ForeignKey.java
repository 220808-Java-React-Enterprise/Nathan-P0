package com.revature.fff.dao;

import com.revature.fff.models.DBModel;

import java.util.UUID;

public class ForeignKey<T extends DBModel> {
    Class<? extends DBModel> cls;
    UUID key;
    T value;

    public ForeignKey(Class<? extends DBModel> cls) {
        this.cls = cls;
        try {
            Class.forName(cls.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ForeignKey(UUID key, Class<? extends DBModel> cls) {
        this.key = key;
        this.cls = cls;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

//    public void set(T value) {
//        this.value = value;
//    }
//
//    public void set(UUID key, T value) {
//        this.key = key;
//        this.value = value;
//    }

    public UUID getKey() {
        return key;
    }

    public T get() {
        if (key != null) return  (T) Database.getForType(cls).get(key);
        return null;
    }
}
