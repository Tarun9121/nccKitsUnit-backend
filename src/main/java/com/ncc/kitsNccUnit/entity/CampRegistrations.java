package com.ncc.kitsNccUnit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="camp_registration")
@Data @NoArgsConstructor @AllArgsConstructor
public class CampRegistrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "camp_id")
    private Camps camp;
    @ManyToOne
    @JoinColumn(name = "cadet_id")
    private Cadet cadet;
    @Column(name = "is_accepted", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isAccepted = Boolean.FALSE;
}
