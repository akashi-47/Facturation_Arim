package com.arimayi.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TauxTVA {
    TVA_0(BigDecimal.ZERO, "0%"),
    TVA_5_5(new BigDecimal("0.055"), "5.5%"),
    TVA_10(new BigDecimal("0.10"), "10%"),
    TVA_20(new BigDecimal("0.20"), "20%");

    private final BigDecimal taux;
    private final String libelle;

    public BigDecimal getTaux() {
        return taux;
    }

    public String getLibelle() {
        return libelle;
    }

    @Override
    @JsonValue 
    public String toString() {
        return libelle;
    }

    @JsonCreator 
    public static TauxTVA fromLibelle(String value) {
        for (TauxTVA t : TauxTVA.values()) {
            if (t.libelle.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Taux n'est pas valide: " + value);
    }
}
