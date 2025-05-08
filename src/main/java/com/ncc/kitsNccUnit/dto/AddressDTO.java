package com.ncc.kitsNccUnit.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private int id;
    private String houseNumber;
    private String street;
    private String city;
    private String district;
    private String state;
    private String pinCode;
}
