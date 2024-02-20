package ru.ssau.todo.service;

import org.springframework.stereotype.Service;

import ru.ssau.todo.api.model.Project;

@Service
public class ProjectService {

    public Project getProject(Integer id) {
        return new Project(id);
    }
}
