package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.TemporaryRegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface TemporaryRegistrationService {
    ResponseEntity<?> registerTemporaryCadet(TemporaryRegistrationDTO dto);
    ResponseEntity<?> getAllTemporaryRegistrations();
    ResponseEntity<?> assignRegimentalNumber(int tempId, String regimentalNo);
    ResponseEntity<?> convertToCadet(int tempId, CadetDTO cadetDTO);
    ResponseEntity<?> deleteTemporaryRegistration(int id);
    ResponseEntity<?> getTemporaryRegistrationById(int id);
}
