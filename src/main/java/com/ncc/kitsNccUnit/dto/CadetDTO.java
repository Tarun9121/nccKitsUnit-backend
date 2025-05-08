package com.ncc.kitsNccUnit.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CadetDTO {
    private int id;
    private String fullName;
    private String mailId;
    private String mobileNo;
    private String alternateMobileNo;
    private String regimentalNo;
    private String gender;
    private LocalDate dateOfBirth;
    private String adhaarNo;
    private String bloodGroup;
    private String branch;
    private int btechYear;
    private String nationality;
    private String religion;
    private String password;
    private AddressDTO address;
    private CadetBankDetailsDTO bankDetails;
}
