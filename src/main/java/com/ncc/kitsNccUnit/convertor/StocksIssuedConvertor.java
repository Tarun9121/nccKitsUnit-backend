package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.StockDTO;
import com.ncc.kitsNccUnit.dto.StocksIssuedDTO;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.entity.Stock;
import com.ncc.kitsNccUnit.entity.StocksIssued;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class StocksIssuedConvertor {

    public StocksIssuedDTO toDTO(StocksIssued stocksIssued) {
        StocksIssuedDTO dto = new StocksIssuedDTO();

        // Set ID, quantity, and remarks
        dto.setId(stocksIssued.getId());
        dto.setQuantity(stocksIssued.getQuantity());
        dto.setRemarks(stocksIssued.getRemarks());

        // Set StockDTO
        StockDTO stockDTO = new StockDTO();
        BeanUtils.copyProperties(stocksIssued.getStock(), stockDTO);
        dto.setStock(stockDTO);

        // Set CadetDTO
        CadetDTO cadetDTO = new CadetDTO();
        BeanUtils.copyProperties(stocksIssued.getCadet(), cadetDTO);
        dto.setCadet(cadetDTO);

        // Convert dates to string
        if (stocksIssued.getIssuedAt() != null) {
            dto.setIssuedAt(stocksIssued.getIssuedAt().toString());
        }
        if (stocksIssued.getReturnDate() != null) {
            dto.setReturnDate(stocksIssued.getReturnDate().toString());
        }
        if (stocksIssued.getReturnedAt() != null) {
            dto.setReturnedAt(stocksIssued.getReturnedAt().toString());
        }

        return dto;
    }

    public StocksIssued toEntity(StocksIssuedDTO dto) {
        StocksIssued stocksIssued = new StocksIssued();

        // Set Stock from StockDTO
        if (dto.getStock() != null) {
            Stock stock = new Stock();
            BeanUtils.copyProperties(dto.getStock(), stock);
            stocksIssued.setStock(stock);
        }

        // Set Cadet from CadetDTO
        if (dto.getCadet() != null) {
            Cadet cadet = new Cadet();
            BeanUtils.copyProperties(dto.getCadet(), cadet);
            stocksIssued.setCadet(cadet);
        }

        // Convert strings to LocalDate
        if (dto.getIssuedAt() != null) {
            stocksIssued.setIssuedAt(java.time.LocalDate.parse(dto.getIssuedAt()));
        }
        if (dto.getReturnDate() != null) {
            stocksIssued.setReturnDate(java.time.LocalDate.parse(dto.getReturnDate()));
        }
        if (dto.getReturnedAt() != null) {
            stocksIssued.setReturnedAt(java.time.LocalDate.parse(dto.getReturnedAt()));
        }

        // Set other fields
        stocksIssued.setId(dto.getId());
        stocksIssued.setQuantity(dto.getQuantity());
        stocksIssued.setRemarks(dto.getRemarks());

        return stocksIssued;
    }
}
