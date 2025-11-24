package org.example;

import java.time.LocalTime;
import java.util.List;

/**
 * Regula: intre orele start (inclusiv) si end (exclusiv) toate bauturile alcoolice au reducere procentuala.
 */
public class HappyHourAlcoolice implements RegulaCalculTotal {
    private final LocalTime start;
    private final LocalTime end;
    private final double procentReducere; // ex: 0.20 pentru 20%

    public HappyHourAlcoolice(LocalTime start, LocalTime end, double procentReducere) {
        this.start = start;
        this.end = end;
        this.procentReducere = procentReducere;
    }

    public HappyHourAlcoolice() {
        this(LocalTime.of(17,0), LocalTime.of(19,0), 0.20); // implicit 17-19, 20%
    }

    @Override
    public double aplica(java.util.List<org.example.OrderItem> items, double subtotal) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(start) || !now.isBefore(end)) { // in afara intervalului
            return subtotal;
        }
        double reducere = 0;
        for (OrderItem item : items) {
            Produs p = item.getProdus();
            if (p instanceof Bautura b && b.isAlcoolic()) {
                reducere += b.getPret() * item.getCantitate() * procentReducere;
            }
        }
        return subtotal - reducere;
    }
}
