package com.alkafol.brtmicroservice.repositories;

import com.alkafol.brtmicroservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByNumber(String number);
}
