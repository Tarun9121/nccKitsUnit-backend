package com.ncc.kitsNccUnit.entity;

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

import java.time.LocalDate;

@Entity @Table(name="stocks_issued")
@Data @NoArgsConstructor @AllArgsConstructor
public class StocksIssued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "cadet_id", referencedColumnName = "id")
    private Cadet cadet;

    private LocalDate issuedAt;
    private LocalDate returnDate;
    private LocalDate returnedAt;
    private String status;
    private int quantity; // New field for the quantity of items issued
    private String remarks; // New field for remarks
}
