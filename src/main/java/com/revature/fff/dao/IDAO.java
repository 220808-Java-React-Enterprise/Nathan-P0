package com.revature.fff.dao;

public interface IDAO<T> {
    void put(T obj);
    T get(String id);
}
