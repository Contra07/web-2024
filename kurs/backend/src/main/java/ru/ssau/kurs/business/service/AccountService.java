package ru.ssau.kurs.business.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AccountPojo;
import ru.ssau.kurs.data.repository.IAccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final IAccountRepository repository;

    public AccountPojo readAccount(UUID id){
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return AccountPojo.fromEntity(repository.findById(id).orElse(null));
    }

    public AccountPojo readAccount(String name){
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        return AccountPojo.fromEntity(repository.findByUsername(name).orElse(null));
    }
}
