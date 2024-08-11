package com.logistica.repository;

import com.logistica.domain.Facture;
import com.logistica.domain.enumeration.TypeLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FactureRepositoryCustom {

    Page<Facture> getFactures(Long societeId, Boolean facture, Long clientId, LocalDate dateDebutLivraison, LocalDate  dateFinLivraison, String chantier, TypeLivraison typeLivraison, Boolean regleEnEspece, Long produitId, Pageable pageable);
}
