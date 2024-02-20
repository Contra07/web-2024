package ru.ssau.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import ru.ssau.todo.api.model.Project;

@Repository
public class ProjectDAO implements IDataAccessObject<Project> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @NonNull
    private ProjectRowMapper rowMapper;

    @Override
    public void Create(Project project) {
        String sql = "INSERT INTO public.project (description = ?, startdate = ?, enddate = ?) VALUES ( ?, ?, ?)";
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
        String sql = "DELETE FROM public.project WHERE id = ?";
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
        String sql = "SELECT * FROM public.project WHERE id = ?";
        Project result = null;
        try {
            result = jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch (DataAccessException ex) {
            result = null;
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<Project> List() {
        String sql = "SELECT * FROM public.project";
        try {
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void Update(Project project, int id) {
        String sql = "UPDATE public.project SET description = ?, startdate = ?, enddate = ? WHERE id = ?";
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
