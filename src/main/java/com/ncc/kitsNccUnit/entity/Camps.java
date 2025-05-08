package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Table(name="camps")
@Data @NoArgsConstructor @AllArgsConstructor
public class Camps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;
    private String description;
    private String campType;
    private String noOfDays;
    private String noOfSeats;
    private String instructions;
}
