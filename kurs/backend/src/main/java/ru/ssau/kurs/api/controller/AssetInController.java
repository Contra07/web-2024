package ru.ssau.kurs.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetInPojo;
import ru.ssau.kurs.business.service.AssetInService;


@RestController
@RequestMapping("/api/recipes/{recipeId}/assets")
@RequiredArgsConstructor
public class AssetInController {
    private final AssetInService service;

    @GetMapping("")
    public ResponseEntity<List<AssetInPojo>> getAllAssets(@PathVariable String recipeId) {
        return ResponseEntity.ok().body(service.readAllAssetIns(UUID.fromString(recipeId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetInPojo> getAsset(@PathVariable String recipeId, @PathVariable String id) {
        try{
            AssetInPojo result = service.readAssetIn(UUID.fromString(recipeId), UUID.fromString(id));
            if(result == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
