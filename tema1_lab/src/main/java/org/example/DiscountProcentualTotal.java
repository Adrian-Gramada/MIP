package org.example;

import java.util.List;

/**
 * Regula generica: aplica un discount procentual asupra intregului subtotal.
 */
public class DiscountProcentualTotal implements RegulaCalculTotal {
    private final double procent; // 0.10 pentru 10%

    public DiscountProcentualTotal(double procent) {
        if (procent < 0 || procent > 1) {
            throw new IllegalArgumentException("Procentul trebuie sa fie intre 0 si 1.");
        }
        this.procent = procent;
    }

    @Override
    public double aplica(java.util.List<org.example.OrderItem> items, double subtotal) {
        return subtotal * (1 - procent);
    }
}
