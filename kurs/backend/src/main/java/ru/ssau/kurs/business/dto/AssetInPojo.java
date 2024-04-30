package ru.ssau.kurs.business.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Recipe;

@AllArgsConstructor
@Getter
@Setter
public class AssetInPojo {
    private UUID id;
    private AssetPojo asset;
    private UUID recipeId;
    private int column;
    private int row;
    private int quantity;

    public static AssetInPojo fromEntity(AssetIn assetIn){
        return new AssetInPojo(assetIn.getId(), AssetPojo.fromEntity(assetIn.getAsset()), assetIn.getRecipe().getId(), assetIn.getColumn(), assetIn.getRow(), assetIn.getQuantity());
    }

    public static AssetIn toEntity(AssetInPojo pojo, Asset asset, Recipe recipe){
        return new AssetIn(pojo.getId(), asset, recipe, pojo.getQuantity(), pojo.getColumn(), pojo.getRow());
    }
}
