package ru.ssau.kurs.business.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateException extends Exception{
    public DuplicateException(String message){
        super(message);
    }
}

