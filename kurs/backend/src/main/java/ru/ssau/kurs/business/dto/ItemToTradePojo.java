package ru.ssau.kurs.business.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemToTradePojo {
    private UUID id;
    private int column;
    private int row;
    private int quantity;
}