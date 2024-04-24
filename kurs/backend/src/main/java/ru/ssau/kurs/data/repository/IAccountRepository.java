package ru.ssau.kurs.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ssau.kurs.data.entity.Account;

public interface IAccountRepository extends JpaRepository<Account, UUID>{
    
}
