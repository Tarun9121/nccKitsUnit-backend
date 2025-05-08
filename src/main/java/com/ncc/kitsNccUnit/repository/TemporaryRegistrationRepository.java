package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.TemporaryRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryRegistrationRepository extends JpaRepository<TemporaryRegistration, Integer> {
    Optional<TemporaryRegistration> findByEmailId(String emailId);
    Optional<TemporaryRegistration> findByAdhaarNo(String adhaarNo);
    Optional<TemporaryRegistration> findByCollegeRollNO(String collegeRollNO);

    @Query("SELECT COUNT(t) > 0 FROM TemporaryRegistration t WHERE t.emailId = :email")
    boolean existsByEmailId(@Param("email") String email);

    @Query("SELECT COUNT(t) > 0 FROM TemporaryRegistration t WHERE t.regimentalNo = :regimentalNo")
    boolean existsByRegimentalNo(@Param("regimentalNo") String regimentalNo);

    Optional<TemporaryRegistration> findByRegimentalNo(String regimentalNo);
}
