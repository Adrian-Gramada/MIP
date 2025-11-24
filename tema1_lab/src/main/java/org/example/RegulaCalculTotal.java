package org.example;

public interface RegulaCalculTotal {
    /**
     * Aplica regula asupra subtotalului si returneaza noul subtotal (inainte de TVA).
     * @param items lista de elemente din comanda
     * @param subtotal subtotal initial (suma pret * cantitate)
     * @return subtotal modificat (nu include TVA)
     */
    double aplica(java.util.List<org.example.OrderItem> items, double subtotal);
}
