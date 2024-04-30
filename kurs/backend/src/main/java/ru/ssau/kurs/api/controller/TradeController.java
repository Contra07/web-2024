package ru.ssau.kurs.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import io.micrometer.core.ipc.http.HttpSender.Response;
import lombok.RequiredArgsConstructor;
import ru.ssau.kurs.business.dto.AssetPojo;
import ru.ssau.kurs.business.dto.ItemToTradePojo;
import ru.ssau.kurs.business.dto.ItemWithAssetPojo;
import ru.ssau.kurs.business.exception.NotExistsException;
import ru.ssau.kurs.business.service.TradeService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService service;

    @PostMapping("/{accountId}")
    public ResponseEntity<ItemWithAssetPojo> postTrade (@PathVariable String accountId, @RequestBody List<ItemToTradePojo> items) {
        try {
            ItemWithAssetPojo result = service.tradeItems(UUID.fromString(accountId), items);
            if(result != null){
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.noContent().build();
        } 
        catch (NotExistsException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<AssetPojo> postCheckTrade (@RequestBody List<ItemToTradePojo> items) {
        try {
            AssetPojo result = service.readTradeResult(items);
            if(result != null){
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.noContent().build();
        } 
        catch (NotExistsException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
