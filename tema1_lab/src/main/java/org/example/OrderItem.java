package org.example;

public class OrderItem {
    private final Produs produs;
    private final int cantitate;

    public OrderItem(Produs produs, int cantitate) {
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public Produs getProdus() {
        return produs;
    }

    public int getCantitate() {
        return cantitate;
    }
}

