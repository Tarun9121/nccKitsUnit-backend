package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.CampsDTO;
import com.ncc.kitsNccUnit.entity.Camps;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CampsConvertor {

    public CampsDTO toDTO(Camps camps) {
        CampsDTO dto = new CampsDTO();
        BeanUtils.copyProperties(camps, dto);
        return dto;
    }

    public Camps toEntity(CampsDTO campsDTO) {
        Camps camps = new Camps();
        BeanUtils.copyProperties(campsDTO, camps);
        return camps;
    }
}
