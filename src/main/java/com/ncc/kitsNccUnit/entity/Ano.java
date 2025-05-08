package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "ano")
@Data @NoArgsConstructor @AllArgsConstructor
public class Ano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String designation;
    private String institution;
    private String department;
    private String emailId;
    private String mobileNo;
    private String password;
}
