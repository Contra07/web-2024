package ru.ssau.todo_2.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ssau.todo_2.data.entity.Task;

import java.util.Set;

import java.util.Optional;




@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID>{
    Set<Task> findAllByProject_Id(UUID project_Id);
    Optional<Task> findByIdAndProject_Id(UUID id, UUID projectId);  
    void deleteByIdAndProject_Id(UUID id, UUID projectId);
    void deleteAllByDoneAndProject_Id(boolean done, UUID projectId);
}
