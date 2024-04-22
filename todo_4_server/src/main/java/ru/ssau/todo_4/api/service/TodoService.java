package ru.ssau.todo_4.api.service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.ssau.todo_4.api.dto.ProjectPojo;
import ru.ssau.todo_4.api.dto.TaskPojo;
import ru.ssau.todo_4.data.entity.Project;
import ru.ssau.todo_4.data.entity.Task;
import ru.ssau.todo_4.data.repository.IProjectRepository;
import ru.ssau.todo_4.data.repository.ITaskRepository;
import ru.ssau.todo_4.data.repository.IProjectRepository.TasksNumberForProject;

@Service
@AllArgsConstructor
public class TodoService {
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

    public Optional<ProjectPojo> createProject(ProjectPojo pojo) {
        Project project = pojo.toEntity();
        project.setTasks(taskRepository.findAllByProject_Id(project.getId()));
        return Optional.of(ProjectPojo.fromEntity(projectRepository.saveAndFlush(project)));
    }

    public Optional<ProjectPojo> readProject(UUID id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.isEmpty() ? Optional.empty() : Optional.of(ProjectPojo.fromEntity(project.get()));
    }

    public Optional<ProjectPojo> updateProject(UUID id, ProjectPojo pojo) {
        if(projectRepository.existsById(id)){
            Project project = pojo.toEntity();
            project.setId(id);
            project.setTasks(taskRepository.findAllByProject_Id(id));
            return Optional.of(ProjectPojo.fromEntity(projectRepository.saveAndFlush(project)));
        }
        return Optional.empty();
    }

    public boolean deleteProject(UUID id) {
        projectRepository.deleteById(id);
        return !projectRepository.existsById(id);
    }

    public List<ProjectPojo> readAllProjects() {
        List<ProjectPojo> pojos = new ArrayList<ProjectPojo>();
        for(Project project : projectRepository.findAll()){
            pojos.add(ProjectPojo.fromEntity(project));
        }
        return pojos;
    }

    public Optional<TaskPojo> createTask(UUID projectId, TaskPojo pojo) {
        Task task = pojo.toEntity();
        task.setProject(projectRepository.getReferenceById(projectId));
        return Optional.of(TaskPojo.fromEntity(taskRepository.saveAndFlush(task)));
    }

    public Optional<TaskPojo> readTask(UUID projectId, UUID id) {
        Optional<Task> task = taskRepository.findByIdAndProject_Id(id, projectId);
        return task.isEmpty() ? Optional.empty() : Optional.of(TaskPojo.fromEntity(task.get()));
    }

    public Optional<TaskPojo> updateTask(UUID projectId, UUID id,TaskPojo pojo) {
        Task task = pojo.toEntity();
        task.setProject(projectRepository.getReferenceById(projectId));
        if(taskRepository.existsById(id)){
            task.setId(id);
            return Optional.of(TaskPojo.fromEntity(taskRepository.saveAndFlush(task)));
        }
        return Optional.empty();
    }

    public boolean deleteTask(UUID projectId, UUID id) {
        taskRepository.deleteByIdAndProject_Id(id,projectId);
        return !taskRepository.existsById(id);
    }

    public List<TaskPojo> readAllTasks(UUID projectId) {
        List<TaskPojo> pojos = new ArrayList<TaskPojo>();
        for(Task task : taskRepository.findAllByProject_Id(projectId)){
            pojos.add(TaskPojo.fromEntity(task));
        }
        return pojos;
    }

    public boolean deleteAllDoneByProject(UUID projectId){
        taskRepository.deleteAllByDoneAndProject_Id( true,projectId);
        return !taskRepository.existsByProjectIdAndDone(projectId, true);
    }

    public List<ProjectPojo> readProjectsBySearch(String text){
        List<ProjectPojo> projectPojos = new ArrayList<ProjectPojo>();
        if(text != null && !text.isBlank())
        {
            for(Project project : projectRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text,text)){
                projectPojos.add(ProjectPojo.fromEntity(project));
            }
        }
        return projectPojos;
    }

    public Dictionary<UUID, Integer> countTasks(){
        Hashtable<UUID, Integer> result = new Hashtable<UUID, Integer>();
        List<TasksNumberForProject> queryResult = projectRepository.countTasksById();
        queryResult.forEach(
            pair -> {
                result.put(pair.getProjectId(), pair.getTasksNumber());
            }
        );
        return result;
    }

}
