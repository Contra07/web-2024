package ru.ssau.kurs.business.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetInPojo;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.dto.RecipeResultPojo;
import ru.ssau.kurs.business.exception.DuplicateException;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Recipe;
import ru.ssau.kurs.data.repository.IAssetInRepository;
import ru.ssau.kurs.data.repository.IAssetRepository;
import ru.ssau.kurs.data.repository.IRecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final IRecipeRepository repository;
    private final IAssetInRepository assetInRepository;
    private final IAssetRepository assetRepository;

    public List<RecipeResultPojo> readAllRecipes() {
        List<RecipeResultPojo> result = new ArrayList<>();
        for (Recipe recipe : repository.findAll()) {
            result.add(RecipeResultPojo.fromEntity(recipe));
        }
        return result;
    }

    public RecipeResultPojo readRecipe(UUID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return RecipeResultPojo.fromEntity(repository.findById(id).orElse(null));
    }

    public RecipeResultPojo createRecipe(List<AssetInPojo> assetIns, RecipeResultPojo result)
            throws IllegalArgumentException, DuplicateException, NotExistsException {
        if (result == null) {
            throw new IllegalArgumentException("result cannot be null");
        }
        if (assetIns == null || assetIns.isEmpty()) {
            throw new IllegalArgumentException("assets in cannot be null or empty");
        }
        Optional<Asset> resultAsset = assetRepository.findById(result.getId());
        if (resultAsset.isEmpty()) {
            throw new NotExistsException("Result asset with this id does not exists");
        }
        List<AssetIn> assets = new ArrayList<>();
        for (AssetInPojo assetInPojo : assetIns) {
            Optional<Asset> asset = assetRepository.findById(assetInPojo.getAsset().getId());
            if (asset.isPresent()) {
                AssetIn assetIn = AssetInPojo.toEntity(assetInPojo, asset.get(), null);
                assets.add(assetIn);
            } else {
                throw new NotExistsException("Asset with this id does not exists");
            }
        }
        Recipe recipe = repository.saveAndFlush(RecipeResultPojo.toEntity(result, resultAsset.get(), null));
        for (AssetIn asset : assets) {
            asset.setRecipe(recipe);
            assetInRepository.saveAndFlush(asset);
        }

        return RecipeResultPojo.fromEntity(recipe);
    }

    public RecipeResultPojo updateRecipe(UUID id, List<AssetInPojo> assetIns, RecipeResultPojo result)
            throws IllegalArgumentException, NotExistsException, DuplicateException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (result == null) {
            throw new IllegalArgumentException("result cannot be null");
        }
        if (assetIns == null || assetIns.isEmpty()) {
            throw new IllegalArgumentException("assets in cannot be null or empty");
        }
        if (!repository.existsById(id)) {
            throw new NotExistsException("Recipe with this id does not exists");
        }
        Recipe recipe = repository.findById(id).get();
        for(AssetIn asset: recipe.getAssetIns()){
            assetInRepository.delete(asset);
            assetInRepository.flush();
        }
        recipe = repository.findById(id).get();
        recipe.setAssetIns(new HashSet<>());
        repository.saveAndFlush(recipe);
        Set<AssetIn> assets = new HashSet<>();
        for (AssetInPojo assetInPojo : assetIns) {
            Optional<Asset> asset = assetRepository.findById(assetInPojo.getAsset().getId());
            if (asset.isPresent()) {
                AssetIn assetIn = AssetInPojo.toEntity(assetInPojo, asset.get(), recipe);
                assetIn.setId(null);
                assets.add(assetIn);
                recipe.setAssetIns(assets);
                assetInRepository.saveAndFlush(assetIn);
                repository.saveAndFlush(recipe);
            } else {
                throw new NotExistsException("Asset with this id does not exists");
            }
        }
        //recipe.setAssetIns(assets);
        Asset asset = assetRepository.findById(result.getAsset().getId()).get();
        recipe.setAsset(asset);
        recipe = repository.saveAndFlush(recipe);
        return RecipeResultPojo.fromEntity(recipe);
    }

    public boolean deleteRecipe(UUID id) throws IllegalArgumentException, NotExistsException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new NotExistsException("Recipe with this id does not exists");
        }
        Recipe recipe = repository.findById(id).get();
        assetInRepository.deleteByRecipe(recipe);
        repository.deleteById(id);
        return !repository.existsById(id);
    }
}
