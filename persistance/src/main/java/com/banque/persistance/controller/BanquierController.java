package com.banque.persistance.controller;

import com.banque.persistance.model.Compte;
import com.banque.persistance.model.DemandeVirement;
import com.banque.persistance.repository.ClientRepository;
import com.banque.persistance.repository.CompteRepository;
import com.banque.persistance.repository.DemandeVirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/banquier")
public class BanquierController {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DemandeVirementRepository demandeVirementRepository;

    @GetMapping("/demandes")
    public ModelAndView listerDemandes() {
        ModelAndView mav = new ModelAndView("demandes");
        List<DemandeVirement> demandes = demandeVirementRepository.findByAcceptee(false);
        mav.addObject("demandes", demandes);
        return mav;
    }

    @PostMapping("/demandes")
    public ModelAndView validerDemande(@RequestParam("demandeId") final Integer id) {
        ModelAndView mav = new ModelAndView("redirect:/banquier/demandes");
        Optional<DemandeVirement> demande = demandeVirementRepository.findById(id);
        if (demande.isPresent()) {
            DemandeVirement d = demande.get();
            Compte compteSource = d.getCompteSource();
            Compte compteDest = d.getCompteDest();
            float montant = d.getMontant();
            if (compteSource.getSolde() + compteSource.getDecouvertAutorise() >= montant) {
                float nouveauSoldeSource = compteSource.getSolde() - montant;
                float nouveauSoldeDest = compteDest.getSolde() + montant;
                compteSource.setSolde(nouveauSoldeSource);
                compteDest.setSolde(nouveauSoldeDest);
                compteRepository.save(compteSource);
                compteRepository.save(compteDest);
                d.setAcceptee(true);
                demandeVirementRepository.save(d);
            } else {
                boolean soldeInsuffisant = true;
                mav.addObject("soldeInsuffisant", soldeInsuffisant);
            }
        }
        return mav;
    }
}
