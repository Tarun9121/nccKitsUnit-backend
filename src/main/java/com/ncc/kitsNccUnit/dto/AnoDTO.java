package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnoDTO {
    private int id;
    private String name;
    private String designation;
    private String institution;
    private String department;
    private String emailId;
    private String mobileNo;
}
