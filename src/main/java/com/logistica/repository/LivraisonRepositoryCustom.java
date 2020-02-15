package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.logistica.service.dto.RecapitulatifAchat;
import org.springframework.data.jpa.repository.Query;

public interface LivraisonRepositoryCustom {

	Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable);

    Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable);

    Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable);

    Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable);

    Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(Pageable pageable);
}
