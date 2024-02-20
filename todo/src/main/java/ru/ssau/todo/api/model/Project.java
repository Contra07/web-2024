package ru.ssau.todo.api.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Project(int id){
        this.id = id;
    }
}
