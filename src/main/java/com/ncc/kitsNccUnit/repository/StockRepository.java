package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByItemCode(String itemCode);
    List<Stock> findByItemNameContainingIgnoreCaseOrItemCodeContainingIgnoreCase(String name, String code, Pageable pageable);

}
