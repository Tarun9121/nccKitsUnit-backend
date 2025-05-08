package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "temporary_registration")
@Data @NoArgsConstructor @AllArgsConstructor
public class TemporaryRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String emailId;
    private String gender;
    private String phoneNo;
    private String phone;
    private String fatherName;
    private String fatherNo;
    private String fatherIncome;
    private String banckAccount;
    private String regimentalNo;
    private String address;
    private String adhaarNo;
    private String bloodGroup;
    private int btechYear;
    private String branch;
    private double height;
    private double weight;
    private String collegeRollNO;
    private boolean isACertificate;
    private boolean notified;
}
