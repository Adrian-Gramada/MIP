package org.example;

/** Centralizare taxe folosite in sistem. */
public final class Taxe {
    private Taxe() {}
    // TVA este acum citit din configuratia externa (config.json) prin AppConfig
    public static double TVA() {
        return AppConfig.getInstance().getTva();
    }
}
