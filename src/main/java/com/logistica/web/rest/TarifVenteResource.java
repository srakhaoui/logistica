package com.logistica.web.rest;

import com.logistica.domain.TarifVente;
import com.logistica.service.TarifVenteService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.TarifVenteCriteria;
import com.logistica.service.TarifVenteQueryService;

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
 * REST controller for managing {@link com.logistica.domain.TarifVente}.
 */
@RestController
@RequestMapping("/api")
public class TarifVenteResource {

    private final Logger log = LoggerFactory.getLogger(TarifVenteResource.class);

    private static final String ENTITY_NAME = "tarifVente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifVenteService tarifVenteService;

    private final TarifVenteQueryService tarifVenteQueryService;

    public TarifVenteResource(TarifVenteService tarifVenteService, TarifVenteQueryService tarifVenteQueryService) {
        this.tarifVenteService = tarifVenteService;
        this.tarifVenteQueryService = tarifVenteQueryService;
    }

    /**
     * {@code POST  /tarif-ventes} : Create a new tarifVente.
     *
     * @param tarifVente the tarifVente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifVente, or with status {@code 400 (Bad Request)} if the tarifVente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-ventes")
    public ResponseEntity<TarifVente> createTarifVente(@Valid @RequestBody TarifVente tarifVente) throws URISyntaxException {
        log.debug("REST request to save TarifVente : {}", tarifVente);
        if (tarifVente.getId() != null) {
            throw new BadRequestAlertException("A new tarifVente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifVente result = tarifVenteService.save(tarifVente);
        return ResponseEntity.created(new URI("/api/tarif-ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-ventes} : Updates an existing tarifVente.
     *
     * @param tarifVente the tarifVente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifVente,
     * or with status {@code 400 (Bad Request)} if the tarifVente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifVente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-ventes")
    public ResponseEntity<TarifVente> updateTarifVente(@Valid @RequestBody TarifVente tarifVente) throws URISyntaxException {
        log.debug("REST request to update TarifVente : {}", tarifVente);
        if (tarifVente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifVente result = tarifVenteService.save(tarifVente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifVente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-ventes} : get all the tarifVentes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifVentes in body.
     */
    @GetMapping("/tarif-ventes")
    public ResponseEntity<List<TarifVente>> getAllTarifVentes(TarifVenteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TarifVentes by criteria: {}", criteria);
        Page<TarifVente> page = tarifVenteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tarif-ventes/count} : count all the tarifVentes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tarif-ventes/count")
    public ResponseEntity<Long> countTarifVentes(TarifVenteCriteria criteria) {
        log.debug("REST request to count TarifVentes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarifVenteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarif-ventes/:id} : get the "id" tarifVente.
     *
     * @param id the id of the tarifVente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifVente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-ventes/{id}")
    public ResponseEntity<TarifVente> getTarifVente(@PathVariable Long id) {
        log.debug("REST request to get TarifVente : {}", id);
        Optional<TarifVente> tarifVente = tarifVenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarifVente);
    }

    /**
     * {@code DELETE  /tarif-ventes/:id} : delete the "id" tarifVente.
     *
     * @param id the id of the tarifVente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-ventes/{id}")
    public ResponseEntity<Void> deleteTarifVente(@PathVariable Long id) {
        log.debug("REST request to delete TarifVente : {}", id);
        tarifVenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
