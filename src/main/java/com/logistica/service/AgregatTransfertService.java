package com.logistica.service;

import com.logistica.domain.AgregatTransfert;
import com.logistica.repository.AgregatTransfertRepository;
import com.logistica.service.dto.StockDepot;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AgregatTransfert}.
 */
@Service
@Transactional
public class AgregatTransfertService {

    private final Logger log = LoggerFactory.getLogger(AgregatTransfertService.class);

    private final AgregatTransfertRepository agregatTransfertRepository;

    @Autowired
    private DepotAgregatService depotAgregatService;

    public AgregatTransfertService(AgregatTransfertRepository agregatTransfertRepository) {
        this.agregatTransfertRepository = agregatTransfertRepository;
    }

    /**
     * Save a agregatTransfert.
     *
     * @param agregatTransfert the entity to save.
     * @return the persisted entity.
     */
    public AgregatTransfert save(AgregatTransfert agregatTransfert) {
        log.debug("Request to save AgregatTransfert : {}", agregatTransfert);
        Assert.isTrue(agregatTransfert.getQuantite() > 0.0, "La quantité transferée doit être supérieure à 0");

        if (!quantiteDepotSourceSuffisante(agregatTransfert)) {
            throw new QuantiteTransfertInsuffisanteException();
        }
        return agregatTransfertRepository.save(agregatTransfert);
    }

    private Boolean quantiteDepotSourceSuffisante(AgregatTransfert agregatTransfert) {
        return depotAgregatService
            .getStock(agregatTransfert.getSource(), agregatTransfert.getUnite())
            .map(sourceStock -> agregatTransfert.getQuantite() < sourceStock)
            .orElseThrow(() -> new QuantiteTransfertInsuffisanteException());
    }

    /**
     * Get all the agregatTransferts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgregatTransfert> findAll(Pageable pageable) {
        log.debug("Request to get all AgregatTransferts");
        return agregatTransfertRepository.findAll(pageable);
    }


    /**
     * Get one agregatTransfert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgregatTransfert> findOne(Long id) {
        log.debug("Request to get AgregatTransfert : {}", id);
        return agregatTransfertRepository.findById(id);
    }

    /**
     * Delete the agregatTransfert by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgregatTransfert : {}", id);
        agregatTransfertRepository.deleteById(id);
    }

    public List<StockDepot> getTotalTransfertEntrantsByDepotAndUnite() {
        return agregatTransfertRepository.getTotalTransfertEntrantsByDepotAndUnite();
    }

    public List<StockDepot> getTotalTransfertSortantsByDepotAndUnite() {
        return agregatTransfertRepository.getTotalTransfertSortantsByDepotAndUnite();
    }
}
