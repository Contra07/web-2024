package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.todo_2.data.entity.Project;
import ru.ssau.todo_2.data.entity.Task;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPojo {
    private UUID id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ProjectPojo fromEntity(Project project) {
        return new ProjectPojo(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getStartDate());
    }

    public static Project toEntity(ProjectPojo projectDTO){
        return new Project(
            projectDTO.id,
            projectDTO.name,
            projectDTO.description,
            projectDTO.startDate,
            projectDTO.endDate,
            new HashSet<Task>()
        );
    }
}