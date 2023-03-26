package com.banque.persistance.model;

import javax.persistence.*;

@Entity
@Table(name = "demandevirement")
public class DemandeVirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Compte compteSource;

    @ManyToOne
    private Compte compteDest;

    private float montant;

    private boolean acceptee;

    public DemandeVirement(Compte compteSource, Compte compteDest, float montant) {
        this.compteSource = compteSource;
        this.compteDest = compteDest;
        this.montant = montant;
        this.acceptee = false;
    }

    public DemandeVirement() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Compte getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Compte compteSource) {
        this.compteSource = compteSource;
    }

    public Compte getCompteDest() {
        return compteDest;
    }

    public void setCompteDest(Compte compteDest) {
        this.compteDest = compteDest;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public boolean isAcceptee() {
        return acceptee;
    }

    public void setAcceptee(boolean acceptee) {
        this.acceptee = acceptee;
    }
}
