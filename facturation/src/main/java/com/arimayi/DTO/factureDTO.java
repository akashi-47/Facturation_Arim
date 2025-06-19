package com.arimayi.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor
public class factureDTO {
    
    private String numero;
    
    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;
    
 
    
    @NotNull(message = "La date de facture est obligatoire")
    private LocalDate dateFacture;
    
    @NotNull()
    @Valid
    @Size(min = 1, message = "Une facture doit contenir au moins une ligne")
    private List<ligneFactureDTO> lignesFacture;
    
    // calculer les totaux
    private BigDecimal totalHT;
    private BigDecimal totalTVA;
    private BigDecimal totalTTC;
     private void calculTotals() {
        if (lignesFacture != null && !lignesFacture.isEmpty()) {
            this.totalHT = lignesFacture.stream()
                .map(ligneFactureDTO::getTotalLigneHT)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            this.totalTVA = lignesFacture.stream()
                .map(ligneFactureDTO::getTotalLigneTVA)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            this.totalTTC = totalHT.add(totalTVA);
        }
    }
     public factureDTO(Long id, String numero, Long clientId, String clientNom, 
                     String clientEmail, String clientSiret, LocalDate dateFacture, 
                     List<ligneFactureDTO> lignesFacture) {
      
        this.numero = numero;
        this.clientId = clientId;
      
        this.dateFacture = dateFacture;
        this.lignesFacture = lignesFacture;
        this.calculTotals(); //calculer les totaux lors de la creation de la facture
    }
    
       public void setLignesFacture(List<ligneFactureDTO> lignesFacture) { 
        this.lignesFacture = lignesFacture;
        calculTotals(); // recalculer les totaux au moment de la modification des lignes
    }
    
}
