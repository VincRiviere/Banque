package com.banque.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banque.persistance.model.DemandeVirement;

import java.util.List;

@Repository
public interface DemandeVirementRepository extends CrudRepository<DemandeVirement, Integer> {
    List<DemandeVirement> findByAcceptee(@Param("acceptee") boolean acceptee);
}
