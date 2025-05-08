package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampsDTO {
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
