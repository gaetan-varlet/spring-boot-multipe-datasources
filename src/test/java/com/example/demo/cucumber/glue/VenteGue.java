package com.example.demo.cucumber.glue;

import com.example.demo.base2.entities.Vente;
import com.example.demo.base2.repositories.VenteRepository;
import io.cucumber.java8.En;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VenteGue implements En {

    private BigDecimal result;

    public VenteGue(VenteRepository venteRepository){

        Then("j'ai {int} ventes pour un motant total de {double}", (Integer quantiteExpected, Double montantExcepted) -> {
            List<Vente> ventes = venteRepository.findAll();
            assertThat(ventes).hasSize(1);
            Vente vente = ventes.get(0);
            assertThat(vente.getQuantite()).isEqualTo(quantiteExpected);
            assertThat(vente.getMontantTotal().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(montantExcepted).stripTrailingZeros());
        });

    }
}
