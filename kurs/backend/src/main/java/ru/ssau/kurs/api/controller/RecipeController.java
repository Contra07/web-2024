package ru.ssau.kurs.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetInPojo;
import ru.ssau.kurs.business.dto.PostRecipePojo;
import ru.ssau.kurs.business.dto.PutRecipePojo;
import ru.ssau.kurs.business.dto.RecipeResultPojo;
import ru.ssau.kurs.business.exception.DuplicateException;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.business.service.RecipeService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService service;

    @GetMapping("")
    public ResponseEntity<List<RecipeResultPojo>> getAllRecipes() {
        return ResponseEntity.ok().body(service.readAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResultPojo> getRecipe(@PathVariable String id) {
        try{
            RecipeResultPojo result = service.readRecipe(UUID.fromString(id));
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
    public ResponseEntity<RecipeResultPojo> postRecipe(@RequestBody PostRecipePojo recipe) {
        try{
            RecipeResultPojo result = service.createRecipe(recipe.getAssetIns(), recipe.getAssetOut());
            if(result == null){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok().body(result);
        }
        catch(IllegalArgumentException | DuplicateException | NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResultPojo> putRecipe(@PathVariable String id, @RequestBody PutRecipePojo recipePojo) {
        try{
            RecipeResultPojo result = service.updateRecipe(UUID.fromString(id), recipePojo.getAssetIns(), recipePojo.getAssetOut());
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
    public ResponseEntity<String> deleteRecipe(@PathVariable String id){
        try{
            if(service.deleteRecipe(UUID.fromString(id))){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Recipe delete error");
        }
        catch(IllegalArgumentException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        catch(NotExistsException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
