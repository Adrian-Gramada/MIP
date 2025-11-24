package org.example;

public class Bautura extends Produs {
    private final int volum;
    private final boolean alcoolic;

    public Bautura(String nume, double pret, int volum, boolean alcoolic) {
        // Apelam constructorul parintelui pentru a initializa numele si pretul
        super(nume, pret, alcoolic ? Categorie.BAUTURI_ALCOOLICE : Categorie.BAUTURI_RACORITOARE);
        this.volum = volum;
        this.alcoolic = alcoolic;
    }

    public boolean isAlcoolic() {
        return alcoolic;
    }

    public int getVolum() {
        return volum;
    }

    @Override
    public String getDetalii() {
        return String.format("> %s - %.1f RON - Volum: %dml%s", getNume(), getPret(), volum,
                alcoolic ? " (alcoolic)" : "");
    }
}
