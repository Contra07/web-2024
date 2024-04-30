package ru.ssau.kurs.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import ru.ssau.kurs.data.entity.Item;
import java.util.List;
import ru.ssau.kurs.data.entity.Asset;


public interface IItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByAccount_Id(UUID accountId);

    List<Item> findByAccount_IdAndAsset_Id(UUID accountId, UUID assetId);

    @Query(value = "select a.id as AssetId, count(I.id) as Number from item as I join asset as A on I.assetid = a.id and i.accountid = :accountId group by a.id ", nativeQuery = true)
    List<ItemCounted> countItemsByAssets(UUID accountId);

    boolean existsByIdAndAccount_Id(UUID id, UUID accountId);

    @Transactional
    void deleteByAsset(Asset asset);

    @Transactional
    void deleteByIdAndAccount_Id(UUID id, UUID accountId);
    public interface ItemCounted {
        UUID getAssetId();
        Long getNumber();
    }

}
