package ru.ssau.todo_2.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.ssau.todo_2.data.entity.Task;

import java.util.Set;

import ru.ssau.todo_2.data.entity.Project;
import java.util.Optional;



@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID>{
    @Query(value = "delete from Task as T where T.id = :id and T.projectid = :projectId ", nativeQuery = true)    
    void deleteByIdAndProject(UUID id, Project project);
    @Query(value = "select 1 * from Task as T where T.id = :id and T.projectid = :projectId ", nativeQuery = true)    
    Optional<Task> findByIdAndProject(UUID id, UUID projectId);
    @Query(value = "select * from Task as T where T.projectid = :projectId ", nativeQuery = true)    
    Set<Task> findAllByProject(UUID projectId);
    @Query(value = "delete from Task as T where T.projectid = :projectId and T.done = :done ", nativeQuery = true)    
    void deleteAllByProjectAndDone(UUID projectId, boolean done);
}
