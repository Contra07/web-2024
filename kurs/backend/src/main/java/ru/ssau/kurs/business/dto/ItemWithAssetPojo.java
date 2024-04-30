package ru.ssau.kurs.business.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.ssau.kurs.data.entity.Account;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Item;
import ru.ssau.kurs.data.entity.Recipe;

@AllArgsConstructor
@Getter
@Setter
public class ItemWithAssetPojo {
    private UUID id;
    private UUID accountId;
    private LocalDateTime createdOn;
    private AssetPojo asset;

    public static ItemWithAssetPojo fromEntity(Item entity){
        return new ItemWithAssetPojo(entity.getId(), entity.getAccount().getId(), entity.getCreatedOn(), AssetPojo.fromEntity(entity.getAsset()));
    }

    public static Item toEntity(ItemWithAssetPojo pojo, Account account, Set<Item> items, Set<Recipe> recipes, Set<AssetIn> assetIns){
        return new Item(pojo.id, AssetPojo.toEntity(pojo.asset, items, recipes, assetIns), account, pojo.createdOn);
    }
}
