package com.arimayi.Controllers;

import com.arimayi.DTO.factureDTO;
import com.arimayi.DTO.exportedFactureDTO;
import com.arimayi.DTO.ligneFactureDTO;
import com.arimayi.Entities.Facture;
import com.arimayi.Entities.LigneFacture;
import com.arimayi.Services.FactureService;
import com.arimayi.exceptions.QuantityNotZeroException;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("arimayi/facture")
@Tag(name = "Factures", description = "Opérations liees aux factures")
public class factureController {

    @Autowired
    private FactureService factureService;

    @GetMapping("/all")
    @Operation(
        summary = "Liste toutes les factures",
        description = "Retourne la liste complète des factures"
    )
  
    public ResponseEntity<List<factureDTO>> getListFactures() {
        List<Facture> factures = factureService.findAll();
        List<factureDTO> factureDTOs = factures.stream()
            .map(factureService::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(factureDTOs);
    }

    @PostMapping("/add")
    @Operation(
        summary = "Ajouter une nouvelle facture"
     
    )

    public Facture addFacture(@Valid @RequestBody factureDTO dto) {
        return factureService.createFacture(dto);
    }

    @GetMapping("/{clientId}/all")
    public ResponseEntity<List<factureDTO>> getClientFactures(@PathVariable Long clientId) {
        List<Facture> factures = factureService.findByClientId(clientId);
        List<factureDTO> factureDTOs = factures.stream()
            .map(factureService::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(factureDTOs);
    }

  
    @GetMapping("/{factureId}/lignes")
    @Operation(
        summary = "Liste toutes les lignes d'une facture",
        description = "Retourne la liste des lignes pour une facture donnée"
    )
 
    public ResponseEntity<List<ligneFactureDTO>> getLignesByFactureId(@PathVariable Long factureId) {
        List<LigneFacture> lignes = factureService.findLignesByFactureId(factureId);
        List<ligneFactureDTO> ligneDTOs = lignes.stream()
            .map(factureService::mapLigneToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ligneDTOs);
    }
      @PostMapping("/{factureId}/lignes/add")
    @Operation(
        summary = "Ajouter une ligne à une facture",
        description = "Ajoute une ligne de facture à une facture existante et retourne la ligne ajoutée en DTO"
    )
 
    public ResponseEntity<ligneFactureDTO> addLigneFactureToFacture(
            @PathVariable Long factureId,
            @Valid @RequestBody ligneFactureDTO dto) {
        LigneFacture savedLigne = factureService.addLigneFactureToFacture(factureId, dto);
        if (savedLigne == null) {
            return ResponseEntity.notFound().build();
        }
        ligneFactureDTO resultDto = factureService.mapLigneToDto(savedLigne);
        return ResponseEntity.status(201).body(resultDto);
    }

  

    @GetMapping("/{factureId}")
    @Operation(
        summary = "Exporter une facture complète",
        description = "Retourne la facture complète structurée pour l'export"
    )
    public ResponseEntity<exportedFactureDTO> exportFacture(@PathVariable Long factureId) {
        exportedFactureDTO export = factureService.exportFacture(factureId);
        return ResponseEntity.ok(export);
    }

    @ExceptionHandler(QuantityNotZeroException.class)
    public ResponseEntity<String> handleQuantityNotZeroException(QuantityNotZeroException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
