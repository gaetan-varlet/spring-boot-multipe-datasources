package com.example.demo.base2.repositories;

import com.example.demo.base2.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Integer> {
}
