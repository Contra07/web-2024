package ru.ssau.kurs.data.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.entity.Recipe;
import java.util.List;




public interface IAssetInRepository extends JpaRepository<AssetIn, UUID>{
    @Transactional
    void deleteByAsset(Asset asset);
    @Transactional
    void deleteByRecipe(Recipe recipe);
    Optional<AssetIn> findAllByRecipe_IdAndId(UUID recipeId, UUID id);
    List<AssetIn> findAllByRecipe_Id(UUID recipeId);
    List<AssetIn> findAllByAsset_IdAndColumnAndRow(UUID asset_Id, int column, int row);
}
