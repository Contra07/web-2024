package ru.ssau.kurs.data.entity;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "asset", schema = "public")
public class Asset extends Entity{
    public Asset(UUID id, String name, String picturePath, Set<Item> items, Set<Recipe> recipes, Set<AssetIn> assetIns){
        this.id = id;
        this.name = name;
        this.picturePath = picturePath;
        this.assetIns = assetIns;
        this.items = items;
        this.recipes = recipes;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "picturepath")
    private String picturePath;
    @OneToMany(mappedBy = "asset")
    private Set<Item> items;
    @OneToMany(mappedBy = "asset")
    private Set<Recipe> recipes;
    @OneToMany(mappedBy = "asset")
    private Set<AssetIn> assetIns;
}
