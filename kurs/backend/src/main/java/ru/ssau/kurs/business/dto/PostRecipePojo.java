package ru.ssau.kurs.business.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRecipePojo {
    private List<AssetInPojo> assetIns;
    private RecipeResultPojo assetOut;
}
