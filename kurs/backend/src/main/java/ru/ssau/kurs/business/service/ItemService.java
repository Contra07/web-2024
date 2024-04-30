package ru.ssau.kurs.business.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.dto.ItemWithAssetPojo;
import ru.ssau.kurs.business.dto.ItemsGroupedPojo;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.data.entity.Account;
import ru.ssau.kurs.data.entity.Asset;
import ru.ssau.kurs.data.entity.Item;
import ru.ssau.kurs.data.repository.IAccountRepository;
import ru.ssau.kurs.data.repository.IAssetRepository;
import ru.ssau.kurs.data.repository.IItemRepository;
import ru.ssau.kurs.data.repository.IItemRepository.ItemCounted;

@Service
@RequiredArgsConstructor
public class ItemService {
    private static final Random rnd = new Random();
    private final IItemRepository itemRepository;
    private final IAssetRepository assetRepository;
    private final IAccountRepository accountRepository;

    public ItemWithAssetPojo readItem(UUID id) throws IllegalArgumentException{
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent())
        {
            return ItemWithAssetPojo.fromEntity(item.get());
        }
        else{
            return null;
        }
    }


    public List<ItemWithAssetPojo> readItemsByAccount(UUID accountId) throws IllegalArgumentException{
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        List<ItemWithAssetPojo> result = new ArrayList<>();
        for (Item item : itemRepository.findByAccount_Id(accountId)) {
            result.add(ItemWithAssetPojo.fromEntity(item));
        }
        return result;
    }

    public List<ItemWithAssetPojo> readItemsByAccountAndAsset(UUID accountId, UUID assetId) throws IllegalArgumentException{
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        if (assetId == null) {
            throw new IllegalArgumentException("asset id cannot be null");
        }
        List<ItemWithAssetPojo> result = new ArrayList<>();
        for (Item item : itemRepository.findByAccount_IdAndAsset_Id(accountId, assetId)) {
            result.add(ItemWithAssetPojo.fromEntity(item));
        }
        return result;
    }

    public ItemWithAssetPojo createRandomItem(UUID accountId) throws NotExistsException, IllegalArgumentException{
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){
            throw new NotExistsException(String.format("account with id %s does not exist", accountId.toString()));
        }
        List<Asset> assets = assetRepository.findAll();
        if(assets.isEmpty()){
            throw new NotExistsException(String.format("There is no assets"));
        }
        int index = rnd.nextInt(assets.size());
        Item item = new Item(null, assets.get(index), account.get(), LocalDateTime.now());
        return ItemWithAssetPojo.fromEntity(itemRepository.saveAndFlush(item));
    }    

    public ItemWithAssetPojo createItem(UUID assetId, UUID accountId) throws NotExistsException, IllegalArgumentException{
        if (assetId == null) {
            throw new IllegalArgumentException("asset id cannot be null");
        }
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        Optional<Asset> asset = assetRepository.findById(assetId);
        if (asset.isEmpty()){
            throw new NotExistsException(String.format("asset with id %s does not exist", assetId.toString()));
        }
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){
            throw new NotExistsException(String.format("account with id %s does not exist", accountId.toString()));
        }
        Item item = new Item(null, asset.get(), account.get(), LocalDateTime.now());
        return ItemWithAssetPojo.fromEntity(itemRepository.saveAndFlush(item));
    }

    public boolean deleteItem(UUID accountId, UUID id){
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        if (id == null) {
            throw new IllegalArgumentException("asset id cannot be null");
        }
        itemRepository.deleteByIdAndAccount_Id(id, accountId);
        return !itemRepository.existsByIdAndAccount_Id(id, accountId);
    }

    public List<ItemsGroupedPojo> readItemsGrouped(UUID accountId) throws IllegalArgumentException, NotExistsException{
        if (accountId == null) {
            throw new IllegalArgumentException("account id cannot be null");
        }
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()){
            throw new NotExistsException(String.format("account with id %s does not exist", accountId.toString()));
        }
        List<ItemsGroupedPojo> result = new ArrayList<ItemsGroupedPojo>();
        List<ItemCounted> items =  itemRepository.countItemsByAssets(accountId);
        for(ItemCounted item : items){
            Optional<Asset> asset = null;
            if(item.getAssetId() != null){
                asset = assetRepository.findById(item.getAssetId());
                if(asset.isPresent()){
                    result.add(new ItemsGroupedPojo(AssetPojo.fromEntity(asset.get()), item.getNumber()));
                }
            }
        }
        return result;
    }

}
