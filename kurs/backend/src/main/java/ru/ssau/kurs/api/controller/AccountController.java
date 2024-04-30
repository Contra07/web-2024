package ru.ssau.kurs.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AccountPojo;
import ru.ssau.kurs.business.service.AccountService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService service;
    @GetMapping("/{id}")
    public ResponseEntity<AccountPojo> getAccount(@PathVariable String id) {
        try{
            AccountPojo result = service.readAccount(UUID.fromString(id));
            if(result == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<AccountPojo> getAccountByName(@RequestParam String name) {
        try{
            AccountPojo result = service.readAccount(name);
                if(result == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
}
