package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Camps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampsRepository extends JpaRepository<Camps, Integer> {
    // You can add custom queries if needed (e.g., findByName, findByStartDate, etc.)
    List<Camps> findByStartDateAfter(LocalDate currentDate);
    List<Camps> findByEndDateBefore(LocalDate currentDate);
}
