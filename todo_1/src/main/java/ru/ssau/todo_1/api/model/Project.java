package ru.ssau.todo_1.api.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
}
