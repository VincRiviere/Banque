package com.banque.persistance.controller;

import com.banque.persistance.model.Client;
import com.banque.persistance.model.Compte;
import com.banque.persistance.model.DemandeVirement;
import com.banque.persistance.repository.ClientRepository;
import com.banque.persistance.repository.CompteRepository;
import com.banque.persistance.repository.DemandeVirementRepository;
import com.banque.persistance.service.ClientService;
import com.banque.persistance.service.CompteService;
import com.banque.persistance.service.DemandeVirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController // equivalent a @Controller + @ResponseBody
public class CompteController {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DemandeVirementRepository demandeVirementRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DemandeVirementService demandeVirementService;

    @GetMapping("/compte/lister") public ModelAndView listeComptes() {
        return new ModelAndView("listeComptes", "comptes", compteService.getComptes());
    }

    @GetMapping("/compte/lister/{id}")
    public ModelAndView detailCompte (@PathVariable("id") final Integer id) {
        ModelAndView mav = new ModelAndView("detailCompte");
        Compte compte = compteRepository.findById(id).orElse(null);
        if (compte == null) {
            mav.setViewName("redirect:/compte/lister");
            return mav;
        }
        Client client = compte.getClient();
        List<Compte> comptes = compteRepository.findByClientId(client.getId());
        mav.addObject("allComptes", compteService.getComptes());
        mav.addObject("compte", compte);
        mav.addObject("comptes", comptes);
        return mav;
    }

    @GetMapping("/compte/creer")
    public ModelAndView creerCompte(@ModelAttribute("compte") Compte compte, ModelMap model) {
        model.addAttribute("clients", clientService.getClients());
        return new ModelAndView("creerCompte", "compte", compte);
    }

    @PostMapping("/compte/creer")
    public ModelAndView submit (@ModelAttribute("compte") Compte compte, ModelMap model) {
        model.addAttribute("numero", compte.getNumero());
        model.addAttribute("client", compte.getClient());
        model.addAttribute("solde", compte.getSolde());
        model.addAttribute("decouvertAutorise", compte.getDecouvertAutorise());

        compteService.saveCompte (compte);
        return listeComptes();
    }

    @GetMapping("/compte/editer/{id}")
    public ModelAndView editerCompte(@PathVariable("id") final Integer id,@ModelAttribute("compte") Compte c, ModelMap model) {
        model.addAttribute("clients", clientService.getClients());
        Optional<Compte> compte = compteService.getCompte(id);
        return new ModelAndView("editerCompte", "compte", compte.orElse(null));
    }

    @PostMapping("/compte/editer")
    public ModelAndView editerCompte(@ModelAttribute("compte") Compte compte, ModelMap model) {
        compteService.saveCompte(compte);
        return listeComptes();
    }

    @GetMapping("/compte/supprimer/{id}")
    public ModelAndView supprimerCompte(@PathVariable("id") final Integer id) {
        compteService.deleteCompte(id);
        return listeComptes();
    }

    @PostMapping("/compte/retrait")
    public ModelAndView retrait(@RequestParam("idR") final Integer id,
                                @RequestParam("montantR") float montant) {
        ModelAndView mav = new ModelAndView("redirect:/compte/lister/" + id);
        Compte compte = compteRepository.findById(id).orElse(null);
        if (compte != null) {
            float solde = compte.getSolde();
            float decouvertAutorise = compte.getDecouvertAutorise();
            if (solde + decouvertAutorise >= montant) {
                float nouveauSolde = solde - montant;
                compte.setSolde(nouveauSolde);
                compteRepository.save(compte);
            }
        }
        return mav;
    }

    @PostMapping("/compte/ajout")
    public ModelAndView ajout(@RequestParam("idA") final Integer id,
                                @RequestParam("montantA") float montant) {
        ModelAndView mav = new ModelAndView("redirect:/compte/lister/" + id);
        Compte compte = compteRepository.findById(id).orElse(null);
        if (compte != null) {
            float nouveauSolde = compte.getSolde() + montant;
            compte.setSolde(nouveauSolde);
            compteRepository.save(compte);
        }
        return mav;
    }

    @PostMapping("/compte/virementInterne")
    public ModelAndView virementInterne(@RequestParam("compteSourceIdI") final Integer compteSourceId,
                                 @RequestParam("compteDestIdI") final Integer compteDestId,
                                 @RequestParam("montantI") float montant) {
        ModelAndView mav = new ModelAndView("redirect:/compte/lister/" + compteSourceId);
        Compte compteSource = compteRepository.findById(compteSourceId).orElse(null);
        Compte compteDest = compteRepository.findById(compteDestId).orElse(null);
        if (compteSource != null && compteDest != null) {
            float solde = compteSource.getSolde();
            float decouvertAutorise = compteSource.getDecouvertAutorise();
            if (solde + decouvertAutorise >= montant) {
                float nouveauSoldeSource = compteSource.getSolde() - montant;
                compteSource.setSolde(nouveauSoldeSource);
                compteDest.setSolde(compteDest.getSolde() + montant);
                compteRepository.save(compteSource);
                compteRepository.save(compteDest);
            }

        }
        return mav;
    }

    @PostMapping("/compte/virementExterne")
    public ModelAndView virementExterne(@RequestParam("compteSourceIdE") Integer compteSourceId,
                                 @RequestParam("compteDestIdE") Integer compteDestId,
                                 @RequestParam("montantE") float montant) {
        ModelAndView mav = new ModelAndView("redirect:/compte/lister/" + compteSourceId);
        Compte compteSource = compteRepository.findById(compteSourceId).orElse(null);
        Compte compteDest = compteRepository.findById(compteDestId).orElse(null);
        if (compteSource != null && compteDest != null) {
            DemandeVirement demandeVirement = new DemandeVirement(compteSource, compteDest, montant);
            demandeVirementRepository.save(demandeVirement);
        }
        return mav;
    }

}
