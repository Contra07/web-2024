package ru.ssau.todo.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.ssau.todo.api.model.Project;
import ru.ssau.todo.service.ProjectDAO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectDAO projectService;

    @ResponseBody
    @GetMapping("/{id}")
    public Optional<Project> getProject(@PathVariable Integer id) {
        return projectService.Get(id);
    }

    @ResponseBody
    @GetMapping("/all")
    public List<Project> getAllProjects() {
        return projectService.List();
    }
    
}
