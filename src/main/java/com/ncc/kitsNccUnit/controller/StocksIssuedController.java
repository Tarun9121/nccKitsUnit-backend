package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.IssueStockRequestDTO;
import com.ncc.kitsNccUnit.dto.StockDTO;
import com.ncc.kitsNccUnit.dto.StocksIssuedDTO;
import com.ncc.kitsNccUnit.exceptions.ResourceNotFoundException;
import com.ncc.kitsNccUnit.implementation.StocksIssuedServiceImpl;
import com.ncc.kitsNccUnit.service.StocksIssuedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issued-stocks")
public class StocksIssuedController {

    @Autowired
    private StocksIssuedServiceImpl stocksIssuedService;

    // Get all issued stocks
    @GetMapping("/")
    public ResponseEntity<List<StocksIssuedDTO>> getAllIssuedStocks() {
        List<StocksIssuedDTO> stocksIssued = stocksIssuedService.getAllIssuedStocks();
        return ResponseEntity.ok(stocksIssued);
    }

    // Get issued stocks by cadet ID
    @GetMapping("/{cadetId}")
    public ResponseEntity<List<StocksIssuedDTO>> getIssuedStocksByCadetId(@PathVariable int cadetId) {
        List<StocksIssuedDTO> stocksIssued = stocksIssuedService.getIssuedStocksByCadetId(cadetId);
        return stocksIssued.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(stocksIssued);
    }

    // Get issued stocks by regimental number
    @GetMapping("/regimentalNo/{regimentalNo}")
    public ResponseEntity<List<StocksIssuedDTO>> getIssuedStocksByRegimentalNo(@PathVariable String regimentalNo) {
        List<StocksIssuedDTO> stocksIssued = stocksIssuedService.getIssuedStocksByRegimentalNo(regimentalNo);
        return stocksIssued.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(stocksIssued);
    }

    // Get pending cadets (those who haven't returned their stocks on time)
    @GetMapping("/pending-cadets")
    public ResponseEntity<List<StocksIssuedDTO>> getPendingCadets() {
        List<StocksIssuedDTO> pendingCadets = stocksIssuedService.getPendingCadets();
        return pendingCadets.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(pendingCadets);
    }

    // Get unreturned cadets (those who haven't returned the stocks yet)
    @GetMapping("/unreturned-cadets")
    public ResponseEntity<List<StocksIssuedDTO>> getUnreturnedCadets() {
        List<StocksIssuedDTO> unreturned = stocksIssuedService.getUnreturnedCadets();
        return unreturned.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(unreturned);
    }

    // Get cadets who have taken a specific stock by stock ID
    @GetMapping("/stock/{stockId}/cadets")
    public ResponseEntity<List<StocksIssuedDTO>> getCadetsByStockId(@PathVariable int stockId) {
        List<StocksIssuedDTO> cadets = stocksIssuedService.getCadetsByStockId(stockId);
        return cadets.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(cadets);
    }

    // Get cadets who have not returned the stock (those with returnedAt as null)
    @GetMapping("/stock/{stockId}/unreturned")
    public ResponseEntity<List<StocksIssuedDTO>> getUnreturnedCadetsByStockId(@PathVariable int stockId) {
        List<StocksIssuedDTO> unreturnedCadets = stocksIssuedService.getUnreturnedCadetsByStockId(stockId);
        return unreturnedCadets.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(unreturnedCadets);
    }

    // Get cadets who have crossed the return deadline for the stock
    @GetMapping("/stock/{stockId}/deadline-crossed")
    public ResponseEntity<List<StocksIssuedDTO>> getDeadlineCrossedCadetsByStockId(@PathVariable int stockId) {
        List<StocksIssuedDTO> deadlineCrossedCadets = stocksIssuedService.getDeadlineCrossedCadetsByStockId(stockId);
        return deadlineCrossedCadets.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(deadlineCrossedCadets);
    }

    // Create or update issued stocks
//    @PostMapping("/")
//    public ResponseEntity<StocksIssuedDTO> createOrUpdateStocksIssued(@RequestBody StocksIssuedDTO stocksIssuedDTO) {
//        StocksIssuedDTO createdOrUpdated = stocksIssuedService.createOrUpdateStocksIssued(stocksIssuedDTO);
//        return ResponseEntity.ok(createdOrUpdated);
//    }

//    @PostMapping("/")
//    public ResponseEntity<StocksIssuedDTO> createOrUpdateStocksIssued(
//            @RequestBody IssueStockRequestDTO requestDTO) {
//        System.out.println(requestDTO);
//        // Convert the simplified request to the full DTO
//        StocksIssuedDTO stocksIssuedDTO = convertRequestToStocksIssuedDTO(requestDTO);
//        StocksIssuedDTO createdOrUpdated = stocksIssuedService.createOrUpdateStocksIssued(stocksIssuedDTO);
//        return ResponseEntity.ok(createdOrUpdated);
//    }

    @PostMapping("/")
    public ResponseEntity<?> createOrUpdateStocksIssued(
            @RequestBody IssueStockRequestDTO requestDTO) {
        try {
            StocksIssuedDTO createdOrUpdated = stocksIssuedService.issueStock(requestDTO);
            return ResponseEntity.ok(createdOrUpdated);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Get pending stocks for a specific cadet (returnDate < currentDate AND returnedAt is null)
    @GetMapping("/cadet/{cadetId}/pending")
    public ResponseEntity<List<StocksIssuedDTO>> getPendingStocksByCadetId(@PathVariable int cadetId) {
        List<StocksIssuedDTO> pendingStocks = stocksIssuedService.getPendingStocksByCadetId(cadetId);
        return pendingStocks.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(pendingStocks);
    }

    // Get unreturned stocks for a specific cadet (returnDate > currentDate AND returnedAt is null)
    @GetMapping("/cadet/{cadetId}/unreturned")
    public ResponseEntity<List<StocksIssuedDTO>> getUnreturnedStocksByCadetId(@PathVariable int cadetId) {
        List<StocksIssuedDTO> unreturnedStocks = stocksIssuedService.getUnreturnedStocksByCadetId(cadetId);
        return unreturnedStocks.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(unreturnedStocks);
    }

    private StocksIssuedDTO convertRequestToStocksIssuedDTO(IssueStockRequestDTO request) {
        StocksIssuedDTO dto = new StocksIssuedDTO();

        // Create and set StockDTO
        StockDTO stockDTO = new StockDTO();
        stockDTO.setItemCode(request.getItemCode());
        stockDTO.setItemName(request.getItemName());
        stockDTO.setItemType(request.getItemType());
        dto.setStock(stockDTO);

        // Create and set CadetDTO
        CadetDTO cadetDTO = new CadetDTO();
        cadetDTO.setRegimentalNo(request.getRegimentalNo());
        cadetDTO.setFullName(request.getCadetName());
        dto.setCadet(cadetDTO);

        // Set other fields
        dto.setIssuedAt(request.getIssueDate());
        dto.setReturnDate(request.getReturnDate());
        dto.setQuantity(request.getQuantity());
        dto.setRemarks(request.getRemarks());
        dto.setStatus("ISSUED"); // Default status

        return dto;
    }

    // Issue stock to cadet
    @PostMapping("/issue-stock")
    public ResponseEntity<?> issueStockToCadet(
            @RequestParam String itemCode,
            @RequestParam String regimentalNo,
            @RequestParam String issuedAt,
            @RequestParam String returnDate,
            @RequestParam int quantity,
            @RequestParam String remarks) {

        // Create and populate the StocksIssuedDTO using the provided details
        StocksIssuedDTO stocksIssuedDTO = new StocksIssuedDTO();
        stocksIssuedDTO.setIssuedAt(issuedAt);  // Using String for issuedAt
        stocksIssuedDTO.setReturnDate(returnDate);  // Using String for returnDate
        stocksIssuedDTO.setQuantity(quantity);
        stocksIssuedDTO.setRemarks(remarks);

        // Call service to handle issuing stock
        return stocksIssuedService.issueStockToCadet(stocksIssuedDTO, itemCode, regimentalNo);
    }
}
