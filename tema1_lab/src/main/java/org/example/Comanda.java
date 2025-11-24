package org.example;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private final List<OrderItem> produse = new ArrayList<>();

    private org.example.RegulaCalculTotal regulaZilei; // optional

    public void setRegulaZilei(org.example.RegulaCalculTotal regulaZilei) {
        this.regulaZilei = regulaZilei;
    }

    public void adaugaProdus(Produs produs, int cantitate) {
        // Permite doar Mancare si Bautura
        if (!(produs instanceof Mancare || produs instanceof Bautura)) {
            throw new IllegalArgumentException("Doar produse de tip Mancare sau Bautura pot fi adaugate in comanda.");
        }
        if (cantitate <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie sa fie pozitiva.");
        }
        produse.add(new OrderItem(produs, cantitate));
    }

    public double calculeazaSubtotal() {
        double total = 0;
        for (OrderItem item : produse) {
            total += item.getProdus().getPret() * item.getCantitate();
        }
        return total;
    }

    public double calculeazaTotal() {
        double subtotal = calculeazaSubtotal();
        if (regulaZilei != null) {
            // Cast pentru a ajuta analiza sa vada semnatura completa
            @SuppressWarnings("unchecked")
            java.util.List<org.example.OrderItem> items = (java.util.List<org.example.OrderItem>)(java.util.List<?>) produse;
            subtotal = regulaZilei.aplica(items, subtotal);
        }
        // TVA aplicat dupa reduceri, luat din configuratia externa
        return subtotal * (1 + Taxe.TVA());
    }

    public List<OrderItem> getProduse(){
        return produse;
    }

    public String generareNota() {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Nota de plata --\n");
        for (OrderItem item : produse) {
            sb.append(String.format("%dx %s @ %.2f RON = %.2f RON\n", item.getCantitate(), item.getProdus().getNume(), item.getProdus().getPret(), item.getProdus().getPret() * item.getCantitate()));
        }
        double subtotal = calculeazaSubtotal();
        sb.append(String.format("Subtotal: %.2f RON\n", subtotal));
        if (regulaZilei != null) {
            @SuppressWarnings("unchecked")
            java.util.List<org.example.OrderItem> items = (java.util.List<org.example.OrderItem>)(java.util.List<?>) produse;
            double dupaRegula = regulaZilei.aplica(items, subtotal);
            double reducere = subtotal - dupaRegula;
            if (reducere > 0) {
                sb.append(String.format("Reducere aplicata: -%.2f RON (%s)\n", reducere, regulaZilei.getClass().getSimpleName()));
                subtotal = dupaRegula;
            }
        }
        double tva = subtotal * Taxe.TVA();
        sb.append(String.format("TVA (%.0f%%): %.2f RON\n", Taxe.TVA() * 100, tva));
        sb.append(String.format("Total: %.2f RON\n", subtotal + tva));
        return sb.toString();
    }
}
