package com.revature.fff.dao;

import com.revature.fff.models.DBModel;

public class ForeignKey<T extends DBModel> {
    Class<? extends DBModel> cls;
    String key;
    T value;

    public ForeignKey(Class<? extends DBModel> cls) {
        this.cls = cls;
    }

    public ForeignKey(String key, Class<? extends DBModel> cls) {
        this.key = key;
        this.cls = cls;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void set(T value) {
        this.value = value;
    }

    public void set(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T get() {
        if (key != null) value = (T) Database.getForType(cls).get(key);
        return value;
    }
}
