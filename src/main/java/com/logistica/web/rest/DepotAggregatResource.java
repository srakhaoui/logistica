package com.logistica.web.rest;

import com.logistica.domain.DepotAggregat;
import com.logistica.repository.DepotAggregatRepository;
import com.logistica.service.DepotAgregatService;
import com.logistica.service.dto.RecapitulatifDepotAggregatStock;
import com.logistica.service.dto.RecapitulatifDepotAggregatStockRequest;
import com.logistica.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.logistica.domain.DepotAggregat}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DepotAggregatResource {

    private final Logger log = LoggerFactory.getLogger(DepotAggregatResource.class);

    private static final String ENTITY_NAME = "depotAggregat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepotAggregatRepository depotAggregatRepository;

    private final DepotAgregatService depotAgregatService;

    public DepotAggregatResource(DepotAgregatService depotAgregatService, DepotAggregatRepository depotAggregatRepository) {
        this.depotAgregatService = depotAgregatService;
        this.depotAggregatRepository = depotAggregatRepository;
    }

    /**
     * {@code POST  /depot-aggregats} : Create a new depotAggregat.
     *
     * @param depotAggregat the depotAggregat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depotAggregat, or with status {@code 400 (Bad Request)} if the depotAggregat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depot-aggregats")
    public ResponseEntity<DepotAggregat> createDepotAggregat(@Valid @RequestBody DepotAggregat depotAggregat) throws URISyntaxException {
        log.debug("REST request to save DepotAggregat : {}", depotAggregat);
        if (depotAggregat.getId() != null) {
            throw new BadRequestAlertException("A new depotAggregat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepotAggregat result = depotAggregatRepository.save(depotAggregat);
        return ResponseEntity.created(new URI("/api/depot-aggregats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depot-aggregats} : Updates an existing depotAggregat.
     *
     * @param depotAggregat the depotAggregat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depotAggregat,
     * or with status {@code 400 (Bad Request)} if the depotAggregat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depotAggregat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depot-aggregats")
    public ResponseEntity<DepotAggregat> updateDepotAggregat(@Valid @RequestBody DepotAggregat depotAggregat) throws URISyntaxException {
        log.debug("REST request to update DepotAggregat : {}", depotAggregat);
        if (depotAggregat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepotAggregat result = depotAggregatRepository.save(depotAggregat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depotAggregat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /depot-aggregats} : get all the depotAggregats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depotAggregats in body.
     */
    @GetMapping("/depot-aggregats")
    public List<DepotAggregat> getAllDepotAggregats() {
        log.debug("REST request to get all DepotAggregats");
        return depotAggregatRepository.findAll();
    }

    /**
     * {@code GET  /depot-aggregats/:id} : get the "id" depotAggregat.
     *
     * @param id the id of the depotAggregat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depotAggregat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depot-aggregats/{id}")
    public ResponseEntity<DepotAggregat> getDepotAggregat(@PathVariable Long id) {
        log.debug("REST request to get DepotAggregat : {}", id);
        Optional<DepotAggregat> depotAggregat = depotAggregatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(depotAggregat);
    }

    /**
     * {@code DELETE  /depot-aggregats/:id} : delete the "id" depotAggregat.
     *
     * @param id the id of the depotAggregat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depot-aggregats/{id}")
    public ResponseEntity<Void> deleteDepotAggregat(@PathVariable Long id) {
        log.debug("REST request to delete DepotAggregat : {}", id);
        depotAggregatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /depot-aggregats} : get all the depotAggregats' stocks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stocks in body.
     */
    @GetMapping("/depot-aggregats/stocks")
    public List<RecapitulatifDepotAggregatStock> getAllDepotAggregatStocks(RecapitulatifDepotAggregatStockRequest recapitulatifDepotAggregatStockRequest) {
        log.debug("REST request to get all DepotAggregatStocks");
        return depotAgregatService.getStocks(recapitulatifDepotAggregatStockRequest);
    }
}
