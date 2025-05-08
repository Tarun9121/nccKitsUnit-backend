package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.CadetBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadetBankDetailsRepository extends JpaRepository<CadetBankDetails, Integer> {
}
