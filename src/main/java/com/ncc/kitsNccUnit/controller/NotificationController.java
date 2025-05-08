package com.ncc.kitsNccUnit.controller;

import com.ncc.kitsNccUnit.dto.NotificationDTO;
import com.ncc.kitsNccUnit.entity.TemporaryRegistration;
import com.ncc.kitsNccUnit.repository.TemporaryRegistrationRepository;
import com.ncc.kitsNccUnit.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private TemporaryRegistrationRepository tempRepo;

    @Autowired
    private MailService mailService;

    @PostMapping("/notify-temporary-registrations")
    public ResponseEntity<?> notifyAllTemporaryRegistrants(@RequestBody NotificationDTO dto) {
        List<TemporaryRegistration> registrants = tempRepo.findAll();

        if (registrants.isEmpty()) {
            return ResponseEntity.badRequest().body("No temporary registrations found.");
        }

        String subject = "NCC Ground Gathering Instructions";
        String content = String.format("""
                Dear Cadet,
                
                You are requested to gather at the following location for verification:
                
                📍 Location: %s
                📅 Date: %s
                ⏰ Time: %s
                
                %s
                
                Regards,
                NCC Unit
                """, dto.getLocation(), dto.getDate(), dto.getTime(), dto.getInstructions() != null ? dto.getInstructions() : "");

        for (TemporaryRegistration reg : registrants) {
            mailService.sendMail(reg.getEmailId(), subject, content);
            // Optionally mark them as notified
            // reg.setNotified(true);
        }

        // tempRepo.saveAll(registrants); // if you're updating "notified"

        return ResponseEntity.ok("Notification emails sent to all temporary registrants.");
    }
}
