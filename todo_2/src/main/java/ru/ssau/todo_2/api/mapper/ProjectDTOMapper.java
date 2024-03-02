package ru.ssau.todo_2.api.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import ru.ssau.todo_2.api.dto.ProjectDTO;
import ru.ssau.todo_2.data.entity.Project;

@Component
public class ProjectDTOMapper implements Function<Project, ProjectDTO>{
    @Override
    public ProjectDTO apply(Project project) {
        return new ProjectDTO(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getStartDate(),
            project.getStartDate()
        );
    }
}
