package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.StockDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StockService {
    StockDTO addStock(StockDTO stockDTO);
    ResponseEntity<?> getAllStocks();
    ResponseEntity<?> getStockById(int id);
    ResponseEntity<?> getStockByItemCode(String itemCode);
    public List<StockDTO> searchStockItems(String query, int limit);
}
