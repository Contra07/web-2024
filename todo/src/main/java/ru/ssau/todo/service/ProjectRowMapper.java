package ru.ssau.todo.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import ru.ssau.todo.api.model.Project;

@Component
public class ProjectRowMapper implements RowMapper<Project>{

    @Override
    public Project mapRow(@NonNull ResultSet resultSet, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("Id"));
        project.setDescription(resultSet.getString("Description"));
        project.setStartDate(resultSet.getDate("StartDate"));
        project.setEndDate(resultSet.getDate("EndDate"));
        return project;
    }


}
