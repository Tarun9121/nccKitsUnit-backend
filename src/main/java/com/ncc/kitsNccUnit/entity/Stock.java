package com.ncc.kitsNccUnit.entity;

import com.ncc.kitsNccUnit.ItemType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String itemCode;
    private String itemName;
    private int quantity;             // Total quantity of the item
    private int availableQuantity;    // Available quantity of the item
    private double cost;
    private LocalDate receivedDate;
    private int years;
    private ItemType itemType;
}
