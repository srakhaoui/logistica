package com.logistica.service.impl;

import com.logistica.domain.Gasoil;
import com.logistica.repository.GasoilRepository;
import com.logistica.service.GasoilService;
import com.logistica.service.KilometrageInvalideException;
import com.logistica.service.LivraisonService;
import com.logistica.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Gasoil}.
 */
@Service
@Transactional
public class GasoilServiceImpl implements GasoilService {

    private final Logger log = LoggerFactory.getLogger(GasoilServiceImpl.class);

    private final GasoilRepository gasoilRepository;

    @Autowired
    private LivraisonService livraisonService;

    public GasoilServiceImpl(GasoilRepository gasoilRepository) {
        this.gasoilRepository = gasoilRepository;
    }

    /**
     * Save a gasoil.
     *
     * @param gasoil the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Gasoil save(Gasoil gasoil) {
        log.debug("Request to save Gasoil : {}", gasoil);
        if (gasoil.getKilometrageFinal().compareTo(gasoil.getKilometrageInitial()) <= 0) {
            throw new KilometrageInvalideException();
        }
        gasoil.setPrixTotalGasoil(gasoil.getPrixDuLitre() * gasoil.getQuantiteEnLitre());
        gasoil.setKilometrageParcouru(gasoil.getKilometrageFinal() - gasoil.getKilometrageInitial());
        return gasoilRepository.save(gasoil);
    }

    /**
     * Get all the gasoils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Gasoil> findAll(Pageable pageable) {
        log.debug("Request to get all Gasoils");
        return gasoilRepository.findAll(pageable);
    }


    /**
     * Get one gasoil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Gasoil> findOne(Long id) {
        log.debug("Request to get Gasoil : {}", id);
        return gasoilRepository.findById(id);
    }

    /**
     * Delete the gasoil by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gasoil : {}", id);
        gasoilRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecapitulatifChargeGasoil> getRecapitulatifChargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable) {
        Assert.notNull(recapitulatifChargeGasoilRequest.getDateDebut(), "Date de début non renseignée");
        Assert.notNull(recapitulatifChargeGasoilRequest.getDateFin(), "Date de fin non renseignée");
        Page<RecapitulatifChargeGasoil> recapitulatifChargeGasoilPage = gasoilRepository.getRecapitulatifChargeGasoil(recapitulatifChargeGasoilRequest, pageable);
        calculerMargeGasoil(recapitulatifChargeGasoilRequest, recapitulatifChargeGasoilPage);
        return recapitulatifChargeGasoilPage;
    }

    private void calculerMargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Page<RecapitulatifChargeGasoil> recapitulatifChargeGasoilPage) {
        recapitulatifChargeGasoilPage.getContent().stream().forEach(recapitulatifChargeGasoil -> {
            final Double totalPrixVente = Optional.ofNullable(livraisonService.getTotalPrixVenteBySocieteFacturation(recapitulatifChargeGasoil.getSocieteId(), recapitulatifChargeGasoilRequest.getDateDebut(), recapitulatifChargeGasoilRequest.getDateFin())).orElse(0.0);
            double margeGasoil = (totalPrixVente - recapitulatifChargeGasoil.getTotalPrixGasoil()) / recapitulatifChargeGasoil.getTotalPrixGasoil();
            margeGasoil = round(margeGasoil);
            recapitulatifChargeGasoil.setMargeGasoil(margeGasoil);
            Double totalCommissionChauffeur = Optional.ofNullable(livraisonService.getTotalCommissionByChauffeur(recapitulatifChargeGasoil.getTransporteurId(), recapitulatifChargeGasoilRequest.getDateDebut(), recapitulatifChargeGasoilRequest.getDateFin())).orElse(0.0);
            totalCommissionChauffeur = round(totalCommissionChauffeur);
            recapitulatifChargeGasoil.setTotalCommissionChauffeur(totalCommissionChauffeur);
            Double totalVenteByTransporteur = Optional.ofNullable(livraisonService.getTotalVenteByTransporteur(recapitulatifChargeGasoil.getTransporteurId(), recapitulatifChargeGasoilRequest.getDateDebut(), recapitulatifChargeGasoilRequest.getDateFin())).orElse(0.0);
            recapitulatifChargeGasoil.setTotalVenteTransporteur(totalVenteByTransporteur);
        });
    }

    private double round(Double montant) {
        return BigDecimal.valueOf(montant).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getKilometrageFinal(String matricule) {
        Assert.notNull(matricule, "gasoilRepository.getKilometrageFinal: Le matricule est obligatoire");
        Integer kilometrageFinal = gasoilRepository.getkilometrageFinalByMatricule(matricule);
        return Optional.ofNullable(kilometrageFinal).orElse(0);
    }

    @Override
    @Transactional(readOnly = true)
    public GasoilPriceResponse getLastPrixGasoil() {
        final Float latestGasoilPrice = gasoilRepository.getLastPrixGasoil();
        return new GasoilPriceResponse(latestGasoilPrice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargeGasoilParMois> getEvolutionChargeGasoilParMois(ChargeGasoilRequest chargeGasoilRequest) {
        return gasoilRepository.getEvolutionChargeGasoilParMois(chargeGasoilRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargeGasoilParMatricule> getRepartitionChargeGasoilParMatricule(ChargeGasoilRequest chargeGasoilRequest) {
        return gasoilRepository.getRepartitionChargeGasoilParMatricule(chargeGasoilRequest);
    }
}
