package ru.ssau.kurs.business.dto;

import java.util.Set;
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
public class RecipeResultPojo {
    private UUID id;
    private AssetPojo asset;
    private int quantity;

    public static Recipe toEntity(RecipeResultPojo recipe, Asset assetOut, Set<AssetIn> assetIns){
        return new Recipe(recipe.getId(), assetOut, recipe.getQuantity(), assetIns);
    }

    public static RecipeResultPojo fromEntity(Recipe recipe){
        return new RecipeResultPojo(recipe.getId(), AssetPojo.fromEntity(recipe.getAsset()), recipe.getQuantity());
    }
}
