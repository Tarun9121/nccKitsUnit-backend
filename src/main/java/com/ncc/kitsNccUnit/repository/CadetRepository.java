package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Cadet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Repository
public interface CadetRepository extends JpaRepository<Cadet, Integer> {
    Cadet findByMailId(String email);
    Cadet findByRegimentalNo(String regimentalNo);
    Optional<Cadet> findByRegimentalNoAndGenderAndMobileNoAndBtechYearAndBranch(
            String regimentalNo, String gender, String mobileNo, int btechYear, String branch);

    boolean existsByMailId(String mailId);

    // Check if regimental number exists
    boolean existsByRegimentalNo(String regimentalNo);

    List<Cadet> findByRegimentalNoContainingIgnoreCase(String regimentalNo, Pageable pageable);
}
