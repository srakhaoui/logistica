package com.logistica.web.rest;

import com.logistica.domain.Trajet;
import com.logistica.service.TrajetService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.TrajetCriteria;
import com.logistica.service.TrajetQueryService;

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
 * REST controller for managing {@link com.logistica.domain.Trajet}.
 */
@RestController
@RequestMapping("/api")
public class TrajetResource {

    private final Logger log = LoggerFactory.getLogger(TrajetResource.class);

    private static final String ENTITY_NAME = "trajet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrajetService trajetService;

    private final TrajetQueryService trajetQueryService;

    public TrajetResource(TrajetService trajetService, TrajetQueryService trajetQueryService) {
        this.trajetService = trajetService;
        this.trajetQueryService = trajetQueryService;
    }

    /**
     * {@code POST  /trajets} : Create a new trajet.
     *
     * @param trajet the trajet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trajet, or with status {@code 400 (Bad Request)} if the trajet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trajets")
    public ResponseEntity<Trajet> createTrajet(@Valid @RequestBody Trajet trajet) throws URISyntaxException {
        log.debug("REST request to save Trajet : {}", trajet);
        if (trajet.getId() != null) {
            throw new BadRequestAlertException("A new trajet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trajet result = trajetService.save(trajet);
        return ResponseEntity.created(new URI("/api/trajets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trajets} : Updates an existing trajet.
     *
     * @param trajet the trajet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trajet,
     * or with status {@code 400 (Bad Request)} if the trajet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trajet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trajets")
    public ResponseEntity<Trajet> updateTrajet(@Valid @RequestBody Trajet trajet) throws URISyntaxException {
        log.debug("REST request to update Trajet : {}", trajet);
        if (trajet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trajet result = trajetService.save(trajet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trajet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trajets} : get all the trajets.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trajets in body.
     */
    @GetMapping("/trajets")
    public ResponseEntity<List<Trajet>> getAllTrajets(TrajetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Trajets by criteria: {}", criteria);
        Page<Trajet> page = trajetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /trajets/count} : count all the trajets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/trajets/count")
    public ResponseEntity<Long> countTrajets(TrajetCriteria criteria) {
        log.debug("REST request to count Trajets by criteria: {}", criteria);
        return ResponseEntity.ok().body(trajetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trajets/:id} : get the "id" trajet.
     *
     * @param id the id of the trajet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trajet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trajets/{id}")
    public ResponseEntity<Trajet> getTrajet(@PathVariable Long id) {
        log.debug("REST request to get Trajet : {}", id);
        Optional<Trajet> trajet = trajetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trajet);
    }

    /**
     * {@code DELETE  /trajets/:id} : delete the "id" trajet.
     *
     * @param id the id of the trajet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trajets/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        log.debug("REST request to delete Trajet : {}", id);
        trajetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
