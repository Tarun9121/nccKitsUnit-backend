package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampRegistrationDTO {
    private int id;
    private int campId;
    private int cadetId;
    private boolean isAccepted;
}
