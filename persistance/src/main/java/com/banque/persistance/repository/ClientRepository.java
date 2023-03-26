package com.banque.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.banque.persistance.model.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}
