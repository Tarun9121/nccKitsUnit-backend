package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.convertor.CadetConvertor;
import com.ncc.kitsNccUnit.convertor.StockConvertor;
import com.ncc.kitsNccUnit.dto.StockDTO;
import com.ncc.kitsNccUnit.entity.Stock;
import com.ncc.kitsNccUnit.repository.StockRepository;
import com.ncc.kitsNccUnit.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockConvertor stockConvertor;

    @Autowired
    CadetConvertor cadetConvertor;

    @Override
    public StockDTO addStock(StockDTO stockDTO) {
        Stock stock = stockConvertor.toEntity(stockDTO);
        Stock saved = stockRepository.save(stock);
        return stockConvertor.toDTO(saved);
    }

    @Override
    public ResponseEntity<?> getAllStocks() {
        try {
            List<StockDTO> stocks = stockRepository.findAll().stream()
                    .map(stockConvertor::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving stocks: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getStockById(int id) {
        try {
            Optional<Stock> stockOpt = stockRepository.findById(id);
            if (stockOpt.isPresent()) {
                StockDTO dto = stockConvertor.toDTO(stockOpt.get());
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404).body("Stock not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching stock by ID: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getStockByItemCode(String itemCode) {
        try {
            Optional<Stock> stockOpt = stockRepository.findByItemCode(itemCode);
            if (stockOpt.isPresent()) {
                StockDTO dto = stockConvertor.toDTO(stockOpt.get());
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.status(404).body("Stock not found with item code: " + itemCode);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching stock by item code: " + e.getMessage());
        }
    }

    public List<StockDTO> searchStockItems(String query, int limit) {
        List<Stock> matchedStocks = stockRepository
                .findByItemNameContainingIgnoreCaseOrItemCodeContainingIgnoreCase(query, query, PageRequest.of(0, limit));

        return matchedStocks.stream()
                .map(stockConvertor::toDTO)
                .collect(Collectors.toList());
    }

}
