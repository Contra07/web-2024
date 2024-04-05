package ru.ssau.todo_2.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ssau.todo_2.data.entity.Project;

@Repository
public interface IProjectRepository extends JpaRepository<Project, UUID>{
    List<Project> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
