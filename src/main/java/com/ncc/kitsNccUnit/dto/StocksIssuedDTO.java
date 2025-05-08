package com.ncc.kitsNccUnit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StocksIssuedDTO {
    private int id;
    private StockDTO stock;   // Reference to StockDTO
    private CadetDTO cadet;   // Reference to CadetDTO
    private String issuedAt;
    private String returnDate;
    private String returnedAt;
    private String status;
    private int quantity; // New field for the quantity of items issued
    private String remarks; // New field for remarks
}
