package ru.ssau.todo_4.data.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@jakarta.persistence.Entity
@Table(name = "project", schema = "public")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Project extends Entity{
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public Project(UUID id, String name, String description, LocalDate startDate, LocalDate endDate, Set<Task> tasks){
        super(id);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
    }

    public void copyNotNullFields(Project project){
        setId(project.getId());
        setName(project.getName());
        setDescription(project.getDescription());
        setStartDate(project.getStartDate());
        setEndDate(project.getEndDate());
    }
}