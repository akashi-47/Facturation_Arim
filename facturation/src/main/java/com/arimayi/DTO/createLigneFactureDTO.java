package com.arimayi.DTO;

import java.math.BigDecimal;

import com.arimayi.Enums.TauxTVA;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class createLigneFactureDTO {

        @NotBlank(message = "La description est obligatoire")
    private String description;
    
    @NotNull(message = "La quantite est obligatoire")
    @Positive(message = "La quantite doit etre positive")
    private Integer quantite;
    
    @NotNull(message = "Le prix unitaire HT est obligatoire")
    private BigDecimal prixUnitaireHT;
    
    @NotNull(message = "Le taux de TVA est obligatoire")
    private TauxTVA tauxTVA;
    
}
