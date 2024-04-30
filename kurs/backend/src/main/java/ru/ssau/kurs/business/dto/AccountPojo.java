package ru.ssau.kurs.business.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.kurs.data.entity.Account;

@AllArgsConstructor
@Getter
@Setter
public class AccountPojo {
    private UUID id;
    private String name;

    public static AccountPojo fromEntity(Account entity){
        if (entity != null){
            return new AccountPojo(entity.getId(), entity.getUsername());
        }
        return null;
    }
}
