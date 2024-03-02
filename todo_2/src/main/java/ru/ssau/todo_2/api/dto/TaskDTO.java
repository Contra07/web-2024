package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private UUID id;
    private UUID projectId;
    private String name;
    private String description;
    private LocalDate completionDate;
    private boolean done;
}