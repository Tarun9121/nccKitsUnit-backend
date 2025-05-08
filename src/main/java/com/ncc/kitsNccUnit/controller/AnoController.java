package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.AnoLoginDTO;
import com.ncc.kitsNccUnit.dto.AnoLoginResponseDTO;
import com.ncc.kitsNccUnit.implementation.AnoServiceImpl;
import com.ncc.kitsNccUnit.repository.AnoRepository;
import com.ncc.kitsNccUnit.service.AnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ano")
public class AnoController {

    private AnoService anoService;
    private AnoRepository anoRepository;

    @Autowired
    public AnoController(AnoServiceImpl anoService, AnoRepository anoRepository) {
        this.anoService = anoService;
        this.anoRepository = anoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AnoLoginResponseDTO> login(@RequestBody AnoLoginDTO dto) {
        AnoLoginResponseDTO response = anoService.login(dto);
        if (response.getId() != 0) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnoById(@PathVariable("id") int id) {
        return ResponseEntity.ok(anoRepository.findById(id).orElseThrow());
    }
}
