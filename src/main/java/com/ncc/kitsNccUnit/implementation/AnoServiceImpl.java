package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.dto.AnoLoginDTO;
import com.ncc.kitsNccUnit.dto.AnoLoginResponseDTO;
import com.ncc.kitsNccUnit.entity.Ano;
import com.ncc.kitsNccUnit.repository.AnoRepository;
import com.ncc.kitsNccUnit.service.AnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnoServiceImpl implements AnoService {

    @Autowired
    private AnoRepository anoRepository;

    @Override
    public AnoLoginResponseDTO login(AnoLoginDTO dto) {
        Optional<Ano> optionalAno = anoRepository.findByEmailIdAndPassword(dto.getEmailId(), dto.getPassword());
        if (optionalAno.isPresent()) {
            Ano ano = optionalAno.get();
            return new AnoLoginResponseDTO(ano.getId(), "Login successful");
        }
        return new AnoLoginResponseDTO(0, "Invalid credentials");
    }
}
