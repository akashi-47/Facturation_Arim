package com.arimayi.Services;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import com.arimayi.DTO.factureDTO;
import com.arimayi.DTO.createLigneFactureDTO;
import com.arimayi.DTO.ligneFactureDTO;
import com.arimayi.DTO.exportedFactureDTO;
import com.arimayi.DTO.exportedLigneFactureDTO;
import com.arimayi.DTO.createClientDTO;
import com.arimayi.Entities.Facture;
import com.arimayi.Entities.LigneFacture;
import com.arimayi.Repositories.FactureRepository;
import com.arimayi.Repositories.LigneFactureRepository;
import com.arimayi.Repositories.ClientRepository;
import com.arimayi.exceptions.QuantityNotZeroException;
import com.arimayi.exceptions.PrimaryValueException;
import com.arimayi.exceptions.NotFoundException;

@Service
public class FactureService {
    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private LigneFactureRepository ligneFactureRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Facture> findByClientId(Long clientId) {
        return factureRepository.findByClientIdWithLignes(clientId);
    }

    public List<Facture> findAll() {
        return factureRepository.findAll();
    }

    public Facture createFacture(factureDTO dto) {
        // Check if client exists before mapping and saving
        var client = clientRepository.findById(dto.getClientId())
            .orElseThrow(() -> new NotFoundException("Client non trouvé avec l'id: " + dto.getClientId()));

        Facture facture = mapToEntity(dto);
        facture.setClient(client); // Set the client relation

        try {
            return factureRepository.save(facture);
        } catch (DataIntegrityViolationException ex) {
            throw new PrimaryValueException("Le numéro de facture doit être unique.");
        }
    }

    private Facture mapToEntity(factureDTO dto) {
        Facture facture = modelMapper.map(dto, Facture.class);
        facture.setId(null);
        List<LigneFacture> lignes = dto.getLignesFacture().stream().map(ligneDto -> {
            LigneFacture ligne = modelMapper.map(ligneDto, LigneFacture.class);
            ligne.setFacture(facture);
            ligne.setTotalLigneHT(ligneDto.getTotalLigneHT());
            ligne.setTotalLigneTVA(ligneDto.getTotalLigneTVA());
           
            return ligne;
        }).collect(Collectors.toList());

        facture.setLignesFacture(lignes);
        return facture;
    }

    public ResponseEntity<LigneFacture> addLigneFactureToFacture(Long factureId, createLigneFactureDTO dto) {
        return factureRepository.findById(factureId)
            .map(facture -> {

                ligneFactureDTO ligneDto = modelMapper.map(dto, ligneFactureDTO.class);
                LigneFacture ligne = modelMapper.map(ligneDto, LigneFacture.class);
                ligne.setFacture(facture);
                ligne.setTotalLigneHT(ligneDto.getTotalLigneHT());
                ligne.setTotalLigneTVA(ligneDto.getTotalLigneTVA());
                LigneFacture saved = ligneFactureRepository.save(ligne);
                return ResponseEntity.status(HttpStatus.CREATED).body(saved);
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public factureDTO mapToDto(Facture facture) {
        factureDTO dto = modelMapper.map(facture, factureDTO.class);
        if (facture.getClient() != null) {
            dto.setClientId(facture.getClient().getId());
         
        }
        
        return dto;
    }

    public List<LigneFacture> findLignesByFactureId(Long factureId) {
        return ligneFactureRepository.findByFactureId(factureId);
    }

    public ligneFactureDTO mapLigneToDto(LigneFacture ligne) {
        return modelMapper.map(ligne, ligneFactureDTO.class);
    }
    public LigneFacture addLigneFactureToFacture(Long factureId, ligneFactureDTO dto) {
        if (dto.getQuantite() == null || dto.getQuantite() <= 0) {
            throw new QuantityNotZeroException("La quantite doit être strictement superieure à zero.");
        }
        return factureRepository.findById(factureId)
            .map(facture -> {
                LigneFacture ligne = modelMapper.map(dto, LigneFacture.class);
                ligne.setId(null); 
                ligne.setFacture(facture);
                return ligneFactureRepository.save(ligne);
            })
            .orElse(null);
}
    public factureDTO getFactureComplet(Long factureId) {
        Facture facture = factureRepository.findFactureWithClientAndLignes(factureId);
    
        return mapToDto(facture); 
    }

    public exportedFactureDTO exportFacture(Long factureId) {
        Facture facture = factureRepository.findFactureWithClientAndLignes(factureId);
        
        // Map client info
        createClientDTO clientInfo = new createClientDTO(
            facture.getClient().getNom(),
            facture.getClient().getEmail(),
            facture.getClient().getSiret()
        );

        // le mapping, il y'avait des problemes avec modelMapper
        List<exportedLigneFactureDTO> lignes = facture.getLignesFacture().stream()
            .map(ligne -> new exportedLigneFactureDTO(
                ligne.getDescription(),
                ligne.getQuantite(),
                ligne.getPrixUnitaireHT(),
                ligne.getTauxTVA().toString(),
                ligne.getTotalLigneHT(),
                ligne.getTotalLigneTVA()
             
            ))
            .collect(java.util.stream.Collectors.toList());

     
        return new exportedFactureDTO(
            facture.getNumero(),
            facture.getDateFacture(),
            facture.getTotalHT(),
            facture.getTotalTVA(),
            facture.getTotalTTC(),
            clientInfo,
            lignes
        );
    }
}


