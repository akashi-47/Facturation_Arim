package com.arimayi.DTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

import com.arimayi.Enums.TauxTVA;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
public class ligneFactureDTO {
    
    @NotBlank(message = "La description est obligatoire")
    @Size(max = 255, message = "La description ne peut pas depasse 255 caractères")
    private String description;
    
    @NotNull(message = "La quantite est obligatoire")
    @Positive(message = "La quantite doit être positive")
    private Integer quantite;
    
    @NotNull(message = "Le prix unitaire HT est obligatoire")
    private BigDecimal prixUnitaireHT;
    
    @NotNull(message = "Le taux de TVA est obligatoire")
    private TauxTVA tauxTVA;
      private BigDecimal totalLigneHT;
    private BigDecimal totalLigneTVA;
    private BigDecimal totalLigneTTC;

       private void calculTotals() {
        if (quantite != null && prixUnitaireHT != null && tauxTVA != null) {
            this.totalLigneHT = prixUnitaireHT.multiply(BigDecimal.valueOf(quantite));
            this.totalLigneTVA = totalLigneHT.multiply(tauxTVA.getTaux());
            this.totalLigneTTC = totalLigneHT.add(totalLigneTVA);
        }
    }
     
    public ligneFactureDTO(Long id, String description, Integer quantite, 
                          BigDecimal prixUnitaireHT, TauxTVA tauxTVA) {
     
        this.description = description;
        this.quantite = quantite;
        this.prixUnitaireHT = prixUnitaireHT;
        this.tauxTVA = tauxTVA;
        this.calculTotals(); // Calculer les totaux lors de la création de l'objet
    }

    // il faut a chaque fois que l'on modifie un attribut recalculer les totaux
       public void setPrixUnitaireHT(BigDecimal prixUnitaireHT) { 
        this.prixUnitaireHT = prixUnitaireHT;
        calculTotals();
    }
       public void setTauxTVA(TauxTVA tauxTVA) { 
        this.tauxTVA = tauxTVA;
        calculTotals();
    }
       public void setQuantite(Integer quantite) { 
        this.quantite = quantite;
        calculTotals();
    }
    
}
