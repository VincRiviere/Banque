package com.banque.persistance.service;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banque.persistance.model.Compte;
import com.banque.persistance.repository.CompteRepository;

import java.util.Optional;

@Data
@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    public Optional<Compte> getCompte(final Integer id) {
        return compteRepository.findById(id);
    }

    public Iterable<Compte> getComptes() {
        return compteRepository.findAll();
    }

    public void deleteCompte(final Integer id) {
        compteRepository.deleteById(id);
    }

    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }
}