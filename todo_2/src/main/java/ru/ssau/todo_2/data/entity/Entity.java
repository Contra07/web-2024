package ru.ssau.todo_2.data.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 100)
    private UUID id;
}
