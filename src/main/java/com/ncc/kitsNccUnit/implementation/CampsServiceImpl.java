package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.convertor.CampsConvertor;
import com.ncc.kitsNccUnit.dto.CampsDTO;
import com.ncc.kitsNccUnit.entity.Camps;
import com.ncc.kitsNccUnit.repository.CampsRepository;
import com.ncc.kitsNccUnit.service.CampsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampsServiceImpl implements CampsService {

    @Autowired
    private CampsRepository campsRepository;

    @Autowired
    private CampsConvertor campsConvertor;

    @Override
    public List<CampsDTO> getAllCamps() {
        return campsRepository.findAll().stream()
                .map(campsConvertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CampsDTO getCampById(int id) {
        Optional<Camps> campOptional = campsRepository.findById(id);
        if (campOptional.isEmpty()) {
            return null; // or you can throw an exception for not found
        }
        return campsConvertor.toDTO(campOptional.get());
    }

    @Override
    public ResponseEntity<CampsDTO> createCamp(CampsDTO campsDTO) {
        // Convert DTO to entity
        Camps camps = campsConvertor.toEntity(campsDTO);

        // Save the entity in the database
        campsRepository.save(camps);

        return ResponseEntity.status(HttpStatus.CREATED).body(campsConvertor.toDTO(camps));
    }

    @Override
    public List<CampsDTO> getUpcomingCamps() {
        LocalDate currentDate = LocalDate.now();  // Get today's date
        List<Camps> upcomingCamps = campsRepository.findByStartDateAfter(currentDate);
        return upcomingCamps.stream().map(campsConvertor::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CampsDTO> getPastCamps() {
        LocalDate currentDate = LocalDate.now();  // Get today's date
        List<Camps> pastCamps = campsRepository.findByEndDateBefore(currentDate);
        return pastCamps.stream().map(campsConvertor::toDTO).collect(Collectors.toList());
    }
}
