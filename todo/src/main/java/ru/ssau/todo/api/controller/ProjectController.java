package ru.ssau.todo.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.ssau.todo.api.model.Project;
import ru.ssau.todo.service.ProjectService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    @ResponseBody
    public Project getProject(@RequestParam Integer id) {
        return projectService.getProject(id);
    }
    
}
