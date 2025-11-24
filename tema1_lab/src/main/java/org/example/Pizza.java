package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pizza extends Mancare {

    public enum TipBlat {
        SUBTIRE, GROS, PANE
    }

    public enum TipSos {
        ROSII, ALB, PESTO
    }

    public enum Topping {
        EXTRA_MOZZARELLA,
        CIUPERCI,
        SALAM,
        ANANAS,
        MASLINE,
        CEAPA,
        ARDEI
    }

    private final TipBlat blat;
    private final TipSos sos;
    private final List<Topping> toppinguri;

    private Pizza(String nume, double pret, int gramaj,
                  TipBlat blat, TipSos sos, List<Topping> toppinguri) {
        super(nume, pret, gramaj);
        this.blat = Objects.requireNonNull(blat, "blat obligatoriu");
        this.sos = Objects.requireNonNull(sos, "sos obligatoriu");
        this.toppinguri = Collections.unmodifiableList(new ArrayList<>(toppinguri));
    }

    public TipBlat getBlat() {
        return blat;
    }

    public TipSos getSos() {
        return sos;
    }

    public List<Topping> getToppinguri() {
        return toppinguri;
    }

    @Override
    public String getDetalii() {
        return String.format("> %s - %.1f RON - Blat: %s, Sos: %s, Toppinguri: %s",
                getNume(), getPret(), blat, sos, toppinguri);
    }

    public static class Builder {
        private final String nume;
        private double pret;
        private int gramaj;
        private TipBlat blat;
        private TipSos sos;
        private final List<Topping> toppinguri = new ArrayList<>();

        public Builder(String nume) {
            this.nume = Objects.requireNonNull(nume, "nume obligatoriu");
        }

        public Builder pret(double pret) {
            this.pret = pret;
            return this;
        }

        public Builder gramaj(int gramaj) {
            this.gramaj = gramaj;
            return this;
        }

        public Builder blat(TipBlat blat) {
            this.blat = blat;
            return this;
        }

        public Builder sos(TipSos sos) {
            this.sos = sos;
            return this;
        }

        public Builder adaugaTopping(Topping topping) {
            this.toppinguri.add(Objects.requireNonNull(topping));
            return this;
        }

        public Pizza build() {
            if (blat == null) {
                throw new IllegalStateException("Trebuie sa alegi un tip de blat pentru pizza");
            }
            if (sos == null) {
                throw new IllegalStateException("Trebuie sa alegi un tip de sos pentru pizza");
            }
            return new Pizza(nume, pret, gramaj, blat, sos, toppinguri);
        }
    }
}

