package com.ncc.kitsNccUnit.dto;

import com.ncc.kitsNccUnit.ItemType;
import lombok.Data;

@Data
public class StockDTO {
    private int id;
    private String itemCode;
    private String itemName;
    private int quantity;
    private double cost;
    private String receivedDate;
    private int years;
    private ItemType itemType;
}
