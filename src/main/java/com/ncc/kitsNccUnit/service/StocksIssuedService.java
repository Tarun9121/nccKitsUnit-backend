package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.IssueStockRequestDTO;
import com.ncc.kitsNccUnit.dto.StocksIssuedDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StocksIssuedService {

    List<StocksIssuedDTO> getAllIssuedStocks();
    List<StocksIssuedDTO> getIssuedStocksByCadetId(int cadetId);
    List<StocksIssuedDTO> getIssuedStocksByRegimentalNo(String regimentalNo);

    List<StocksIssuedDTO> getPendingCadets();
    List<StocksIssuedDTO> getUnreturnedCadets();

    List<StocksIssuedDTO> getCadetsByStockId(int stockId);
    List<StocksIssuedDTO> getUnreturnedCadetsByStockId(int stockId);
    List<StocksIssuedDTO> getDeadlineCrossedCadetsByStockId(int stockId);

    StocksIssuedDTO createOrUpdateStocksIssued(StocksIssuedDTO stocksIssuedDTO);
    ResponseEntity<?> issueStockToCadet(StocksIssuedDTO stocksIssuedDTO, String itemCode, String regimentalNo);

    List<StocksIssuedDTO> getPendingStocksByCadetId(int cadetId);
    List<StocksIssuedDTO> getUnreturnedStocksByCadetId(int cadetId);

    public StocksIssuedDTO issueStock(IssueStockRequestDTO requestDTO);
}
