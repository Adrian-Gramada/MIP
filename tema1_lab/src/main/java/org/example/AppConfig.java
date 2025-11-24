package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Configuratie aplicatie citita dintr-un fisier JSON extern (config.json). */
public final class AppConfig {

    private static final String DEFAULT_RESTAURANT_NAME = "La Andrei";
    private static final double DEFAULT_TVA = 0.09; // 9%
    private static final String DEFAULT_EXPORT_PATH = "meniu_export.json";

    private static AppConfig instance;

    private final String restaurantName;
    private final double tva;
    private final String menuExportPath;

    private AppConfig(String restaurantName, double tva, String menuExportPath) {
        this.restaurantName = restaurantName;
        this.tva = tva;
        this.menuExportPath = menuExportPath;
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static AppConfig load() {
        String resourceName = "/config.json";
        try (InputStream is = AppConfig.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                System.err.println("Eroare: Fisierul de configurare lipseste (config.json). Se folosesc valorile implicite.");
                return new AppConfig(DEFAULT_RESTAURANT_NAME, DEFAULT_TVA, DEFAULT_EXPORT_PATH);
            }
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line.trim());
                }
            }
            String json = sb.toString();
            // parsare extrem de simpla, fara librarii externe (cautam cheile cunoscute)
            String name = extractString(json, "restaurantName", DEFAULT_RESTAURANT_NAME);
            double tva = extractDouble(json, "tva", DEFAULT_TVA);
            String exportPath = extractString(json, "menuExportPath", DEFAULT_EXPORT_PATH);
            return new AppConfig(name, tva, exportPath);
        } catch (IOException | RuntimeException e) {
            System.err.println("Eroare: Fisierul de configurare este corupt sau nu poate fi citit. Se folosesc valorile implicite.");
            return new AppConfig(DEFAULT_RESTAURANT_NAME, DEFAULT_TVA, DEFAULT_EXPORT_PATH);
        }
    }

    private static String extractString(String json, String key, String defaultValue) {
        try {
            String pattern = "\"" + key + "\"";
            int idx = json.indexOf(pattern);
            if (idx == -1) return defaultValue;
            int colon = json.indexOf(':', idx);
            int firstQuote = json.indexOf('"', colon + 1);
            int secondQuote = json.indexOf('"', firstQuote + 1);
            if (firstQuote == -1 || secondQuote == -1) return defaultValue;
            return json.substring(firstQuote + 1, secondQuote);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static double extractDouble(String json, String key, double defaultValue) {
        try {
            String pattern = "\"" + key + "\"";
            int idx = json.indexOf(pattern);
            if (idx == -1) return defaultValue;
            int colon = json.indexOf(':', idx);
            int comma = json.indexOf(',', colon + 1);
            int endBrace = json.indexOf('}', colon + 1);
            int end = (comma == -1) ? endBrace : Math.min(comma, endBrace);
            if (end == -1) end = json.length();
            String num = json.substring(colon + 1, end).trim();
            return Double.parseDouble(num);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getTva() {
        return tva;
    }

    public String getMenuExportPath() {
        return menuExportPath;
    }
}
