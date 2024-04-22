package ru.ssau.todo_4.data.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@jakarta.persistence.Entity
@Table(name = "task", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Task{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 100)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projectid", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Project project;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private LocalDate completionDate;
    @Column(name = "done")
    private boolean done;
    public Task(UUID id, Project project, String name, String description, LocalDate completionDate, boolean done) {
        this.id=id;
        this.project = project;
        this.name = name;
        this.description = description;
        this.completionDate = completionDate;
        this.done = done;
    }

    
}
