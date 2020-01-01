package com.logistica.web.rest;

import com.logistica.domain.Transporteur;
import com.logistica.service.TransporteurService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.TransporteurCriteria;
import com.logistica.service.TransporteurQueryService;

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
 * REST controller for managing {@link com.logistica.domain.Transporteur}.
 */
@RestController
@RequestMapping("/api")
public class TransporteurResource {

    private final Logger log = LoggerFactory.getLogger(TransporteurResource.class);

    private static final String ENTITY_NAME = "transporteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransporteurService transporteurService;

    private final TransporteurQueryService transporteurQueryService;

    public TransporteurResource(TransporteurService transporteurService, TransporteurQueryService transporteurQueryService) {
        this.transporteurService = transporteurService;
        this.transporteurQueryService = transporteurQueryService;
    }

    /**
     * {@code POST  /transporteurs} : Create a new transporteur.
     *
     * @param transporteur the transporteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transporteur, or with status {@code 400 (Bad Request)} if the transporteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transporteurs")
    public ResponseEntity<Transporteur> createTransporteur(@Valid @RequestBody Transporteur transporteur) throws URISyntaxException {
        log.debug("REST request to save Transporteur : {}", transporteur);
        if (transporteur.getId() != null) {
            throw new BadRequestAlertException("A new transporteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transporteur result = transporteurService.save(transporteur);
        return ResponseEntity.created(new URI("/api/transporteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transporteurs} : Updates an existing transporteur.
     *
     * @param transporteur the transporteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transporteur,
     * or with status {@code 400 (Bad Request)} if the transporteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transporteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transporteurs")
    public ResponseEntity<Transporteur> updateTransporteur(@Valid @RequestBody Transporteur transporteur) throws URISyntaxException {
        log.debug("REST request to update Transporteur : {}", transporteur);
        if (transporteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transporteur result = transporteurService.save(transporteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transporteur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transporteurs} : get all the transporteurs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transporteurs in body.
     */
    @GetMapping("/transporteurs")
    public ResponseEntity<List<Transporteur>> getAllTransporteurs(TransporteurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Transporteurs by criteria: {}", criteria);
        Page<Transporteur> page = transporteurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /transporteurs/count} : count all the transporteurs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/transporteurs/count")
    public ResponseEntity<Long> countTransporteurs(TransporteurCriteria criteria) {
        log.debug("REST request to count Transporteurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(transporteurQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transporteurs/:id} : get the "id" transporteur.
     *
     * @param id the id of the transporteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transporteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transporteurs/{id}")
    public ResponseEntity<Transporteur> getTransporteur(@PathVariable Long id) {
        log.debug("REST request to get Transporteur : {}", id);
        Optional<Transporteur> transporteur = transporteurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transporteur);
    }

    /**
     * {@code DELETE  /transporteurs/:id} : delete the "id" transporteur.
     *
     * @param id the id of the transporteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transporteurs/{id}")
    public ResponseEntity<Void> deleteTransporteur(@PathVariable Long id) {
        log.debug("REST request to delete Transporteur : {}", id);
        transporteurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
