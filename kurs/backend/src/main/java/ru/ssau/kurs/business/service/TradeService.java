package ru.ssau.kurs.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.dto.ItemToTradePojo;
import ru.ssau.kurs.business.dto.ItemWithAssetPojo;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.data.entity.AssetIn;
import ru.ssau.kurs.data.entity.Item;
import ru.ssau.kurs.data.entity.Recipe;
import ru.ssau.kurs.data.repository.IAssetInRepository;
import ru.ssau.kurs.data.repository.IItemRepository;
import ru.ssau.kurs.data.repository.IRecipeRepository;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final IRecipeRepository recipeRepository;
    private final IItemRepository itemRepository;
    private final ItemService itemService;
    private final IAssetInRepository assetInRepository;
    public AssetPojo readTradeResult(List<ItemToTradePojo> pojos) throws NotExistsException{
        if (pojos == null || pojos.isEmpty()) {
            throw new IllegalArgumentException("items to trade cannot be null or empty");
        }
        //List<AssetIn> assets = getAssetIns(pojos);
        Recipe recipe = getRecipeForItems(pojos);
        if(recipe == null ){
            return null;
        }
        return AssetPojo.fromEntity(recipe.getAsset());
    }

    public ItemWithAssetPojo tradeItems(UUID accountId, List<ItemToTradePojo> pojos) throws NotExistsException{
        if (pojos == null || pojos.isEmpty()) {
            throw new IllegalArgumentException("items to trade cannot be null or empty");
        }
        if (accountId == null ) {
            throw new IllegalArgumentException("account id cannot be null or empty");
        }
        List<AssetIn> assets = getAssetIns(pojos);
        Recipe recipe = getRecipeForItems(pojos);
        if(recipe == null && !MatchAssetInsWithRecipe(recipe, assets)){
            return null;
        }
        return trade(recipe, accountId, pojos);
    }

    private ItemWithAssetPojo trade(Recipe recipe, UUID accountId, List<ItemToTradePojo> items) throws IllegalArgumentException, NotExistsException{
        for(ItemToTradePojo item: items){
            itemService.deleteItem(accountId, item.getId());
        }
        return itemService.createItem(recipe.getAsset().getId(), accountId);
    }

    private boolean MatchAssetInsWithRecipe(Recipe recipe, List<AssetIn> assets){
        for(AssetIn asset: assets){
            if(recipe.getAssetIns().stream().anyMatch((assetIn) -> {
                return assetIn.getColumn() != asset.getColumn() ||
                    assetIn.getRow() != asset.getRow() ||
                    assetIn.getQuantity() != asset.getQuantity() ||
                    assetIn.getAsset().getId() != assetIn.getAsset().getId();
            })){
                return false;
            }
        }
        return true;
    }
    private Recipe getRecipeForItems(List<ItemToTradePojo> pojos) throws NotExistsException{
        List<Recipe> matchRecipes = recipeRepository.findAllById(recipeRepository.recipesByItemId(pojos.get(0).getId()));
        List<Recipe> result = new ArrayList<>(matchRecipes);
        for(ItemToTradePojo pojo: pojos){
            if(pojo.getId() == null){
                throw new NotExistsException(String.format("Item with id null"));
            }
            List<Recipe> recipes = recipeRepository.findAllById(recipeRepository.recipesByItemId(pojo.getId()));
            for(Recipe recipe: matchRecipes){
                if(!recipes.contains(recipe)){
                    result.remove(recipe);
                }
            }
        }
        if(result.isEmpty()){
            return null;
        }
        Stream<Recipe> str = result.stream().filter((recipe) -> {
            return recipe.getAssetIns().size() == pojos.size();
        });
        Optional<Recipe> rec = str.findFirst();
        if(rec.isPresent()){
            return rec.get();
        }
        else{
            return null;
        }
    }

    private List<AssetIn> getAssetIns(List<ItemToTradePojo> pojos) throws NotExistsException{
        List<AssetIn> assetIns = new ArrayList<>();
        for(ItemToTradePojo pojo: pojos){
            if(pojo.getId() != null){
                Optional<Item> item = itemRepository.findById(pojo.getId());
                if(item.isPresent()){
                    // AssetIn asset = new AssetIn(null, item.get().getAsset(), null, pojo.getQuantity(), pojo.getColumn(), pojo.getRow());
                    assetIns.addAll(assetInRepository.findAllByAsset_IdAndColumnAndRow(item.get().getAsset().getId(), pojo.getColumn(), pojo.getRow()));
                }
                else{
                    throw new NotExistsException(String.format("no item with id %s", pojo.getId()));
                }
            }
            else{
                throw new NotExistsException(String.format("no item with id null"));
            }
        }
        return assetIns;
    }
}
