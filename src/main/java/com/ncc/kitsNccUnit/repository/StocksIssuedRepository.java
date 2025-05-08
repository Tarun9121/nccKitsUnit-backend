package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.StocksIssued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksIssuedRepository extends JpaRepository<StocksIssued, Integer> {

    List<StocksIssued> findByCadetId(int cadetId);

    List<StocksIssued> findByCadet_RegimentalNo(String regimentalNo);

    List<StocksIssued> findByStockId(int stockId);

    List<StocksIssued> findByCadetIdAndReturnedAtIsNullAndReturnDateBefore(int cadetId, LocalDate currentDate);

    List<StocksIssued> findByCadetIdAndReturnedAtIsNullAndReturnDateAfter(int cadetId, LocalDate currentDate);
}
