package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicCampRegistrationDTO {
    private int campId;
    private String regimentalNo;
    private String gender;
    private String mobileNo;
    private int btechYear;
    private String branch;
}
