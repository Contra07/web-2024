package ru.ssau.todo_2.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ssau.todo_2.api.dto.IPojo;
import ru.ssau.todo_2.api.dto.IPojoFactory;
import ru.ssau.todo_2.data.entity.Entity;

public class CRUDService<E extends Entity, P extends IPojo<E>> implements ICRUDService<P> {
    JpaRepository<E, UUID> repository;
    IPojoFactory<E, P> pojoFactory;

    @Override
    public Optional<P> create(P pojo) {
        try{
            E entity = pojo.toEntity();
            if(entity.getId() != null && repository.existsById(entity.getId())){
                return Optional.empty();
            }
            E newEntity = repository.saveAndFlush(entity);
            return Optional.of(pojoFactory.fromEntity(newEntity));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    @Override
    public Optional<P> read(UUID id) {
        Optional<E> entity = repository.findById(id);
        if(entity.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(pojoFactory.fromEntity(entity.get()));
    }

    @Override
    public Optional<P> update(P pojo) {
        try{
            E entity = pojo.toEntity();
            if(!repository.existsById(entity.getId())){
                return Optional.empty();
            }
            return Optional.of(pojoFactory.fromEntity(repository.saveAndFlush(entity)));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(UUID id) {
        try{
            repository.deleteById(id);
            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    @Override
    public List<P> readAll() {
        List<P> pojos = new ArrayList<P>();
        for(E entity : repository.findAll()){
            pojos.add(pojoFactory.fromEntity(entity));
        }
        return pojos;
    }

}
