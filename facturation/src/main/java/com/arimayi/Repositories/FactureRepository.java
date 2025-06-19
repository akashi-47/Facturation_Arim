package com.arimayi.Repositories;

import com.arimayi.Entities.Client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arimayi.Entities.Facture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface FactureRepository extends JpaRepository<Facture, Long> {

    // Methode pour trouver une facture par son numéro
    Optional<Facture> findByNumero(String numero);

    // Methode pour trouver toutes les factures d'un client
    List<Facture> findByClient(Client client);

    // Methode pour trouver toutes les factures avec un statut specifique
    // Recupere toutes les factures d'un client avec leurs lignes
    @Query("SELECT f FROM Facture f LEFT JOIN FETCH f.lignesFacture WHERE f.client.id = :clientId")
    List<Facture> findByClientIdWithLignes(@Param("clientId") Long clientId);

    @Query("SELECT f FROM Facture f " +
           "LEFT JOIN FETCH f.lignesFacture " +
           "LEFT JOIN FETCH f.client " +
           "WHERE f.id = :factureId")
    Facture findFactureWithClientAndLignes(@Param("factureId") Long factureId);
}
