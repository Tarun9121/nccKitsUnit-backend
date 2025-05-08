package com.ncc.kitsNccUnit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String date;     // e.g. "2025-05-06"
    private String time;     // e.g. "09:00 AM"
    private String location; // e.g. "Ground near Admin Block"
    private String instructions; // Optional extra notes
}

