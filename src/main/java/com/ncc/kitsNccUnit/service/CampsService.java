package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.CampsDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CampsService {
    List<CampsDTO> getAllCamps();
    CampsDTO getCampById(int id);
    ResponseEntity<CampsDTO> createCamp(CampsDTO campsDTO);
    List<CampsDTO> getUpcomingCamps();
    List<CampsDTO> getPastCamps();
}
