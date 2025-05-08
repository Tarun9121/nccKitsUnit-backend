package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="cadet_bank_details")
@Data @NoArgsConstructor @AllArgsConstructor
public class CadetBankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bankName;
    private String accountNo;
    private String ifscCode;
}
