package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.CampRegistrationDTO;
import com.ncc.kitsNccUnit.dto.PublicCampRegistrationDTO;

import java.util.List;

public interface CampRegistrationService {
    CampRegistrationDTO registerForCamp(CampRegistrationDTO dto);
    List<CampRegistrationDTO> getAllRegistrations();
    CampRegistrationDTO getRegistrationById(int id);

    boolean registerWithoutLogin(PublicCampRegistrationDTO dto);

    boolean acceptCadetToCamp(int registrationId, boolean isAccepted);

    List<CampRegistrationDTO> getCadetsByCampId(int campId);

    List<CampRegistrationDTO> getRegistrationsByCadetId(int cadetId);

    List<CampRegistrationDTO> getAcceptedCadetsByCampId(int campId);
    List<CampRegistrationDTO> getPendingCadetsByCampId(int campId);

}
