package com.banque.persistance.service;

import lombok.Data;

import com.banque.persistance.model.DemandeVirement;
import com.banque.persistance.repository.DemandeVirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class DemandeVirementService {

    @Autowired
    private DemandeVirementRepository demandeVirementRepository;

    public DemandeVirement saveDemandeVirement(DemandeVirement demandeVirement) {
        return demandeVirementRepository.save(demandeVirement);
    }

    public List<DemandeVirement> getDemandesEnAttente() {
        return demandeVirementRepository.findByAcceptee(false);
    }

    public DemandeVirement getDemandeVirementById(Integer id) {
        return demandeVirementRepository.findById(id).orElse(null);
    }

    public void validerDemandeVirement(DemandeVirement demandeVirement) {
        demandeVirement.setAcceptee(true);
        demandeVirementRepository.save(demandeVirement);
    }
}

