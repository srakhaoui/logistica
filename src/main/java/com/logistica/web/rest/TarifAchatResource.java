package com.logistica.web.rest;

import com.logistica.domain.TarifAchat;
import com.logistica.service.TarifAchatService;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.dto.TarifAchatCriteria;
import com.logistica.service.TarifAchatQueryService;

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
 * REST controller for managing {@link com.logistica.domain.TarifAchat}.
 */
@RestController
@RequestMapping("/api")
public class TarifAchatResource {

    private final Logger log = LoggerFactory.getLogger(TarifAchatResource.class);

    private static final String ENTITY_NAME = "tarifAchat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarifAchatService tarifAchatService;

    private final TarifAchatQueryService tarifAchatQueryService;

    public TarifAchatResource(TarifAchatService tarifAchatService, TarifAchatQueryService tarifAchatQueryService) {
        this.tarifAchatService = tarifAchatService;
        this.tarifAchatQueryService = tarifAchatQueryService;
    }

    /**
     * {@code POST  /tarif-achats} : Create a new tarifAchat.
     *
     * @param tarifAchat the tarifAchat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarifAchat, or with status {@code 400 (Bad Request)} if the tarifAchat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tarif-achats")
    public ResponseEntity<TarifAchat> createTarifAchat(@Valid @RequestBody TarifAchat tarifAchat) throws URISyntaxException {
        log.debug("REST request to save TarifAchat : {}", tarifAchat);
        if (tarifAchat.getId() != null) {
            throw new BadRequestAlertException("A new tarifAchat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifAchat result = tarifAchatService.save(tarifAchat);
        return ResponseEntity.created(new URI("/api/tarif-achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tarif-achats} : Updates an existing tarifAchat.
     *
     * @param tarifAchat the tarifAchat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarifAchat,
     * or with status {@code 400 (Bad Request)} if the tarifAchat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarifAchat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tarif-achats")
    public ResponseEntity<TarifAchat> updateTarifAchat(@Valid @RequestBody TarifAchat tarifAchat) throws URISyntaxException {
        log.debug("REST request to update TarifAchat : {}", tarifAchat);
        if (tarifAchat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TarifAchat result = tarifAchatService.save(tarifAchat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarifAchat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tarif-achats} : get all the tarifAchats.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarifAchats in body.
     */
    @GetMapping("/tarif-achats")
    public ResponseEntity<List<TarifAchat>> getAllTarifAchats(TarifAchatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TarifAchats by criteria: {}", criteria);
        Page<TarifAchat> page = tarifAchatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /tarif-achats/count} : count all the tarifAchats.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/tarif-achats/count")
    public ResponseEntity<Long> countTarifAchats(TarifAchatCriteria criteria) {
        log.debug("REST request to count TarifAchats by criteria: {}", criteria);
        return ResponseEntity.ok().body(tarifAchatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tarif-achats/:id} : get the "id" tarifAchat.
     *
     * @param id the id of the tarifAchat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarifAchat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tarif-achats/{id}")
    public ResponseEntity<TarifAchat> getTarifAchat(@PathVariable Long id) {
        log.debug("REST request to get TarifAchat : {}", id);
        Optional<TarifAchat> tarifAchat = tarifAchatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarifAchat);
    }

    /**
     * {@code DELETE  /tarif-achats/:id} : delete the "id" tarifAchat.
     *
     * @param id the id of the tarifAchat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tarif-achats/{id}")
    public ResponseEntity<Void> deleteTarifAchat(@PathVariable Long id) {
        log.debug("REST request to delete TarifAchat : {}", id);
        tarifAchatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
