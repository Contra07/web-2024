package ru.ssau.kurs.data.entity;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@jakarta.persistence.Entity
@Table(name = "assetin", schema = "public")
public class AssetIn extends Entity{
    public AssetIn(UUID id, Asset asset, Recipe recipe, int quantity, int column, int row) {
        this.id = id;
        this.asset = asset;
        this.recipe = recipe;
        this.quantity = quantity;
        this.row = row;
        this.column = column;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "assetid", foreignKey = @ForeignKey(ConstraintMode.PROVIDER_DEFAULT))
    private Asset asset;
    @ManyToOne()
    @JoinColumn(name = "recipeid", foreignKey = @ForeignKey(ConstraintMode.PROVIDER_DEFAULT))
    private Recipe recipe;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "col")
    private int column;
    @Column(name = "row")
    private int row;
}
