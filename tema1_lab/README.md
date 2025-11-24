# Sistem Comenzi Restaurant (Faza 2)

## Functionalitati implementate
- Meniu cu produse de tip `Mancare` si `Bautura` (doar aceste doua categorii sunt permise).
- Clasa abstracta `Produs` cu implementari specifice.
- Sistem de comanda (`Comanda`) care accepta adaugare produse cu cantitate.
- Calcul subtotal si total cu TVA (TVA centralizat in `Taxe.TVA`).
- Mecanism flexibil de reguli de discount ("regula zilei") prin interfata `RegulaCalculTotal`.
- Reguli disponibile:
  - `HappyHourAlcoolice`: reducere procentuala pentru bauturi alcoolice intre un interval orar.
  - `DiscountProcentualTotal`: reducere procentuala pe intreaga comanda.
  - `OfertaPizzaBauturaGratis`: pentru fiecare pizza, o bautura (cea mai ieftina) gratuita.
- Metoda `generareNota()` afiseaza detaliile comenzii, reducerile si TVA.

## Extensibilitate
Pentru a adauga o noua oferta:
1. Creati o clasa care implementeaza `RegulaCalculTotal`.
2. Implementati metoda `aplica(List<OrderItem> items, double subtotal)`.
3. Setati regula pe comanda: `comanda.setRegulaZilei(new NouaRegula());`.

## Rulare
Necesita JDK instalat (Java >= 17 recomandat).

```bat
cd tema1_lab
javac -d out src\main\java\org\example\*.java
java -cp out org.example.Main
```

Daca folositi Maven, instalati-l si apoi:
```bat
mvn compile exec:java -Dexec.mainClass=org.example.Main
```

## Siguranta
- Validare cantitate > 0.
- Impedica adaugarea altor tipuri de produse decat `Mancare` si `Bautura`.

## Posibile imbunatatiri viitoare
- Persistenta (salvare comenzi) in baza de date.
- Suport pentru anulare / modificare produse in comanda.
- Teste unitare (JUnit) pentru reguli de discount.
- Localizare si format monetar adaptabil.

