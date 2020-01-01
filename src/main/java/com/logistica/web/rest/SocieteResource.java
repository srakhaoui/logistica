package com.logistica.web.rest;

import com.logistica.domain.Societe;
import com.logistica.service.SocieteService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.SocieteCriteria;
import com.logistica.service.SocieteQueryService;

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
 * REST controller for managing {@link com.logistica.domain.Societe}.
 */
@RestController
@RequestMapping("/api")
public class SocieteResource {

    private final Logger log = LoggerFactory.getLogger(SocieteResource.class);

    private static final String ENTITY_NAME = "societe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocieteService societeService;

    private final SocieteQueryService societeQueryService;

    public SocieteResource(SocieteService societeService, SocieteQueryService societeQueryService) {
        this.societeService = societeService;
        this.societeQueryService = societeQueryService;
    }

    /**
     * {@code POST  /societes} : Create a new societe.
     *
     * @param societe the societe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societe, or with status {@code 400 (Bad Request)} if the societe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/societes")
    public ResponseEntity<Societe> createSociete(@Valid @RequestBody Societe societe) throws URISyntaxException {
        log.debug("REST request to save Societe : {}", societe);
        if (societe.getId() != null) {
            throw new BadRequestAlertException("A new societe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Societe result = societeService.save(societe);
        return ResponseEntity.created(new URI("/api/societes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /societes} : Updates an existing societe.
     *
     * @param societe the societe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societe,
     * or with status {@code 400 (Bad Request)} if the societe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/societes")
    public ResponseEntity<Societe> updateSociete(@Valid @RequestBody Societe societe) throws URISyntaxException {
        log.debug("REST request to update Societe : {}", societe);
        if (societe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Societe result = societeService.save(societe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /societes} : get all the societes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societes in body.
     */
    @GetMapping("/societes")
    public ResponseEntity<List<Societe>> getAllSocietes(SocieteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Societes by criteria: {}", criteria);
        Page<Societe> page = societeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /societes/count} : count all the societes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/societes/count")
    public ResponseEntity<Long> countSocietes(SocieteCriteria criteria) {
        log.debug("REST request to count Societes by criteria: {}", criteria);
        return ResponseEntity.ok().body(societeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /societes/:id} : get the "id" societe.
     *
     * @param id the id of the societe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/societes/{id}")
    public ResponseEntity<Societe> getSociete(@PathVariable Long id) {
        log.debug("REST request to get Societe : {}", id);
        Optional<Societe> societe = societeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societe);
    }

    /**
     * {@code DELETE  /societes/:id} : delete the "id" societe.
     *
     * @param id the id of the societe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/societes/{id}")
    public ResponseEntity<Void> deleteSociete(@PathVariable Long id) {
        log.debug("REST request to delete Societe : {}", id);
        societeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
