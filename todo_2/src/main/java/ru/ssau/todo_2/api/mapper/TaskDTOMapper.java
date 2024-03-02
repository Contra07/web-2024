package ru.ssau.todo_2.api.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import ru.ssau.todo_2.api.dto.TaskDTO;
import ru.ssau.todo_2.data.entity.Task;

@Component
public class TaskDTOMapper implements Function<Task, TaskDTO>{

    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
            task.getId(),
            task.getProjectId(),
            task.getName(),
            task.getDescription(),
            task.getCompletionDate(),
            task.isDone()
        );
    }

}
