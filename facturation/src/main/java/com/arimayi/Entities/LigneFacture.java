package com.arimayi.Entities;

import java.math.BigDecimal;

import com.arimayi.Enums.TauxTVA;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lignes_facture")
public class LigneFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private Integer quantite;

    @NotNull
  
    private BigDecimal prixUnitaireHT;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TauxTVA tauxTVA;

    @NotNull
    private BigDecimal totalLigneHT;

    @NotNull
    private BigDecimal totalLigneTVA;
    @ManyToOne(optional = false)
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture;
    
}
