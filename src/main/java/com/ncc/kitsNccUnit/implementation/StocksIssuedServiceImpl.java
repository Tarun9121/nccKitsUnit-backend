package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.convertor.CadetConvertor;
import com.ncc.kitsNccUnit.convertor.StockConvertor;
import com.ncc.kitsNccUnit.convertor.StocksIssuedConvertor;
import com.ncc.kitsNccUnit.dto.IssueStockRequestDTO;
import com.ncc.kitsNccUnit.dto.StocksIssuedDTO;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.entity.Stock;
import com.ncc.kitsNccUnit.entity.StocksIssued;
import com.ncc.kitsNccUnit.exceptions.ResourceNotFoundException;
import com.ncc.kitsNccUnit.repository.CadetRepository;
import com.ncc.kitsNccUnit.repository.StockRepository;
import com.ncc.kitsNccUnit.repository.StocksIssuedRepository;
import com.ncc.kitsNccUnit.service.StocksIssuedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StocksIssuedServiceImpl implements StocksIssuedService {

    @Autowired
    private StocksIssuedRepository stocksIssuedRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CadetRepository cadetRepository;

    @Autowired
    private StocksIssuedConvertor convertor;

    @Autowired private StockConvertor stockConvertor;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<StocksIssuedDTO> getAllIssuedStocks() {
        return stocksIssuedRepository.findAll()
                .stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getIssuedStocksByCadetId(int cadetId) {
        return stocksIssuedRepository.findByCadetId(cadetId)
                .stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getIssuedStocksByRegimentalNo(String regimentalNo) {
        return stocksIssuedRepository.findByCadet_RegimentalNo(regimentalNo)
                .stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getPendingCadets() {
        LocalDate now = LocalDate.now();
        return stocksIssuedRepository.findAll()
                .stream()
                .filter(si -> si.getReturnedAt() == null && si.getReturnDate().isBefore(now))
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getUnreturnedCadets() {
        return stocksIssuedRepository.findAll()
                .stream()
                .filter(si -> si.getReturnedAt() == null)
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getCadetsByStockId(int stockId) {
        return stocksIssuedRepository.findByStockId(stockId)
                .stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getUnreturnedCadetsByStockId(int stockId) {
        return stocksIssuedRepository.findByStockId(stockId)
                .stream()
                .filter(si -> si.getReturnedAt() == null)
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getDeadlineCrossedCadetsByStockId(int stockId) {
        LocalDate now = LocalDate.now();
        return stocksIssuedRepository.findByStockId(stockId)
                .stream()
                .filter(si -> si.getReturnedAt() == null && si.getReturnDate().isBefore(now))
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StocksIssuedDTO createOrUpdateStocksIssued(StocksIssuedDTO dto) {
        StocksIssuedDTO stocksIssuedDTO = new StocksIssuedDTO();
        Optional<Stock> stockOptional = stockRepository.findByItemCode(dto.getStock().getItemCode());
        Cadet cadet = cadetRepository.findByRegimentalNo(dto.getCadet().getRegimentalNo());
        if(stockOptional.isPresent() && !ObjectUtils.isEmpty(cadet)) {
            stocksIssuedDTO.setStock(stockConvertor.toDTO(stockOptional.get()));

            StocksIssued entity = convertor.toEntity(dto);
            StocksIssued saved = stocksIssuedRepository.save(entity);
            return convertor.toDTO(saved);
        }
        return null;
    }

    @Transactional
    @Override
    public StocksIssuedDTO issueStock(IssueStockRequestDTO requestDTO) {
        // Validate request
        if (requestDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Find stock and cadet
        Stock stock = stockRepository.findByItemCode(requestDTO.getItemCode())
                .orElseThrow(() -> new ResourceNotFoundException("Stock item not found"));

        Cadet cadet = cadetRepository.findByRegimentalNo(requestDTO.getRegimentalNo());
        if (cadet == null) {
            throw new ResourceNotFoundException("Cadet not found");
        }

        // Check available quantity
        if (stock.getQuantity() < requestDTO.getQuantity()) {
            throw new RuntimeException(
                    "Not enough stock available. Available: " + stock.getAvailableQuantity() +
                            ", Requested: " + requestDTO.getQuantity()
            );
        }

        // Update stock quantity
        stock.setQuantity(stock.getQuantity() - requestDTO.getQuantity());
        stockRepository.save(stock);

        // Create stock issuance record
        StocksIssuedDTO issuanceDTO = new StocksIssuedDTO();
        issuanceDTO.setStock(stockConvertor.toDTO(stock));
        CadetConvertor cadetConvertor = new CadetConvertor();
        issuanceDTO.setCadet(cadetConvertor.toDTO(cadet));
        issuanceDTO.setIssuedAt(requestDTO.getIssueDate());
        issuanceDTO.setReturnDate(requestDTO.getReturnDate());
        issuanceDTO.setQuantity(requestDTO.getQuantity());
        issuanceDTO.setRemarks(requestDTO.getRemarks());

        StocksIssued entity = convertor.toEntity(issuanceDTO);
        StocksIssued saved = stocksIssuedRepository.save(entity);

        return convertor.toDTO(saved);
    }

    @Override
    public ResponseEntity<?> issueStockToCadet(StocksIssuedDTO dto, String itemCode, String regimentalNo) {
        Stock stock = stockRepository.findByItemCode(itemCode).orElse(null);
        Cadet cadet = cadetRepository.findByRegimentalNo(regimentalNo);

        if (stock == null || cadet == null) {
            return ResponseEntity.badRequest().body("Invalid stock code or regimental number");
        }

        StocksIssued issued = new StocksIssued();
        issued.setCadet(cadet);
        issued.setStock(stock);
        issued.setIssuedAt(LocalDate.parse(dto.getIssuedAt(), formatter));
        issued.setReturnDate(LocalDate.parse(dto.getReturnDate(), formatter));
        issued.setQuantity(dto.getQuantity());
        issued.setRemarks(dto.getRemarks());

        StocksIssued saved = stocksIssuedRepository.save(issued);
        return ResponseEntity.ok(convertor.toDTO(saved));
    }

    @Override
    public List<StocksIssuedDTO> getPendingStocksByCadetId(int cadetId) {
        LocalDate currentDate = LocalDate.now();
        List<StocksIssued> stocksIssued = stocksIssuedRepository.findByCadetIdAndReturnedAtIsNullAndReturnDateBefore(cadetId, currentDate);
        return stocksIssued.stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StocksIssuedDTO> getUnreturnedStocksByCadetId(int cadetId) {
        LocalDate currentDate = LocalDate.now();
        List<StocksIssued> stocksIssued = stocksIssuedRepository.findByCadetIdAndReturnedAtIsNullAndReturnDateAfter(cadetId, currentDate);
        return stocksIssued.stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }
}
