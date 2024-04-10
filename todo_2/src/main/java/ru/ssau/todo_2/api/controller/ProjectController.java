package ru.ssau.todo_2.api.controller;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import ru.ssau.todo_2.api.dto.ProjectPojo;
import ru.ssau.todo_2.api.service.TodoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private final TodoService todoService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectPojo> getProject(@PathVariable String id){
        try {
            Optional<ProjectPojo> project = todoService.readProject(UUID.fromString(id));
            return project.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(project.get());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectPojo>> getProjects(){
        List<ProjectPojo> projectPojos = todoService.readAllProjects();
        return projectPojos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(projectPojos);
    }

    @GetMapping("")
    public ResponseEntity<List<ProjectPojo>> getProjectsBySearch(@RequestParam String search) {
        List<ProjectPojo> projectPojos = todoService.readProjectsBySearch(search);
        return projectPojos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(projectPojos);
    }

    @PostMapping("")
    public ResponseEntity<ProjectPojo> postProject(@RequestBody ProjectPojo projectPojo) {
        try{
            Optional<ProjectPojo> project = todoService.createProject(projectPojo);
            return project.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(project.get());
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectPojo> putProject(@PathVariable String id, @RequestBody ProjectPojo projectPojo) {
        try{
            Optional<ProjectPojo> project = todoService.updateProject(UUID.fromString(id), projectPojo);
            return project.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(project.get());
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable String id) {
        try{
            return todoService.deleteProject(UUID.fromString(id)) ? ResponseEntity.noContent().build() :ResponseEntity.notFound().build();
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tasksnumber")
    public ResponseEntity<Dictionary<UUID, Integer>> getTasksNumber() {
        try{    
            Dictionary<UUID, Integer> result = todoService.countTasks();
            
            return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);    
        }catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
