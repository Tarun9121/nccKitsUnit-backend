package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.CampsDTO;
import com.ncc.kitsNccUnit.implementation.CampsServiceImpl;
import com.ncc.kitsNccUnit.service.CampsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camps")
public class CampsController {

    private final CampsService campsService;

    @Autowired
    public CampsController(CampsServiceImpl campsService) {
        this.campsService = campsService;
    }

    // Get all camps
    @GetMapping
    public ResponseEntity<List<CampsDTO>> getAllCamps() {
        List<CampsDTO> camps = campsService.getAllCamps();
        return camps.isEmpty() ? ResponseEntity.status(404).body(null) : ResponseEntity.ok(camps);
    }

    // Get camp by ID
    @GetMapping("/{id}")
    public ResponseEntity<CampsDTO> getCampById(@PathVariable int id) {
        CampsDTO campDTO = campsService.getCampById(id);
        if (campDTO == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(campDTO);
    }

    // Create a new camp
    @PostMapping
    public ResponseEntity<CampsDTO> createCamp(@RequestBody CampsDTO campsDTO) {
        return campsService.createCamp(campsDTO);
    }

    // Get all upcoming camps
    @GetMapping("/upcoming")
    public ResponseEntity<List<CampsDTO>> getUpcomingCamps() {
        List<CampsDTO> upcomingCamps = campsService.getUpcomingCamps();
        return upcomingCamps.isEmpty() ? ResponseEntity.status(404).body(null) : ResponseEntity.ok(upcomingCamps);
    }

    // Get all past camps
    @GetMapping("/past")
    public ResponseEntity<List<CampsDTO>> getPastCamps() {
        List<CampsDTO> pastCamps = campsService.getPastCamps();
        return pastCamps.isEmpty() ? ResponseEntity.status(404).body(null) : ResponseEntity.ok(pastCamps);
    }
}
