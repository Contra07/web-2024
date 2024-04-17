package ru.ssau.todo_3.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ssau.todo_3.data.entity.Task;

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

    public Task toEntity() {
        return new Task(
            this.getId(),
            null,
            this.getName(),
            this.getDescription(),
            this.getCompletionDate(),
            this.isDone()
        );
    }
}