package com.logistica.web.rest;

import com.logistica.domain.TarifTransport;
import com.logistica.service.TarifTransportService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.TarifTransportCriteria;
import com.logistica.service.TarifTransportQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.logistica.domain.TarifTransport}.
 */
@RestController
@RequestMapping("/api")
public class TarifTransportResource {

    private final Logger log = LoggerFactory.getLogger(TarifTransportResource.class);

    private static final String ENTITY_NAME = "tarifTransport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifTransportService tarifTransportService;

    private final TarifTransportQueryService tarifTransportQueryService;

    public TarifTransportResource(TarifTransportService tarifTransportService, TarifTransportQueryService tarifTransportQueryService) {
        this.tarifTransportService = tarifTransportService;
        this.tarifTransportQueryService = tarifTransportQueryService;
    }

    /**
     * {@code POST  /tarif-transports} : Create a new tarifTransport.
     *
     * @param tarifTransport the tarifTransport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifTransport, or with status {@code 400 (Bad Request)} if the tarifTransport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-transports")
    public ResponseEntity<TarifTransport> createTarifTransport(@Valid @RequestBody TarifTransport tarifTransport) throws URISyntaxException {
        log.debug("REST request to save TarifTransport : {}", tarifTransport);
        if (tarifTransport.getId() != null) {
            throw new BadRequestAlertException("A new tarifTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifTransport result = tarifTransportService.save(tarifTransport);
        return ResponseEntity.created(new URI("/api/tarif-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-transports} : Updates an existing tarifTransport.
     *
     * @param tarifTransport the tarifTransport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifTransport,
     * or with status {@code 400 (Bad Request)} if the tarifTransport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifTransport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-transports")
    public ResponseEntity<TarifTransport> updateTarifTransport(@Valid @RequestBody TarifTransport tarifTransport) throws URISyntaxException {
        log.debug("REST request to update TarifTransport : {}", tarifTransport);
        if (tarifTransport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifTransport result = tarifTransportService.save(tarifTransport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifTransport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-transports} : get all the tarifTransports.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifTransports in body.
     */
    @GetMapping("/tarif-transports")
    public ResponseEntity<List<TarifTransport>> getAllTarifTransports(TarifTransportCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TarifTransports by criteria: {}", criteria);
        Page<TarifTransport> page = tarifTransportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tarif-transports/count} : count all the tarifTransports.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tarif-transports/count")
    public ResponseEntity<Long> countTarifTransports(TarifTransportCriteria criteria) {
        log.debug("REST request to count TarifTransports by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarifTransportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarif-transports/:id} : get the "id" tarifTransport.
     *
     * @param id the id of the tarifTransport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifTransport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-transports/{id}")
    public ResponseEntity<TarifTransport> getTarifTransport(@PathVariable Long id) {
        log.debug("REST request to get TarifTransport : {}", id);
        Optional<TarifTransport> tarifTransport = tarifTransportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarifTransport);
    }

    /**
     * {@code DELETE  /tarif-transports/:id} : delete the "id" tarifTransport.
     *
     * @param id the id of the tarifTransport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-transports/{id}")
    public ResponseEntity<Void> deleteTarifTransport(@PathVariable Long id) {
        log.debug("REST request to delete TarifTransport : {}", id);
        tarifTransportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
