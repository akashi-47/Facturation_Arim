package com.arimayi.DTO;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class createFactureDTO {
      @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;
    
    @NotNull(message = "La date de facture est obligatoire")
    private LocalDate dateFacture;
    
    @NotEmpty(message = "Une facture doit avoir au moins une ligne")
    @Valid
    private List<createLigneFactureDTO> lignesFacture;
    
}
