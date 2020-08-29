package com.logistica.service;

import com.logistica.domain.Bon;
import com.logistica.domain.Livraison;
import com.logistica.domain.enumeration.TypeBon;
import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Livraison}.
 */
public interface LivraisonService {
    /**
     * Save a livraison.
     *
     * @param livraison the entity to save.
     * @return the persisted entity.
     */
    Livraison save(Livraison livraison);

    /**
     * Get all the livraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Livraison> findAll(Pageable pageable);


    /**
     * Get the "id" livraison.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Livraison> findOne(Long id);

    /**
     * Delete the "id" livraison.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable);

    Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable);

    Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable);

    Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable);

    Page<IRecapitulatifChauffeur> getRecapitulatifChauffeur(RecapitulatifChauffeurRequest recapitulatifChauffeurRequest, Pageable pageable);

    Page<RecapitulatifEfficaciteChauffeur> getRecapitulatifEfficaciteChauffeur(RecapitulatifEfficaciteChauffeurRequest recapitulatifEfficaciteChauffeurRequest, Pageable pageable);

    Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable);

    Bon getBon(Long id, TypeBon typeBon);

    Double getTotalPrixVenteBySocieteFacturation(Long societeId, LocalDate dateDebut, LocalDate dateFin);

    Double getTotalCommissionByChauffeur(Long transporteurId, LocalDate dateDebut, LocalDate dateFin);

    List<String> getChantiersByClient(ChantiersByClientRequest chantiersByClientRequest);
}
