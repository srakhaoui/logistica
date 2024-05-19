package com.logistica.repository;

import com.logistica.domain.Facture;
import com.logistica.domain.enumeration.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture> {

    @Query("Select distinct f From Livraison l inner join Facture f on l.factureId = f.id " +
        "Where l.societeFacturation.id = :societeId And l.client.id = :clientId And l.dateBonLivraison >= :dateDebutLivraison And l.dateBonLivraison <= :dateFinLivraison " +
        "And (:chantier is null Or l.chantier = :chantier)")
    Page<Facture> findFactures(@Param("societeId") Long societeId, @Param("clientId") Long clientId, @Param("dateDebutLivraison") LocalDate dateDebutLivraison, @Param("dateFinLivraison") LocalDate dateFinLivraison, @Param("chantier") String chantier, Pageable pageable);

    @Query("Update Facture f set f.status = :status where f.id=:factureId")
    void updateStatusFacture(Long factureId, InvoiceStatus status);
}
