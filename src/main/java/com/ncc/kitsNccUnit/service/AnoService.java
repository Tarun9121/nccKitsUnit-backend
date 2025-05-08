package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.AnoLoginDTO;
import com.ncc.kitsNccUnit.dto.AnoLoginResponseDTO;

public interface AnoService {
    AnoLoginResponseDTO login(AnoLoginDTO dto);
}
