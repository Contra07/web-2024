package ru.ssau.todo_2.api.dto;

import ru.ssau.todo_2.data.entity.Entity;

public interface IPojo<E extends Entity> {
    E toEntity();
}
