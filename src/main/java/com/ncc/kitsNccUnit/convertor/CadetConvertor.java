package com.ncc.kitsNccUnit.convertor;

import com.ncc.kitsNccUnit.dto.*;
import com.ncc.kitsNccUnit.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CadetConvertor {

    public Cadet toEntity(CadetDTO dto) {
        Cadet entity = new Cadet();
        BeanUtils.copyProperties(dto, entity);

        Address address = new Address();
        BeanUtils.copyProperties(dto.getAddress(), address);
        entity.setAddress(address);

        CadetBankDetails bankDetails = new CadetBankDetails();
        BeanUtils.copyProperties(dto.getBankDetails(), bankDetails);
        entity.setBankDetails(bankDetails);

        return entity;
    }

    public CadetDTO toDTO(Cadet entity) {
        CadetDTO dto = new CadetDTO();
        BeanUtils.copyProperties(entity, dto);

        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(entity.getAddress(), addressDTO);
        dto.setAddress(addressDTO);

        CadetBankDetailsDTO bankDTO = new CadetBankDetailsDTO();
        BeanUtils.copyProperties(entity.getBankDetails(), bankDTO);
        dto.setBankDetails(bankDTO);

        return dto;
    }

    public CadetDTO convertToDTO(Cadet cadet) {
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

}
