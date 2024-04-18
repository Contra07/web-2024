package ru.ssau.todo_1.service;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import ru.ssau.todo_1.api.model.Project;

@Repository
public class ProjectDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedjdbcTemplate;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @NonNull
    private ProjectRowMapper rowMapper;

    public Optional<Project> create(Project project) {
        String sql = "INSERT INTO public.project (name, description, startdate, enddate) VALUES ( ?, ?, ?, ?) returning id" ;
        int result = 0;
        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class, project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate());
        }
        catch (DataAccessException ex) {
            return Optional.empty();
        }
        if(result == 0) {
            return Optional.empty();
        }
        return get(result);
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM public.project WHERE id = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, id);
        }
        catch (DataAccessException ex) {
            return false;
        }
        return result != 0;
    }

    public Optional<Project> get(int id) {
        String sql = "SELECT * FROM public.project WHERE id = ?";
        Project result = null;
        try {
            result = jdbcTemplate.queryForObject(sql, rowMapper, id);
        }
        catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.ofNullable(result);
    }

    public List<Project> list() {
        String sql = "SELECT * FROM public.project";
        try {
            return jdbcTemplate.query(sql, rowMapper);
        }
        catch (DataAccessException ex) {
            return null;
        }
    }

    public List<Project> list(Date startDate, Date endDate) {
        String sql = "SELECT * FROM public.project WHERE startdate > :start_date AND endDate <= :end_date";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("start_date", startDate, Types.DATE);
        namedParameters.addValue("end_date", endDate, Types.DATE);
        try {
            return namedjdbcTemplate.query(sql, namedParameters, rowMapper);
        }
        catch (DataAccessException ex) {
            return null;
        }
    }


    public boolean update(Project project, int id) {
        String sql = "UPDATE public.project SET name = ?, description = ?, startdate = ?, enddate = ? WHERE id = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate(), id);
        }
        catch (DataAccessException ex) {
            return false;
        }
        return result != 0;
    }
}
