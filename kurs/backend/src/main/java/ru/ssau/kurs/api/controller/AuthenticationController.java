package ru.ssau.kurs.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.api.security.AuthService;
import ru.ssau.kurs.api.security.Credential;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService service;

    @PostMapping("")
    public ResponseEntity<Map<String,String>> getLogin(@RequestBody Credential cred){
        try {
            String token = service.authUser(cred);
            if(token != null){
               Map<String,String> response = new HashMap<String, String>();
               response.put("id", token);
               return ResponseEntity.ok().body(response);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login or password is incorrect");
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<Boolean> isAuth() {
        return ResponseEntity.ok().body(true);
    }
    
}
