package org.example;

import java.util.List;

/**
 * Regula: pentru fiecare pizza comandata (Mancare cu numele ce contine "Pizza"), se ofera gratuit o bautura.
 * Se aplica reducere egala cu pretul celei mai ieftine bauturi pentru fiecare pereche, pana la numarul de bauturi existente.
 */
public class OfertaPizzaBauturaGratis implements RegulaCalculTotal {
    @Override
    public double aplica(java.util.List<org.example.OrderItem> items, double subtotal) {
        int numarPizza = 0;
        int numarBauturi = 0;
        double celMaiIeftinPretBautura = Double.MAX_VALUE;

        for (OrderItem item : items) {
            Produs p = item.getProdus();
            if (p instanceof Mancare m && m.getNume().toLowerCase().contains("pizza")) {
                numarPizza += item.getCantitate();
            } else if (p instanceof Bautura b) {
                numarBauturi += item.getCantitate();
                celMaiIeftinPretBautura = Math.min(celMaiIeftinPretBautura, b.getPret());
            }
        }

        if (numarPizza == 0 || numarBauturi == 0) {
            return subtotal; // nu se aplica reducere
        }
        if (celMaiIeftinPretBautura == Double.MAX_VALUE) {
            return subtotal; // defensiv
        }

        int perechi = Math.min(numarPizza, numarBauturi);
        double reducere = perechi * celMaiIeftinPretBautura;
        return subtotal - reducere;
    }
}
