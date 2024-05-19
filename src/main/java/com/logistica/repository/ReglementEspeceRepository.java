package com.logistica.repository;

import com.logistica.domain.ReglementEspece;
import com.logistica.service.dto.FacturationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglementEspeceRepository extends JpaRepository<ReglementEspece, Long> {
    @Query("Select r From Livraison l inner join ReglementEspece r on l.reglementEspeceId = r.id " +
        "Where l.societeFacturation.id = :societeId And l.client.id = :clientId And l.dateBonLivraison >= :dateDebutLivraison And l.dateBonLivraison <= :dateFinLivraison " +
        "And (:chantier is null Or l.chantier = :chantier)")
    Page<ReglementEspece> findReglementsEspece(FacturationRequest facturationRequest, Pageable pageable);
}
