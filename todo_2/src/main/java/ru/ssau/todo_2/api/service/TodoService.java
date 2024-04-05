package ru.ssau.todo_2.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.ssau.todo_2.api.dto.ProjectPojo;
import ru.ssau.todo_2.api.dto.TaskPojo;
import ru.ssau.todo_2.data.entity.Project;
import ru.ssau.todo_2.data.entity.Task;
import ru.ssau.todo_2.data.repository.IProjectRepository;
import ru.ssau.todo_2.data.repository.ITaskRepository;

@Service
@AllArgsConstructor
public class TodoService {
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

    public Optional<ProjectPojo> createProject(ProjectPojo pojo) {
        try{
            Project project = pojo.toEntity();
            project.setTasks(taskRepository.findAllByProject(project.getId()));
            return Optional.of(ProjectPojo.fromEntity(projectRepository.saveAndFlush(project)));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public Optional<ProjectPojo> readProject(UUID id) {
        try{
            Optional<Project> project = projectRepository.findById(id);
            return project.isEmpty() ? Optional.empty() : Optional.of(ProjectPojo.fromEntity(project.get()));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public Optional<ProjectPojo> updateProject(UUID id, ProjectPojo pojo) {
        try{
            if(projectRepository.existsById(id)){
                Project project = pojo.toEntity();
                project.setId(id);
                project.setTasks(taskRepository.findAllByProject(project.getId()));
                return Optional.of(ProjectPojo.fromEntity(projectRepository.saveAndFlush(project)));
            }
            return Optional.empty();
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public boolean deleteProject(UUID id) {
        try{
            projectRepository.deleteById(id);
            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    public List<ProjectPojo> readAllProjects() {
        List<ProjectPojo> pojos = new ArrayList<ProjectPojo>();
        for(Project project : projectRepository.findAll()){
            pojos.add(ProjectPojo.fromEntity(project));
        }
        return pojos;
    }

    public Optional<TaskPojo> createTask(UUID projectId, TaskPojo pojo) {
        try{
            Task task = pojo.toEntity();
            task.setProject(projectRepository.getReferenceById(projectId));
            return Optional.of(TaskPojo.fromEntity(taskRepository.saveAndFlush(task)));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public Optional<TaskPojo> readTask(UUID projectId, UUID id) {
        try{
            Optional<Task> task = taskRepository.findByIdAndProject(id, projectId);
            return task.isEmpty() ? Optional.empty() : Optional.of(TaskPojo.fromEntity(task.get()));
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public Optional<TaskPojo> updateTask(UUID projectId, UUID id,TaskPojo pojo) {
        try{
            Task task = pojo.toEntity();
            task.setProject(projectRepository.getReferenceById(projectId));
            if(taskRepository.existsById(task.getId())){
                task.setId(id);
                return Optional.of(TaskPojo.fromEntity(taskRepository.saveAndFlush(task)));
            }
            return Optional.empty();
        }
        catch(Exception ex){
            return Optional.empty();
        }
    }

    public boolean deleteTask(UUID projectId, UUID id) {
        try{
            taskRepository.deleteByIdAndProject(id, projectRepository.getReferenceById(projectId));
            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    public List<TaskPojo> readAllTasks(UUID projectId) {
        List<TaskPojo> pojos = new ArrayList<TaskPojo>();
        for(Task task : taskRepository.findAllByProject(projectId)){
            pojos.add(TaskPojo.fromEntity(task));
        }
        return pojos;
    }

    public boolean deleteAllDoneByProject(UUID projectId){
        try{
            taskRepository.deleteAllByProjectAndDone(projectId, true);
            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    public List<ProjectPojo> readProjectsBySearch(String text){
        List<ProjectPojo> projectPojos = new ArrayList<ProjectPojo>();
        for(Project project : projectRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text,text)){
            projectPojos.add(ProjectPojo.fromEntity(project));
        }
        return projectPojos;
    }
}
