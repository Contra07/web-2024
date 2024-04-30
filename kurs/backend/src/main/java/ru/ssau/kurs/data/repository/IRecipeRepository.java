package ru.ssau.kurs.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import ru.ssau.kurs.data.entity.Recipe;
import ru.ssau.kurs.data.entity.Asset;

import java.util.List;



public interface IRecipeRepository  extends JpaRepository<Recipe, UUID>{
    @Transactional
    void deleteByAsset(Asset asset);
    
    @Query(value = "select r.id from recipe r join assetin a on r.id = a.recipeid join item i on i.assetid = a.assetid and i.id = :itemId group by r.id", nativeQuery = true)
    List<UUID> recipesByItemId(UUID itemId);
    @Query(value = "select r.id from recipe r join assetin a on r.id = a.recipeid and a.id = :assetInId group by r.id", nativeQuery = true)
    List<UUID> recipesByAssetInId(UUID assetInId);
}
