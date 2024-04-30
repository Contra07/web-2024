package ru.ssau.kurs.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetInPojo;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.repository.IAssetInRepository;

@Service
@RequiredArgsConstructor
public class AssetInService {
    private final IAssetInRepository repository;

    public List<AssetInPojo> readAllAssetIns(UUID recipeId) {
        if (recipeId == null) {
            throw new IllegalArgumentException("recipeId cannot be null");
        }
        List<AssetInPojo> result = new ArrayList<>();
        for (AssetIn asset : repository.findAllByRecipe_Id(recipeId)) {
            result.add(AssetInPojo.fromEntity(asset));
        }
        return result;
    }

    public AssetInPojo readAssetIn(UUID recipeId, UUID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return AssetInPojo.fromEntity(repository.findAllByRecipe_IdAndId(recipeId, id).orElse(null));
    }
}
