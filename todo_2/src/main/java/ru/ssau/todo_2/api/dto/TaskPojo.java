package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.todo_2.data.entity.Task;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPojo {
    private UUID id;
    private String name;
    private String description;
    private LocalDate completionDate;
    private boolean done;

    public static TaskPojo fromEntity(Task task) {
        return new TaskPojo(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCompletionDate(),
                task.isDone());
    }

    public static Task toEntity(TaskPojo taskDTO){
        return new Task(
            taskDTO.getId(),
            null,
            taskDTO.getName(),
            taskDTO.getDescription(),
            taskDTO.getCompletionDate(),
            taskDTO.isDone()
        );
    }
}