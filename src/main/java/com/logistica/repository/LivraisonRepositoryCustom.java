package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivraisonRepositoryCustom {

    Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable);

    Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable);

    Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable);

    Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable);

    Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable);

    Page<RecapitulatifEfficaciteChauffeur> getRecapitulatifEfficaciteChauffeur(RecapitulatifEfficaciteChauffeurRequest recapitulatifEfficaciteChauffeurRequest, Pageable pageable);
}
