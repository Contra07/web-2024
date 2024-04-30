package ru.ssau.kurs.data.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "item", schema = "public")
public class Item extends Entity{
    public Item(UUID id, Asset asset, Account account, LocalDateTime createdOn){
        this.id = id;
        this.account = account;
        this.asset = asset;
        this.createdOn = createdOn;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "assetid", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Asset asset;
    @ManyToOne()
    @JoinColumn(name = "accountid", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Account account;
    @Column(name="createdon")
    private LocalDateTime createdOn;
}
