package ru.ssau.kurs.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.exception.DuplicateException;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.business.service.AssetService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/assets")
@AllArgsConstructor
public class AssetsController {
    private final AssetService service;

    @GetMapping("")
    public ResponseEntity<List<AssetPojo>> getAllAssets() {
        return ResponseEntity.ok().body(service.readAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetPojo> getAsset(@PathVariable String id) {
        try{
            AssetPojo result = service.readAsset(UUID.fromString(id));
            if(result == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    

    @PostMapping("")
    public ResponseEntity<AssetPojo> postAsset(@RequestBody AssetPojo asset) {
        try{
            AssetPojo result = service.createAsset(asset);
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException | DuplicateException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AssetPojo> putAsset(@PathVariable String id, @RequestBody AssetPojo asset) {
        try{
            AssetPojo result = service.updateAsset(UUID.fromString(id), asset);
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException | DuplicateException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable String id){
        try{
            if(service.deleteAsset(UUID.fromString(id))){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Asset delete error");
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
