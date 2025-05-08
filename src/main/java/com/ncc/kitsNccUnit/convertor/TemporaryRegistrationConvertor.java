package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.TemporaryRegistrationDTO;

import com.ncc.kitsNccUnit.entity.TemporaryRegistration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TemporaryRegistrationConvertor {

    public TemporaryRegistration toEntity(TemporaryRegistrationDTO dto) {
        TemporaryRegistration entity = new TemporaryRegistration();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public TemporaryRegistrationDTO toDTO(TemporaryRegistration entity) {
        TemporaryRegistrationDTO dto = new TemporaryRegistrationDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
