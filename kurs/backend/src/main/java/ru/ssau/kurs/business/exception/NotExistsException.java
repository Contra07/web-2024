package ru.ssau.kurs.business.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotExistsException extends Exception{ 
    public NotExistsException(String message){
        super(message);
    }
}
