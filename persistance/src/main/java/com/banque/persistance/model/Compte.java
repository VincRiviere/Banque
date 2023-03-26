package com.banque.persistance.model;
import javax.persistence.*;
@Entity
@Table(name = "compte")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float solde;
    private float decouvertAutorise;

    public float getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(float decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "numero", length = 50)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @Override
    public String toString() {
        return "Compte [id=" + id + ", numero=" + numero;
    }

}