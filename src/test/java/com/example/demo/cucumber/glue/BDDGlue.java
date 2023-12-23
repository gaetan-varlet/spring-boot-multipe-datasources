package com.example.demo.cucumber.glue;

import com.example.demo.base1.entities.Vin;
import com.example.demo.base1.repositories.VinRepository;
import com.example.demo.base2.repositories.VenteRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BDDGlue implements En {

    private static int id = 1;

    public BDDGlue(VinRepository vinRepository, VenteRepository venteRepository){
        Before(() -> {
            vinRepository.deleteAllInBatch();
            venteRepository.deleteAllInBatch();
        });

        Given("les vins suivants", (DataTable dataTable) -> {
            List<Map<String, String>> datas = dataTable.asMaps();
            List<Vin> vinsToSave = datas.stream().map(this::transformMapToVin).toList();
            vinRepository.saveAll(vinsToSave);
        });
    }

    private Vin transformMapToVin(Map<String, String> line) {
        return Vin.builder()
                .id(id++)
                .chateau(line.get("chateau"))
                .quantite(Integer.valueOf(line.get("quantite")))
                .prix(BigDecimal.valueOf(Double.valueOf(line.get("prix"))))
                .build();
    }
}
