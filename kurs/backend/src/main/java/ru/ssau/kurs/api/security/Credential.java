package ru.ssau.kurs.api.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Credential {
    private String username;
    private String password;
}
