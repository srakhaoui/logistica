package com.logistica.web.rest;

import com.logistica.domain.FournisseurGrossiste;
import com.logistica.service.FournisseurGrossisteQueryService;
import com.logistica.service.FournisseurGrossisteService;
import com.logistica.service.dto.FournisseurGrossisteCriteria;
import com.logistica.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.logistica.domain.FournisseurGrossiste}.
 */
@RestController
@RequestMapping("/api")
public class FournisseurGrossisteResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurGrossisteResource.class);

    private static final String ENTITY_NAME = "fournisseurGrossiste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FournisseurGrossisteService fournisseurGrossisteService;

    private final FournisseurGrossisteQueryService fournisseurGrossisteQueryService;

    public FournisseurGrossisteResource(FournisseurGrossisteService fournisseurGrossisteService, FournisseurGrossisteQueryService fournisseurGrossisteQueryService) {
        this.fournisseurGrossisteService = fournisseurGrossisteService;
        this.fournisseurGrossisteQueryService = fournisseurGrossisteQueryService;
    }

    /**
     * {@code POST  /fournisseur-grossistes} : Create a new fournisseurGrossiste.
     *
     * @param fournisseurGrossiste the fournisseurGrossiste to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fournisseurGrossiste, or with status {@code 400 (Bad Request)} if the fournisseurGrossiste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fournisseur-grossistes")
    public ResponseEntity<FournisseurGrossiste> createFournisseurGrossiste(@Valid @RequestBody FournisseurGrossiste fournisseurGrossiste) throws URISyntaxException {
        log.debug("REST request to save FournisseurGrossiste : {}", fournisseurGrossiste);
        if (fournisseurGrossiste.getId() != null) {
            throw new BadRequestAlertException("A new fournisseurGrossiste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FournisseurGrossiste result = fournisseurGrossisteService.save(fournisseurGrossiste);
        return ResponseEntity.created(new URI("/api/fournisseur-grossistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fournisseur-grossistes} : Updates an existing fournisseurGrossiste.
     *
     * @param fournisseurGrossiste the fournisseurGrossiste to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fournisseurGrossiste,
     * or with status {@code 400 (Bad Request)} if the fournisseurGrossiste is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fournisseurGrossiste couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fournisseur-grossistes")
    public ResponseEntity<FournisseurGrossiste> updateFournisseurGrossiste(@Valid @RequestBody FournisseurGrossiste fournisseurGrossiste) throws URISyntaxException {
        log.debug("REST request to update FournisseurGrossiste : {}", fournisseurGrossiste);
        if (fournisseurGrossiste.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FournisseurGrossiste result = fournisseurGrossisteService.save(fournisseurGrossiste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fournisseurGrossiste.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fournisseur-grossistes} : get all the fournisseurGrossistes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fournisseurGrossistes in body.
     */
    @GetMapping("/fournisseur-grossistes")
    public ResponseEntity<List<FournisseurGrossiste>> getAllFournisseurGrossistes(FournisseurGrossisteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FournisseurGrossistes by criteria: {}", criteria);
        Page<FournisseurGrossiste> page = fournisseurGrossisteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fournisseur-grossistes/count} : count all the fournisseurGrossistes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fournisseur-grossistes/count")
    public ResponseEntity<Long> countFournisseurGrossistes(FournisseurGrossisteCriteria criteria) {
        log.debug("REST request to count FournisseurGrossistes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fournisseurGrossisteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fournisseur-grossistes/:id} : get the "id" fournisseurGrossiste.
     *
     * @param id the id of the fournisseurGrossiste to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fournisseurGrossiste, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fournisseur-grossistes/{id}")
    public ResponseEntity<FournisseurGrossiste> getFournisseurGrossiste(@PathVariable Long id) {
        log.debug("REST request to get FournisseurGrossiste : {}", id);
        Optional<FournisseurGrossiste> fournisseurGrossiste = fournisseurGrossisteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fournisseurGrossiste);
    }

    /**
     * {@code DELETE  /fournisseur-grossistes/:id} : delete the "id" fournisseurGrossiste.
     *
     * @param id the id of the fournisseurGrossiste to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fournisseur-grossistes/{id}")
    public ResponseEntity<Void> deleteFournisseurGrossiste(@PathVariable Long id) {
        log.debug("REST request to delete FournisseurGrossiste : {}", id);
        fournisseurGrossisteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
