package com.maksym.taskmanager.repo;


import java.util.List;
import java.util.Optional;

public interface Repository<ID, T> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
