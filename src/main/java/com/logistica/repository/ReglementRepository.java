package com.logistica.repository;

import com.logistica.domain.Reglement;
import com.logistica.service.dto.FacturationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglementRepository extends JpaRepository<Reglement, Long> {

    @Query("Select r From Livraison l inner join Facture f on l.factureId = f.id inner join Reglement r on f.id = r.factureId " +
        "Where l.societeFacturation.id = :societeId And l.client.id = :clientId And l.dateBonLivraison >= :dateDebutLivraison And l.dateBonLivraison <= :dateFinLivraison " +
        "And (:chantier is null Or l.chantier = :chantier)")
    Page<Reglement> findReglements(FacturationRequest facturationRequest, Pageable pageable);
}
