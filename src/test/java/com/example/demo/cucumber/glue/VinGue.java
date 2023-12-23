package com.example.demo.cucumber.glue;

import com.example.demo.vin.VinService;
import io.cucumber.java8.En;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public class VinGue implements En {

    private BigDecimal result;

    public VinGue(VinService vinService){

        When("je calcule le montant total", () -> {
            result = vinService.calculerMontantTotal();
        });

        Then("j'obtiens {double}", (Double nbExcepted) -> {
            Assertions.assertThat(result.stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(nbExcepted).stripTrailingZeros());
        });

    }
}
