package com.logistica.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.logistica.domain.RecapitulatifAchat;

public interface LivraisonRepositoryCustom {

	Page<RecapitulatifAchat> getRecapitulatifByFournisseur(Long societeId, Long fournisseurId, Pageable pageable);
}
