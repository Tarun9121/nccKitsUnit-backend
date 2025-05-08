package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.StockDTO;
import com.ncc.kitsNccUnit.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    // Add new stock
    @PostMapping("/add")
    public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO stockDTO) {
        StockDTO savedStock = stockService.addStock(stockDTO);
        return ResponseEntity.ok(savedStock);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StockDTO>> searchStockItems(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        List<StockDTO> results = stockService.searchStockItems(query, limit);
        return ResponseEntity.ok(results);
    }

    // Get all stocks
    @GetMapping("/all")
    public ResponseEntity<?> getAllStocks() {
        return stockService.getAllStocks();
    }

    // Get stock by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStockById(@PathVariable int id) {
        return stockService.getStockById(id);
    }

    // Get stock by Item Code
    @GetMapping("/item/{itemCode}")
    public ResponseEntity<?> getStockByItemCode(@PathVariable String itemCode) {
        return stockService.getStockByItemCode(itemCode);
    }
}
