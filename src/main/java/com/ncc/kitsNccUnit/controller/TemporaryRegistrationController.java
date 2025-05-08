package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.TemporaryRegistrationDTO;
import com.ncc.kitsNccUnit.service.TemporaryRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/temporary-registrations")
public class TemporaryRegistrationController {

    @Autowired
    private TemporaryRegistrationService service;

    @PostMapping
    public ResponseEntity<?> registerTemporaryCadet(@RequestBody TemporaryRegistrationDTO dto) {
        return service.registerTemporaryCadet(dto);
    }

    @GetMapping
    public ResponseEntity<?> getAllTemporaryRegistrations() {
        return service.getAllTemporaryRegistrations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTemporaryRegistrationById(@PathVariable int id) {
        return service.getTemporaryRegistrationById(id);
    }

    @PutMapping("/{id}/assign-regimental")
    public ResponseEntity<?> assignRegimentalNumber(
            @PathVariable int id,
            @RequestParam String regimentalNo) {
        return service.assignRegimentalNumber(id, regimentalNo);
    }

    @PostMapping("/{id}/convert-to-cadet")
    public ResponseEntity<?> convertToCadet(
            @PathVariable int id,
            @RequestBody CadetDTO cadetDTO) {
        return service.convertToCadet(id, cadetDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemporaryRegistration(@PathVariable int id) {
        return service.deleteTemporaryRegistration(id);
    }
}