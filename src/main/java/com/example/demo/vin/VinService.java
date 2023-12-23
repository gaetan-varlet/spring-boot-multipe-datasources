package com.example.demo.vin;

import com.example.demo.base1.entities.Vin;
import com.example.demo.base1.repositories.VinRepository;
import com.example.demo.base2.entities.Vente;
import com.example.demo.base2.repositories.VenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class VinService {

    private final VinRepository vinRepository;
    private final VenteRepository venteRepository;

    private static int id = 1;

    public BigDecimal calculerMontantTotal() {
        List<Vin> vins = vinRepository.findAll();
        BigDecimal total = calculerMontantTotal(vins);
        venteRepository.save(Vente.builder().id(id++).montantTotal(total).quantite(vins.size()).build());
        return total;
    }

    private BigDecimal calculerMontantTotal(List<Vin> vins) {
        BigDecimal montantTotal = BigDecimal.ZERO;
        for (Vin vin : vins) {
            montantTotal = montantTotal.add(vin.getPrix().multiply(BigDecimal.valueOf(vin.getQuantite())));
        }
        return montantTotal;
    }

}
