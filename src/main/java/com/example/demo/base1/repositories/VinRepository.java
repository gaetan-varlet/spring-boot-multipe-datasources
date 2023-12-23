package com.example.demo.base1.repositories;

import com.example.demo.base1.entities.Vin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinRepository extends JpaRepository<Vin, Integer> {
}
