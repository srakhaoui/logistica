package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LivraisonRepositoryCustom {

    Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable);

    Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable);

    Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable);

    Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable);

    Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable);

    Page<RecapitulatifEfficaciteChauffeur> getRecapitulatifEfficaciteChauffeur(RecapitulatifEfficaciteChauffeurRequest recapitulatifEfficaciteChauffeurRequest, Pageable pageable);

    List<ChiffreAffaireParMois> getEvolutionChiffreAffaireParMois(StatistiquesChiffreAffaireRequest evolutionCARequest);

    List<ChiffreAffaireParRepartition> getRepartitionChiffreAffairePar(StatistiquesChiffreAffaireRequest evolutionCARequest, UniteRepartition uniteRepartition);

    Double getTotalChiffreAffaire(StatistiquesChiffreAffaireRequest evolutionCARequest);

    List<StockDepot> getTotalAchatMarchandisesByDepotAndUnite(RecapitulatifDepotAggregatStockRequest recapitulatifDepotAggregatStockRequest);

    List<StockDepot> getTotalVenteMarchandisesByDepotAndUnite(RecapitulatifDepotAggregatStockRequest recapitulatifDepotAggregatStockRequest);

}
