package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.CampRegistrationDTO;
import com.ncc.kitsNccUnit.dto.PublicCampRegistrationDTO;
import com.ncc.kitsNccUnit.exceptions.DuplicateRegistrationException;
import com.ncc.kitsNccUnit.service.CampRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camp-registrations")
public class CampRegistrationController {

    @Autowired
    private CampRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CampRegistrationDTO dto) {
        try {
            CampRegistrationDTO registration = registrationService.registerForCamp(dto);
            return ResponseEntity.ok(registration);
        } catch (DuplicateRegistrationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CampRegistrationDTO>> getAll() {
        List<CampRegistrationDTO> all = registrationService.getAllRegistrations();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampRegistrationDTO> getById(@PathVariable int id) {
        CampRegistrationDTO dto = registrationService.getRegistrationById(id);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    @PostMapping("/public")
    public ResponseEntity<String> registerWithoutLogin(@RequestBody PublicCampRegistrationDTO dto) {
        boolean registered = registrationService.registerWithoutLogin(dto);
        return registered
                ? ResponseEntity.ok("Registered successfully!")
                : ResponseEntity.badRequest().body("Cadet or Camp not found.");
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptRegistration(
            @PathVariable int id,
            @RequestParam boolean isAccepted) {
        boolean success = registrationService.acceptCadetToCamp(id, isAccepted);
        return success
                ? ResponseEntity.ok("Cadet registration updated successfully.")
                : ResponseEntity.badRequest().build();
    }

    @GetMapping("/camp/{campId}/cadets")
    public ResponseEntity<List<CampRegistrationDTO>> getCadetsByCamp(@PathVariable int campId) {
        List<CampRegistrationDTO> cadets = registrationService.getCadetsByCampId(campId);
        return cadets.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cadets);
    }



    @GetMapping("/cadet/{cadetId}")
    public ResponseEntity<List<CampRegistrationDTO>> getCampsByCadetId(@PathVariable int cadetId) {
        List<CampRegistrationDTO> registrations = registrationService.getRegistrationsByCadetId(cadetId);
        return registrations.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(registrations);
    }

    @GetMapping("/camp/{campId}/accepted-cadets")
    public ResponseEntity<List<CampRegistrationDTO>> getAcceptedCadetsByCamp(@PathVariable int campId) {
        List<CampRegistrationDTO> acceptedCadets = registrationService.getAcceptedCadetsByCampId(campId);
        return acceptedCadets.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(acceptedCadets);
    }

    @GetMapping("/camp/{campId}/pending-cadets")
    public ResponseEntity<List<CampRegistrationDTO>> getPendingCadetsByCamp(@PathVariable int campId) {
        List<CampRegistrationDTO> pendingCadets = registrationService.getPendingCadetsByCampId(campId);
        return pendingCadets.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(pendingCadets);
    }
}
