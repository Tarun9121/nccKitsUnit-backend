package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.StockDTO;
import com.ncc.kitsNccUnit.entity.Stock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StockConvertor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public Stock toEntity(StockDTO dto) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(dto, stock);

        // Convert receivedDate from String to LocalDate
        if (dto.getReceivedDate() != null && !dto.getReceivedDate().isEmpty()) {
            LocalDate receivedDate = LocalDate.parse(dto.getReceivedDate(), DATE_FORMATTER);
            stock.setReceivedDate(receivedDate);
        }

        return stock;
    }

    public StockDTO toDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        BeanUtils.copyProperties(stock, dto);

        // Convert receivedDate from LocalDate to String
        if (stock.getReceivedDate() != null) {
            dto.setReceivedDate(stock.getReceivedDate().toString());
        }

        return dto;
    }

    // Static methods for conversion if needed
    public static Stock dtoToEntity(StockDTO dto) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(dto, stock);

        // Convert receivedDate from String to LocalDate
        if (dto.getReceivedDate() != null && !dto.getReceivedDate().isEmpty()) {
            LocalDate receivedDate = LocalDate.parse(dto.getReceivedDate(), DATE_FORMATTER);
            stock.setReceivedDate(receivedDate);
        }

        return stock;
    }

    public static StockDTO entityToDto(Stock entity) {
        StockDTO dto = new StockDTO();
        BeanUtils.copyProperties(entity, dto);

        // Convert receivedDate from LocalDate to String
        if (entity.getReceivedDate() != null) {
            dto.setReceivedDate(entity.getReceivedDate().toString());
        }

        return dto;
    }
}