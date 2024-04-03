package ru.ssau.todo_2.api.dto;

import ru.ssau.todo_2.data.entity.Entity;

public interface IPojoFactory<E extends Entity, P extends IPojo<E>> {
    P fromEntity(E entity);
}
