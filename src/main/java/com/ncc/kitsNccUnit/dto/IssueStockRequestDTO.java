package com.ncc.kitsNccUnit.dto;

import com.ncc.kitsNccUnit.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class IssueStockRequestDTO {
    private String itemCode;
    private String itemName;
    private String regimentalNo;
    private String cadetName;
    private String issueDate;
    private String returnDate;
    private ItemType itemType;
    private int quantity;
    private String remarks; // Optional field
}