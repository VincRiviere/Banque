package com.banque.persistance.controller;

import com.banque.persistance.model.Compte;
import com.banque.persistance.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.banque.persistance.model.Client;
import com.banque.persistance.service.ClientService;
import com.banque.persistance.service.CompteService;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CompteService compteService;

    @GetMapping("/client/lister")
    public ModelAndView listeClients() {
        return new ModelAndView("listeClients", "clients", clientService.getClients());
    }

    @GetMapping("/client/lister/{id}")
    public ModelAndView detailClient(@PathVariable("id") final Integer id) {
        Optional<Client> client = clientService.getClient(id);
        return new ModelAndView("detailClient", "client", client.orElse(null));
    }

    @GetMapping("/client/creer")
    public ModelAndView creerClient() {
        Client c = new Client();
        return new ModelAndView("creerClient", "client", c);
    }

    @PostMapping("/client/creer")
    public ModelAndView submit(@ModelAttribute("client") Client client, ModelMap model) {
        model.addAttribute("nom", client.getNom());
        model.addAttribute("prenom", client.getPrenom());
        clientService.saveClient(client);

        return listeClients();
    }

    @GetMapping("/client/editer/{id}")
    public ModelAndView editerClient(@PathVariable("id") final Integer id) {
        Optional<Client> client = clientService.getClient(id);
        return new ModelAndView("editerClient", "client", client.orElse(null));
    }

    @PostMapping("/client/editer")
    public ModelAndView editerClient(@ModelAttribute("client") Client client, ModelMap model) {
        clientService.saveClient(client);
        return listeClients();
    }

    @GetMapping("/client/supprimer/{id}")
    public ModelAndView supprimerClient(@PathVariable("id") final Integer id)
    {
        Optional<Client> clientById = clientService.getClient(id);
        Client client = clientById.get();
        List<Compte> comptes = client.getComptes();
        for (Compte compte : comptes) {
            compteService.deleteCompte(compte.getId());
        }
        clientService.deleteClient(id);
        return listeClients();
    }

}

