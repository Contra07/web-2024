package ru.ssau.kurs.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.dto.ItemWithAssetPojo;
import ru.ssau.kurs.business.dto.ItemsGroupedPojo;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.business.service.ItemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account/{accountId}/items")
public class ItemController {
    private final ItemService service;
    @GetMapping("")
    public ResponseEntity<List<ItemWithAssetPojo>> getItemsByAccount(@PathVariable String accountId) {
        try{
            return ResponseEntity.ok(service.readItemsByAccount(UUID.fromString(accountId)));
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<ItemWithAssetPojo>> getItemsByAccountAndAsset(@PathVariable String accountId, @PathVariable String assetId) {
        try{
            return ResponseEntity.ok(service.readItemsByAccountAndAsset(UUID.fromString(accountId), UUID.fromString(assetId)));
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemWithAssetPojo> getItem(@PathVariable String id, @PathVariable String accountId){
        try{
            ItemWithAssetPojo result = service.readItem(UUID.fromString(id));
            if(result == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping("/random")
    public ResponseEntity<ItemWithAssetPojo> postRandomItem(@PathVariable String accountId){
        try{
            ItemWithAssetPojo result = service.createRandomItem(UUID.fromString(accountId));
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    @PostMapping("")
    public ResponseEntity<ItemWithAssetPojo> postItem(@PathVariable String accountId, @RequestParam(name = "assetid") String assetId){
        try{
            ItemWithAssetPojo  result = service.createItem(UUID.fromString(assetId) , UUID.fromString(accountId));
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/groups")
    public ResponseEntity<List<ItemsGroupedPojo>> getItemsGrouped(@PathVariable String accountId) {
        try{
            return ResponseEntity.ok(service.readItemsGrouped(UUID.fromString(accountId)));
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String accountId, @PathVariable String id){
        try{
            if(service.deleteItem(UUID.fromString(accountId), UUID.fromString(id))){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Asset delete error");
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
