package org.example;

public abstract class Produs {
    private final String nume;
    private final double pret;
    private final Categorie categorie; // folosit pentru organizarea meniului pe categorii

    public Produs(String nume, double pret, Categorie categorie) {
        this.nume = nume;
        this.pret = pret;
        this.categorie = categorie;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public Categorie getCategorie() {
        return categorie;
    }


    public abstract String getDetalii();
}