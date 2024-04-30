package ru.ssau.kurs.data.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "account", schema = "public")
public class Account extends Entity{
    public Account(UUID id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
