package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryRegistrationDTO {
    private int id;
    private String name;
    private String emailId;
    private String gender;
    private String phone;
    private LocalDate phoneNo;
    private String fatherName;
    private String fatherNo;
    private String fatherIncome;
    private String regimentalNo;
    private String banckAccount;
    private String address;
    private String adhaarNo;
    private String bloodGroup;
    private int btechYear;
    private String branch;
    private double height;
    private double weight;
    private String collegeRollNO;
    private boolean isACertificate;
}
