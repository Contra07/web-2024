package ru.ssau.todo_2.api.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private long id;
    private long projectId;
    private String name;
    private String description;
    private Date completionDate;
    private boolean done;
}
