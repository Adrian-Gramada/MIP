package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Meniu {
    private final List<Produs> produse = new ArrayList<>();

    // permite adaugarea produselor dar doar daca sunt Mancare sau Bautura (robustete)
    public void adaugareProdus(Produs produs) {
        if (!(produs instanceof Mancare) && !(produs instanceof Bautura)) {
            throw new IllegalArgumentException("Produsul trebuie sa fie mancare sau bautura");
        }
        produse.add(produs);
    }

    public List<Produs> getProduse() {
        return produse;
    }

    // 1. Organizarea meniului pe categorii - fara stream
    public List<Produs> produseDinCategorie(Categorie categorie) {
        List<Produs> rezultat = new ArrayList<>();
        for (Produs p : produse) {
            if (p.getCategorie() == categorie) {
                rezultat.add(p);
            }
        }
        return rezultat;
    }

    // 2.a Toate preparatele vegetariene, sortate alfabetic (folosim doar Mancare si o regula simpla: numele contine "(V)")
    public List<Produs> preparateVegetarieneSortate() {
        List<Produs> rezultat = new ArrayList<>();
        for (Produs p : produse) {
            if (p instanceof Mancare && p.getNume().toLowerCase().contains("(v)")) {
                rezultat.add(p);
            }
        }
        rezultat.sort(Comparator.comparing(Produs::getNume, String.CASE_INSENSITIVE_ORDER));
        return rezultat;
    }

    // 2.b Pretul mediu al deserturilor - intoarcem Optional<Double>, nu Optional<Optional<...>>
    public Optional<Double> pretMediuDeserturi() {
        double suma = 0.0;
        int count = 0;
        for (Produs p : produse) {
            if (p.getCategorie() == Categorie.DESERT) {
                suma += p.getPret();
                count++;
            }
        }
        if (count == 0) {
            return Optional.empty();
        }
        return Optional.of(suma / count);
    }

    // 2.c Exista vreun preparat cu pret > valoare data (ex. 100 RON)
    public boolean existaProdusCuPretMaiMareDecat(double prag) {
        for (Produs p : produse) {
            if (p.getPret() > prag) {
                return true;
            }
        }
        return false;
    }

    // 3. Cautare sigura in meniu (Optional, dar nu dublu Optional)
    public Optional<Produs> cautareProdusDupaNume(String nume) {
        if (nume == null) {
            return Optional.empty();
        }
        for (Produs p : produse) {
            if (p.getNume().equalsIgnoreCase(nume)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    // 3. Export meniu in JSON intr-un fisier configurabil
    public void exportaMeniuInJson() {
        String path = AppConfig.getInstance().getMenuExportPath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("[\n");
            for (int i = 0; i < produse.size(); i++) {
                Produs p = produse.get(i);
                writer.write("  {\n");
                writer.write("    \"nume\": \"" + escapeJson(p.getNume()) + "\",\n");
                writer.write("    \"pret\": " + p.getPret() + ",\n");
                writer.write("    \"categorie\": \"" + p.getCategorie().name() + "\"\n");
                writer.write("  }");
                if (i < produse.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]\n");
            System.out.println("Meniu exportat cu succes in fisierul: " + path);
        } catch (IOException e) {
            System.err.println("Eroare la exportul meniului in fisierul '" + path + "': " + e.getMessage());
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
