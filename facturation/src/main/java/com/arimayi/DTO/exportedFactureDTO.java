package com.arimayi.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
//pour l'exportation
public class exportedFactureDTO {
       private String numeroFacture;
    private LocalDate dateFacture;
       private BigDecimal totalHT;
        private BigDecimal totalTVA;
        private BigDecimal totalTTC;
    
    // Informations client (utiliser le createClientDTO pour les détails pas besoin de creer un nouveau DTO)
    private createClientDTO clientInfo;
    
    // Détails de la facture
    private List<exportedLigneFactureDTO> lignes;
    
}
