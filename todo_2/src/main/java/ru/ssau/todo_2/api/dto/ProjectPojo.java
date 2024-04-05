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

    public static ProjectPojo fromEntity(Project entity) {
        return new ProjectPojo(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getStartDate(),
            entity.getStartDate());
    }

    public Project toEntity(){
        return new Project(
            this.getId(),
            this.getName(),
            this.getDescription(),
            this.getStartDate(),
            this.getEndDate(),
            new HashSet<Task>()
        );
    }
}