package com.alkafol.brtmicroservice.repositories;

import com.alkafol.brtmicroservice.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, String> {
}
