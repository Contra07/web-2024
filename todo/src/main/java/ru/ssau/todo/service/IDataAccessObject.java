package ru.ssau.todo.service;

import java.util.List;
import java.util.Optional;

public interface IDataAccessObject <T> {
    List<T> List();

    void Create(T t);

    Optional<T> Get(int id);

    void Update(T t, int id);

    void Delete(int id);
}
