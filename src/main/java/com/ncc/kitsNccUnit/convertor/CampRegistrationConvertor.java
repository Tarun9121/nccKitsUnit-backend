package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.CampRegistrationDTO;
import com.ncc.kitsNccUnit.entity.CampRegistrations;
import com.ncc.kitsNccUnit.entity.Camps;
import com.ncc.kitsNccUnit.entity.Cadet;
import org.springframework.stereotype.Component;

@Component
public class CampRegistrationConvertor {

    public CampRegistrationDTO toDTO(CampRegistrations registration) {
        return new CampRegistrationDTO(
                registration.getId(),
                registration.getCamp().getId(),
                registration.getCadet().getId(),
                registration.getIsAccepted()
        );
    }

    public CampRegistrations toEntity(CampRegistrationDTO dto, Camps camp, Cadet cadet) {
        return new CampRegistrations(
                dto.getId(),
                camp,
                cadet,
                dto.isAccepted()
        );
    }
}
