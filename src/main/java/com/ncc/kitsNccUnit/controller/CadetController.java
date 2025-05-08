package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.LoginDTO;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.implementation.CadetServiceImpl;
import com.ncc.kitsNccUnit.repository.CadetRepository;
import com.ncc.kitsNccUnit.service.CadetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cadets")
public class CadetController {
    private CadetService cadetService;
    private CadetRepository cadetRepository;

    @Autowired
    public CadetController(CadetServiceImpl cadetService, CadetRepository cadetRepository) {
        this.cadetService = cadetService;
        this.cadetRepository = cadetRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<List<CadetDTO>> searchCadetsByRegimentalNo(
            @RequestParam String regimentalNo,
            @RequestParam(required = false, defaultValue = "10") int limit) {

        List<CadetDTO> results = cadetService.searchByRegimentalNo(regimentalNo, limit);
        return ResponseEntity.ok(results);
    }

    @GetMapping
    public ResponseEntity<?> getAllCadets() {
        return ResponseEntity.ok(cadetService.getAllCadets());
    }

    @PostMapping("/register")
    public CadetDTO registerCadet(@RequestBody CadetDTO cadetDTO) {
        return cadetService.registerCadet(cadetDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = cadetService.loginCadet(loginDTO);

        if (isAuthenticated) {
            Cadet cadet = cadetRepository.findByMailId(loginDTO.getEmail());

            if (cadet != null) {
                // Return a JSON object with cadetId only
                return ResponseEntity.ok().body(Map.of("cadetId", cadet.getId()));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Cadet not found"));
            }
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCadetById(@PathVariable int id) {
        CadetDTO cadetDTO = cadetService.getCadetById(id);
        if (cadetDTO == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Cadet not found"));
        }
        return ResponseEntity.ok(cadetDTO);
    }

    @GetMapping("/regimental-no/{regimentalNo}")
    public ResponseEntity<?> getCadetByRegimentalNo(@PathVariable String regimentalNo) {
        CadetDTO cadetDTO = cadetService.getCadetByRegimentalNo(regimentalNo);
        if(!ObjectUtils.isEmpty(cadetDTO)) {
            return ResponseEntity.ok(cadetDTO);
        }
        return ResponseEntity.badRequest().build();
    }

}
