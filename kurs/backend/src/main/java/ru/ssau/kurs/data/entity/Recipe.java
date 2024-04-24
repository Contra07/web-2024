package ru.ssau.kurs.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
@Table(name = "recipe", schema = "public")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Recipe extends Entity{
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "assetid", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Asset asset;
    @Column(name = "quantity")
    private int quantity;
}
