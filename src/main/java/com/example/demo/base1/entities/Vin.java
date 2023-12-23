package com.example.demo.base1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vin {

    @Id
    private Integer id;

    private String chateau;
    private Integer quantite;
    private BigDecimal prix;
}
