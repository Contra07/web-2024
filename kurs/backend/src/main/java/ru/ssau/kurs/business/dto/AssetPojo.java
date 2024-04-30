package ru.ssau.kurs.business.dto;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Item;
import ru.ssau.kurs.data.entity.Recipe;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetPojo {
    private UUID id;
    private String name;
    private String picturePath;

    public static Asset toEntity(AssetPojo pojo, Set<Item> items, Set<Recipe> recipes, Set<AssetIn> assetIns){
        if (pojo != null){
            return new Asset(pojo.id, Optional.of(pojo.name).orElse(""),  Optional.of(pojo.picturePath).orElse(""),items,recipes,assetIns);
        }
        return null;
    }

    public static AssetPojo fromEntity(Asset entity){
        if (entity != null){
            return new AssetPojo(entity.getId(), entity.getName(), entity.getPicturePath());
        }
        return null;
    }
}
