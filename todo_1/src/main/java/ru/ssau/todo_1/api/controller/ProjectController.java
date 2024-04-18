package ru.ssau.todo_1.api.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.ssau.todo_1.api.model.Project;
import ru.ssau.todo_1.service.ProjectDAO;


@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectDAO projectService;

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Integer id) {
        Optional<Project> result = projectService.get(id);
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(result.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer id) {
        if(projectService.delete(id)){
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build();
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putProject(@RequestBody Project newProject, @PathVariable Integer id) {
        if(projectService.update(newProject, id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping()
    public ResponseEntity<Project> postProject(@RequestBody Project newProject) {
        Optional<Project> result = projectService.create(newProject);
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(result.get(), HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> result = projectService.list();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjectsByDate(@RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate) {
        Date sd = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date ed = Date.from(endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<Project> result = projectService.list(sd, ed);
        return ResponseEntity.ok().body(result);
    }
    
}
