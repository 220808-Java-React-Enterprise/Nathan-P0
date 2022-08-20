package com.revature.fff.dao;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public abstract class DAO<T> {
     HashMap<UUID, T> cache = new HashMap<>();

    public abstract UUID put(T obj) throws SQLException;

    public T get(UUID id) {
        T ret = cache.get(id);
        if (ret == null) return getCurrent(id);
        return ret;
    }

    public abstract T getCurrent(UUID id);
}
