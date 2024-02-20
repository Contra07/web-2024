package ru.ssau.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import ru.ssau.todo.api.model.Project;

public class ProjectDAO implements IDataAccessObject<Project> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    @NonNull
    private ProjectRowMapper rowMapper;

    @Override
    public void create(Project t) {
        // TODO Auto-generated method stub
    }

    @Override
    public void delete(int id) {
        
    }

    @Override
    public Optional<Project> get(int id) {
        String sql = "SELECT * FROM Project WHERE Id = ?";
        Project course = null;
        try {
            course = jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch (DataAccessException ex) {
            course = null;
        }
        return Optional.ofNullable(course);
    }

    @Override
    public List<Project> list() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Project t, int id) {
        // TODO Auto-generated method stub
        
    }
}
