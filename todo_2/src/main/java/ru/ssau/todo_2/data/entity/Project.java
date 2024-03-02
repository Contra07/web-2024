package ru.ssau.todo_2.data.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 100)
    private UUID id;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "description",columnDefinition="TEXT")
    private String description;
    @Temporal(TemporalType.DATE)
    @Column(name = "startdate")
    private LocalDate startDate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
}