package org.example;

public class Mancare extends Produs {
    private final int gramaj;

    public Mancare(String nume, double pret, int gramaj) {
        super(nume, pret, Categorie.FEL_PRINCIPAL);
        this.gramaj = gramaj;
    }

    @Override
    public String getDetalii() {
        return String.format("> %s - %.1f RON - Gramaj: %dg", getNume(), getPret(), gramaj);
    }
}
