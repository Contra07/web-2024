package ru.ssau.kurs.data.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.NonNull;

import jakarta.transaction.Transactional;
import ru.ssau.kurs.data.entity.Asset;
import java.util.List;


public interface IAssetRepository  extends JpaRepository<Asset, UUID>{
    List<Asset> findByName(String name);
    @Modifying
    @Transactional
    void deleteById(@NonNull UUID id);
}
