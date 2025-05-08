package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Ano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnoRepository extends JpaRepository<Ano, Integer> {
    Optional<Ano> findByEmailIdAndPassword(String emailId, String password);
}
