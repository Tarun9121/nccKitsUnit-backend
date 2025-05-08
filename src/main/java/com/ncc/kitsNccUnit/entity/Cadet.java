package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Table(name="cadet")
@Data @NoArgsConstructor @AllArgsConstructor
public class Cadet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_details_id", referencedColumnName = "id")
    private CadetBankDetails bankDetails;
}
