package ru.ssau.todo_4.data.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.ssau.todo_4.data.entity.Project;

@Repository
public interface IProjectRepository extends JpaRepository<Project, UUID>{
    List<Project> findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    @Query(value = "select P.id as ProjectId , count(T.id) as TasksNumber from project as P left join task as T on T.projectid = P.id and T.done = false group by P.id ", nativeQuery = true)
    List<TasksNumberForProject> countTasksById();
    public interface TasksNumberForProject {
        UUID getProjectId();
        int getTasksNumber();
    }
}
