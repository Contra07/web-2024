package ru.ssau.todo_2.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICRUDService<P> {
    Optional<P> create(P entity);
    Optional<P> read(UUID id);
    Optional<P> update(P entity);
    List<P> readAll();
    boolean delete(UUID id);
}
