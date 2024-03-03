package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.todo_2.data.entity.Project;
import ru.ssau.todo_2.data.entity.Task;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TaskDTO> tasks;

    public static ProjectDTO fromEntity(Project project) {
        List<TaskDTO> tasksDTO = new ArrayList<>();
        for (Task task : project.getTasks()) {
            tasksDTO.add(TaskDTO.fromEntity(task));
        }
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getStartDate(),
                tasksDTO);
    }
}