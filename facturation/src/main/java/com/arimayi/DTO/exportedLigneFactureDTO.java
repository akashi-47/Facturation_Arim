package com.arimayi.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class exportedLigneFactureDTO {
     private String description;
        private Integer quantite;
        private BigDecimal prixUnitaireHT;
        private String tauxTVA;
        private BigDecimal totalHT;
        private BigDecimal totalTVA;
       
    
}
