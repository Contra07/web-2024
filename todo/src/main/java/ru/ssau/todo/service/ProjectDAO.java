package ru.ssau.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.ssau.todo.api.model.Project;

public class ProjectDAO implements IDataAccessObject<Project> {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void Create(Project project) {
        String sql = "INSERT INTO public.\"Project\" (\"Description\" = ?, \"StartDate\" = ?, \"EndDate\" = ?) VALUES ( ?, ?, ?)";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, project.getDescription(), project.getStartDate(), project.getEndDate());
        }
        catch (DataAccessException ex) {
            
        }
        if(result == 0) {
            
        }
    }

    @Override
    public void Delete(int id) {
        String sql = "DELETE FROM public.\"Project\" WHERE \"Id\" = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql);
        }
        catch (DataAccessException ex) {
            
        }
        if(result == 0) {
            
        }
    }

    @Override
    public Optional<Project> Get(int id) {
        String sql = "SELECT * FROM public.\"Project\" WHERE \"Id\" = ?";
        Project result = null;
        try {
            result = jdbcTemplate.queryForObject(sql, Project.class, id);
        }
        catch (DataAccessException ex) {
            result = null;
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Project> List() {
        String sql = "SELECT * FROM public.\"Project\"";
        try {
            return jdbcTemplate.queryForList(sql, Project.class);
        }
        catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void Update(Project project, int id) {
        String sql = "UPDATE public.\"Project\" SET \"Description\" = ?, \"StartDate\" = ?, \"EndDate\" = ? WHERE \"Id\" = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, project.getDescription(), project.getStartDate(), project.getEndDate(), id);
        }
        catch (DataAccessException ex) {
            
        }
        if(result == 0) {
            
        }
        
    }
}
