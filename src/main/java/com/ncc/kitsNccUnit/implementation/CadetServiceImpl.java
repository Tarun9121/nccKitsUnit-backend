package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.convertor.CadetConvertor;
import com.ncc.kitsNccUnit.dto.AddressDTO;
import com.ncc.kitsNccUnit.dto.CadetBankDetailsDTO;
import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.LoginDTO;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.entity.TemporaryRegistration;
import com.ncc.kitsNccUnit.repository.CadetRepository;
import com.ncc.kitsNccUnit.repository.TemporaryRegistrationRepository;
import com.ncc.kitsNccUnit.service.CadetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CadetServiceImpl implements CadetService {

    @Autowired
    private CadetRepository cadetRepository;

    @Autowired
    private CadetConvertor cadetConvertor;
    @Autowired
    private TemporaryRegistrationRepository temporaryRegistrationRepository;
    @Override
    public CadetDTO registerCadet(CadetDTO cadetDTO) {
        // Validate required fields
        if (cadetDTO.getRegimentalNo() == null || cadetDTO.getRegimentalNo().isEmpty()) {
            throw new IllegalArgumentException("Regimental number is required");
        }

        // Check if cadet with this regimental number already exists
        if (cadetRepository.existsByRegimentalNo(cadetDTO.getRegimentalNo())) {
            throw new IllegalArgumentException("Cadet with this regimental number already exists");
        }

        // Find temporary registration by regimental number
        TemporaryRegistration tempRegistration = temporaryRegistrationRepository.findByRegimentalNo(cadetDTO.getRegimentalNo())
                .orElseThrow(() -> new IllegalArgumentException("No temporary registration found with this regimental number"));

        // Validate matching details
        if (!tempRegistration.getEmailId().equalsIgnoreCase(cadetDTO.getMailId())) {
            throw new IllegalArgumentException("Email does not match temporary registration");
        }

        if (!tempRegistration.getPhone().equals(cadetDTO.getMobileNo())) {
            throw new IllegalArgumentException("Mobile number does not match temporary registration");
        }

        if (!tempRegistration.getAdhaarNo().equals(cadetDTO.getAdhaarNo())) {
            throw new IllegalArgumentException("Aadhaar number does not match temporary registration");
        }

        // Convert DTO to Entity
        Cadet cadet = cadetConvertor.toEntity(cadetDTO);

        // Save the Cadet entity to the database
        Cadet savedCadet = cadetRepository.save(cadet);

        // Delete the temporary registration after successful cadet registration
        temporaryRegistrationRepository.deleteById(tempRegistration.getId());

        // Convert saved entity back to DTO and return
        return cadetConvertor.toDTO(savedCadet);
    }

    @Override
    public boolean loginCadet(LoginDTO loginDTO) {
        // Find the cadet by email
        Cadet cadet = cadetRepository.findByMailId(loginDTO.getEmail());

        // If cadet exists and the password matches
        if (cadet != null && cadet.getPassword().equals(loginDTO.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public CadetDTO getCadetById(int id) {
        Optional<Cadet> cadetOpt = cadetRepository.findById(id);
        return cadetOpt.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<CadetDTO> getAllCadets() {
        return cadetRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public CadetDTO getCadetByRegimentalNo(String regimentalNo) {
        Cadet cadet = cadetRepository.findByRegimentalNo(regimentalNo);
        return cadetConvertor.toDTO(cadet);
    }

    private CadetDTO convertToDTO(Cadet cadet) {
        CadetDTO dto = new CadetDTO();
        BeanUtils.copyProperties(cadet, dto);

        // If nested Address and BankDetails exist:
        if (cadet.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(cadet.getAddress(), addressDTO);
            dto.setAddress(addressDTO);
        }

        if (cadet.getBankDetails() != null) {
            CadetBankDetailsDTO bankDTO = new CadetBankDetailsDTO();
            BeanUtils.copyProperties(cadet.getBankDetails(), bankDTO);
            dto.setBankDetails(bankDTO);
        }

        return dto;
    }

    public List<CadetDTO> searchByRegimentalNo(String regimentalNo, int limit) {
        List<Cadet> cadets = cadetRepository.findByRegimentalNoContainingIgnoreCase(
                regimentalNo, PageRequest.of(0, limit));

        return cadets.stream()
                .map(cadetConvertor::convertToDTO)
                .collect(Collectors.toList());
    }

}
