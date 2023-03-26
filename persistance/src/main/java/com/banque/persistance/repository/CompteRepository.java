package com.banque.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banque.persistance.model.Compte;

import java.util.List;

@Repository
public interface CompteRepository extends CrudRepository<Compte, Integer> {
    List<Compte> findByClientId(@Param("clientId") Integer clientId);
}