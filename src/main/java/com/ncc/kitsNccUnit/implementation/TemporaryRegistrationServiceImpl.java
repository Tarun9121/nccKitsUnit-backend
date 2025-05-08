package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.TemporaryRegistrationDTO;
import com.ncc.kitsNccUnit.entity.Address;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.entity.CadetBankDetails;
import com.ncc.kitsNccUnit.entity.TemporaryRegistration;
import com.ncc.kitsNccUnit.repository.AddressRepository;
import com.ncc.kitsNccUnit.repository.CadetBankDetailsRepository;
import com.ncc.kitsNccUnit.repository.CadetRepository;
import com.ncc.kitsNccUnit.repository.TemporaryRegistrationRepository;
import com.ncc.kitsNccUnit.service.MailService;
import com.ncc.kitsNccUnit.service.TemporaryRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import com.ncc.kitsNccUnit.dto.*;
import com.ncc.kitsNccUnit.entity.*;
import com.ncc.kitsNccUnit.repository.*;
import com.ncc.kitsNccUnit.service.MailService;
import com.ncc.kitsNccUnit.service.TemporaryRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TemporaryRegistrationServiceImpl implements TemporaryRegistrationService {

    private final TemporaryRegistrationRepository tempRepo;
    private final CadetRepository cadetRepo;
    private final AddressRepository addressRepo;
    private final CadetBankDetailsRepository bankRepo;
    private final MailService mailService;

    @Autowired
    public TemporaryRegistrationServiceImpl(
            TemporaryRegistrationRepository tempRepo,
            CadetRepository cadetRepo,
            AddressRepository addressRepo,
            CadetBankDetailsRepository bankRepo,
            MailService mailService) {
        this.tempRepo = tempRepo;
        this.cadetRepo = cadetRepo;
        this.addressRepo = addressRepo;
        this.bankRepo = bankRepo;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<?> registerTemporaryCadet(TemporaryRegistrationDTO dto) {
        try {
            // Check for existing email
            if (tempRepo.existsByEmailId(dto.getEmailId()) || cadetRepo.existsByMailId(dto.getEmailId())) {
                return ResponseEntity.badRequest().body("Email already registered");
            }

            TemporaryRegistration registration = new TemporaryRegistration();
            BeanUtils.copyProperties(dto, registration);

            TemporaryRegistration savedRegistration = tempRepo.save(registration);
            return ResponseEntity.ok(savedRegistration);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllTemporaryRegistrations() {
        try {
            List<TemporaryRegistration> registrations = tempRepo.findAll();
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error fetching registrations: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getTemporaryRegistrationById(int id) {
        try {
            TemporaryRegistration registration = tempRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Registration not found"));
            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error fetching registration: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> assignRegimentalNumber(int tempId, String regimentalNo) {
        try {
            TemporaryRegistration registration = tempRepo.findById(tempId)
                    .orElseThrow(() -> new RuntimeException("Registration not found"));

            if (registration.getRegimentalNo() != null) {
                return ResponseEntity.badRequest().body("Regimental number already assigned");
            }

            if (tempRepo.existsByRegimentalNo(regimentalNo) || cadetRepo.existsByRegimentalNo(regimentalNo)) {
                return ResponseEntity.badRequest().body("Regimental number already in use");
            }

            registration.setRegimentalNo(regimentalNo);
            tempRepo.save(registration);

            // Send notification email
            String emailContent = String.format(
                    "Dear %s,\n\nYour NCC regimental number has been assigned: %s\n" +
                            "Please complete your registration at [registration link].\n\n" +
                            "Regards,\nNCC Unit",
                    registration.getName(),
                    regimentalNo
            );

            mailService.sendMail(
                    registration.getEmailId(),
                    "Your NCC Regimental Number",
                    emailContent
            );

            return ResponseEntity.ok("Regimental number assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error assigning regimental number: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> convertToCadet(int tempId, CadetDTO cadetDTO) {
        try {
            // Get temporary registration
            TemporaryRegistration tempReg = tempRepo.findById(tempId)
                    .orElseThrow(() -> new RuntimeException("Temporary registration not found"));

            // Validate regimental number
            if (tempReg.getRegimentalNo() == null) {
                return ResponseEntity.badRequest().body("Regimental number not assigned");
            }

            // Create and save cadet
            Cadet cadet = new Cadet();
            BeanUtils.copyProperties(cadetDTO, cadet);
            cadet.setRegimentalNo(tempReg.getRegimentalNo());
            Cadet savedCadet = cadetRepo.save(cadet);

            // Save address
            Address address = new Address();
            BeanUtils.copyProperties(cadetDTO.getAddress(), address);
            address = addressRepo.save(address);

            // Save bank details
            CadetBankDetails bankDetails = new CadetBankDetails();
            BeanUtils.copyProperties(cadetDTO.getBankDetails(), bankDetails);
            bankDetails = bankRepo.save(bankDetails);

            // Update cadet with relationships
            savedCadet.setAddress(address);
            savedCadet.setBankDetails(bankDetails);
            cadetRepo.save(savedCadet);

            // Delete temporary registration
            tempRepo.delete(tempReg);

            return ResponseEntity.ok("Cadet registration completed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error converting to cadet: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteTemporaryRegistration(int id) {
        try {
            if (!tempRepo.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            tempRepo.deleteById(id);
            return ResponseEntity.ok("Temporary registration deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting registration: " + e.getMessage());
        }
    }
}