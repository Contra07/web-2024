package ru.ssau.kurs.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.exception.DuplicateException;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.repository.IAssetInRepository;
import ru.ssau.kurs.data.repository.IAssetRepository;
import ru.ssau.kurs.data.repository.IItemRepository;
import ru.ssau.kurs.data.repository.IRecipeRepository;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final IAssetRepository repository;
    private final IAssetInRepository assetInRepository;
    private final IRecipeRepository recipeRepository;
    private final IItemRepository itemRepository;
    public List<AssetPojo> readAllAssets() {
        List<AssetPojo> result = new ArrayList<>();
        for (Asset asset : repository.findAll()) {
            result.add(AssetPojo.fromEntity(asset));
        }
        return result;
    }

    public AssetPojo readAsset(UUID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return AssetPojo.fromEntity(repository.findById(id).orElse(null));
    }

    public AssetPojo createAsset(AssetPojo pojo) throws IllegalArgumentException, DuplicateException {
        if (pojo == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (repository.findByName(pojo.getName()).stream().anyMatch(
                (existingAsset) -> {
                    return existingAsset.getName().equals(pojo.getName());
                })) {
            throw new DuplicateException("Asset name must be unique");
        }
        Asset asset = AssetPojo.toEntity(pojo, null, null, null);
        asset = repository.saveAndFlush(asset);
        return AssetPojo.fromEntity(asset);
    }

    public AssetPojo updateAsset(UUID id, AssetPojo pojo)
            throws IllegalArgumentException, NotExistsException, DuplicateException {
        if (pojo == null) {
            throw new IllegalArgumentException("Asset cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new NotExistsException("Asset with this id does not exists");
        }
        if (repository.findByName(pojo.getName())
                .stream().anyMatch(
                        (existingAsset) -> {
                            return existingAsset.getId() == id;
                        })) {
            throw new DuplicateException("Asset name must be unique");
        }
        Asset oldAsset = repository.findById(id).get();
        Asset newAsset = AssetPojo.toEntity(pojo, oldAsset.getItems(), oldAsset.getRecipes(), oldAsset.getAssetIns());
        newAsset.setId(id);
        newAsset = repository.saveAndFlush(newAsset);
        return AssetPojo.fromEntity(newAsset);
    }

    public boolean deleteAsset(UUID id) throws IllegalArgumentException, NotExistsException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new NotExistsException("Asset with this id does not exists");
        }
        Asset asset = repository.findById(id).get();
        assetInRepository.deleteByAsset(asset);
        recipeRepository.deleteByAsset(asset);
        itemRepository.deleteByAsset(asset);
        repository.deleteById(id);
        // repository.delete(asset);
        return !repository.existsById(id);
    }
}
