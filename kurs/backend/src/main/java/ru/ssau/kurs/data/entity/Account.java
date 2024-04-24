package ru.ssau.kurs.data.entity;

import jakarta.persistence.Column;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends Entity{
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
}
