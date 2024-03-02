package ru.ssau.todo_2.api.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}