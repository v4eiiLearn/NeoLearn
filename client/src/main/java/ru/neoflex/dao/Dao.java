package ru.neoflex.dao;

import java.util.List;

public interface Dao<T> {

    void create(T t);
    void update(T t);
    void destroy(T t);
    T findById(Integer id);
    List<T> findAll();

}
