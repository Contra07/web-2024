package ru.ssau.todo_3.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import ru.ssau.todo_3.api.dto.TaskPojo;
import ru.ssau.todo_3.api.service.TodoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/projects/{projectId}/tasks")
@AllArgsConstructor
public class TaskController {
    private final TodoService todoService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskPojo> readTask(@PathVariable String projectId, @PathVariable String id){
        try {
            Optional<TaskPojo> task = todoService.readTask(UUID.fromString(projectId), UUID.fromString(id));
            return task.isEmpty() ? ResponseEntity.notFound().build() :ResponseEntity.ok().body(task.get());
        } catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskPojo>> readTasks(@PathVariable String projectId){
        try{
            List<TaskPojo> taskPojos = todoService.readAllTasks(UUID.fromString(projectId));
            return taskPojos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(taskPojos);
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    

    @PostMapping("")
    public ResponseEntity<TaskPojo> createTask(@PathVariable String projectId, @RequestBody TaskPojo taskPojo) {
        try{
            Optional<TaskPojo> task = todoService.createTask(UUID.fromString(projectId), taskPojo);
            return task.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.ok().body(task.get());
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskPojo> updateTask(@PathVariable String projectId, @PathVariable String id, @RequestBody TaskPojo taskPojo) {
        try{
            Optional<TaskPojo> task = todoService.updateTask(UUID.fromString(projectId), UUID.fromString(id), taskPojo);
            if(task.isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(task.get());
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String projectId, @PathVariable String id) {
        try{
            return todoService.deleteTask(UUID.fromString(id), UUID.fromString(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("done")
    public ResponseEntity<String> deleteAllTask(@PathVariable String projectId) {
        try{
            return todoService.deleteAllDoneByProject(UUID.fromString(projectId)) ? ResponseEntity.noContent().build() :  ResponseEntity.notFound().build();
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
