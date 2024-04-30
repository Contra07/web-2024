package ru.ssau.kurs.data.entity;

import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.mapping.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "recipe", schema = "public")
public class Recipe extends Entity {
    public Recipe(UUID id, Asset asset, int quantity, Set<AssetIn> assetIns) {
        this.id = id;
        this.asset = asset;
        this.quantity = quantity;
        this.assetIns = assetIns;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "assetoutid", foreignKey = @ForeignKey(ConstraintMode.PROVIDER_DEFAULT))
    private Asset asset;
    @Column(name = "quantity")
    private int quantity;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "recipe")
    private Set<AssetIn> assetIns;
}
