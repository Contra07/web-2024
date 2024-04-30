package ru.ssau.kurs.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemsGroupedPojo {
    private AssetPojo asset;
    private Long itemsNumber;
}
