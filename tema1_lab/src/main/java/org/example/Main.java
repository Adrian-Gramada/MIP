package org.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Definire meniu folosind clasa Meniu inteligenta
        Meniu meniu = new Meniu();
        // Mancare (feluri principale)
        meniu.adaugareProdus(new Mancare("Pizza Margherita", 45.0, 450));
        meniu.adaugareProdus(new Mancare("Pizza Quattro Formaggi", 55.0, 480));
        meniu.adaugareProdus(new Mancare("Paste Carbonara", 52.5, 400));
        // Desert
        meniu.adaugareProdus(new Mancare("Tiramisu", 28.0, 150));
        // Bauturi
        meniu.adaugareProdus(new Bautura("Limonada", 15.0, 400, false));
        meniu.adaugareProdus(new Bautura("Apa Plata", 8.0, 500, false));
        meniu.adaugareProdus(new Bautura("Bere Lager", 12.0, 500, true));
        meniu.adaugareProdus(new Bautura("Vin Rosu", 25.0, 150, true));

        // Pizza customizabila
        Pizza pizzaCustom = new Pizza.Builder("Pizza Custom (V)")
                .pret(60.0)
                .gramaj(500)
                .blat(Pizza.TipBlat.SUBTIRE)
                .sos(Pizza.TipSos.ROSII)
                .adaugaTopping(Pizza.Topping.EXTRA_MOZZARELLA)
                .adaugaTopping(Pizza.Topping.CIUPERCI)
                .build();
        meniu.adaugareProdus(pizzaCustom);

        System.out.println("--- Meniul Restaurantului \"La Andrei\" ---");
        for (Produs produs : meniu.getProduse()) {
            System.out.println(produs.getDetalii());
        }
        System.out.println("-".repeat(55));

        // Export meniu in JSON (iteratia 4 - export meniu)
        meniu.exportaMeniuInJson();

        // Interogari pe meniu
        System.out.println("Preparatele vegetariene (marcate cu (V)), sortate alfabetic:");
        for (Produs p : meniu.preparateVegetarieneSortate()) {
            System.out.println(p.getDetalii());
        }
        System.out.println("-".repeat(55));

        System.out.println("Deserturi - pret mediu (daca exista):");
        Optional<Double> medieDeserturi = meniu.pretMediuDeserturi();
        if (medieDeserturi.isPresent()) {
            System.out.printf("Pret mediu deserturi: %.2f RON%n", medieDeserturi.get());
        } else {
            System.out.println("Nu exista deserturi in meniu.");
        }

        boolean existaScump = meniu.existaProdusCuPretMaiMareDecat(100.0);
        System.out.println("Exista produs cu pret > 100 RON? " + (existaScump ? "Da" : "Nu"));

        // Cautare sigura in meniu
        System.out.println("Cautam produsul 'Pizza Margherita':");
        Optional<Produs> cautat = meniu.cautareProdusDupaNume("Pizza Margherita");
        System.out.println(cautat.map(Produs::getDetalii).orElse("Produsul nu a fost gasit"));

        System.out.println("Cautam produsul 'Produs Inexistent':");
        Optional<Produs> cautat2 = meniu.cautareProdusDupaNume("Produs Inexistent");
        System.out.println(cautat2.map(Produs::getDetalii).orElse("Produsul nu a fost gasit"));

        // Creare comanda
        Comanda comanda = new Comanda();
        List<Produs> listaProduse = new ArrayList<>(meniu.getProduse());
        comanda.adaugaProdus(listaProduse.get(0), 2); // 2x Pizza Margherita
        comanda.adaugaProdus(listaProduse.get(6), 2); // 2x Bere Lager
        comanda.adaugaProdus(listaProduse.get(4), 1); // 1x Limonada
        comanda.adaugaProdus(listaProduse.get(7), 1); // 1x Vin Rosu

        System.out.printf("Subtotal fara discount: %.2f RON%n", comanda.calculeazaSubtotal());
        System.out.println(comanda.generareNota());

        // Simulam Happy Hour (fortam regula indiferent de ora actuala, doar demonstrativ)
        RegulaCalculTotal happyHour = new HappyHourAlcoolice(LocalTime.MIN, LocalTime.MAX, 0.20); // aplic mereu pentru demo
        comanda.setRegulaZilei(happyHour);
        System.out.printf("Total cu Happy Hour (alcoolice -20%%): %.2f RON%n", comanda.calculeazaTotal());
        System.out.println(comanda.generareNota());

        // Schimbam regula la reducere totala 10%
        comanda.setRegulaZilei(new DiscountProcentualTotal(0.10));
        System.out.printf("Total cu reducere 10%% nota: %.2f RON%n", comanda.calculeazaTotal());
        System.out.println(comanda.generareNota());

        // Schimbam regula la oferta pizza + bautura gratis
        comanda.setRegulaZilei(new OfertaPizzaBauturaGratis());
        System.out.printf("Total cu oferta Pizza + Bautura gratis: %.2f RON%n", comanda.calculeazaTotal());
        System.out.println(comanda.generareNota());
    }
}