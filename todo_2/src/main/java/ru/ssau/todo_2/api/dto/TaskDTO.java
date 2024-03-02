package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private long id;
    private long projectId;
    private String name;
    private String description;
    private Date completionDate;
    private LocalDate done;
}