package com.example.demo.vin;

import com.example.demo.base1.entities.Vin;
import com.example.demo.base1.repositories.VinRepository;
import com.example.demo.base2.entities.Vente;
import com.example.demo.base2.repositories.VenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class VinService {

    private final VinRepository vinRepository;
    private final VenteRepository venteRepository;
    private final DataSource base1DataSource;
    @PersistenceContext(unitName = "base1EntityManagerFactory")
    private EntityManager base1EntityManager;

    private int id = 1;

    public BigDecimal calculerMontantTotal() {
        sqlRequest();
        entityManagerRequest();
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

    private void sqlRequest() {
        try (Connection connection = base1DataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT chateau FROM vin");
                ResultSet rs = ps.executeQuery();) {
            while (rs.next()) {
                log.info("RESULTAT : {}", rs.getString("chateau"));
            }

        } catch (SQLException e) {
            log.error("ERREUR SQL", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void entityManagerRequest() {
        String sql = "SELECT DISTINCT quantite FROM vin";
        Query query = base1EntityManager.createNativeQuery(sql);
        List<Integer> result = query.getResultList();
        result.forEach(r -> log.info("RESULTAT : {}", r));
    }

}
