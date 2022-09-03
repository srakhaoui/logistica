package com.logistica.web.rest;

import com.logistica.domain.AgregatTransfert;
import com.logistica.service.AgregatTransfertService;
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
 * REST controller for managing {@link com.logistica.domain.AgregatTransfert}.
 */
@RestController
@RequestMapping("/api")
public class AgregatTransfertResource {

    private final Logger log = LoggerFactory.getLogger(AgregatTransfertResource.class);

    private static final String ENTITY_NAME = "agregatTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgregatTransfertService agregatTransfertService;

    public AgregatTransfertResource(AgregatTransfertService agregatTransfertService) {
        this.agregatTransfertService = agregatTransfertService;
    }

    /**
     * {@code POST  /agregat-transferts} : Create a new agregatTransfert.
     *
     * @param agregatTransfert the agregatTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agregatTransfert, or with status {@code 400 (Bad Request)} if the agregatTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agregat-transferts")
    public ResponseEntity<AgregatTransfert> createAgregatTransfert(@Valid @RequestBody AgregatTransfert agregatTransfert) throws URISyntaxException {
        log.debug("REST request to save AgregatTransfert : {}", agregatTransfert);
        if (agregatTransfert.getId() != null) {
            throw new BadRequestAlertException("A new agregatTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgregatTransfert result = agregatTransfertService.save(agregatTransfert);
        return ResponseEntity.created(new URI("/api/agregat-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agregat-transferts} : Updates an existing agregatTransfert.
     *
     * @param agregatTransfert the agregatTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agregatTransfert,
     * or with status {@code 400 (Bad Request)} if the agregatTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agregatTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agregat-transferts")
    public ResponseEntity<AgregatTransfert> updateAgregatTransfert(@Valid @RequestBody AgregatTransfert agregatTransfert) throws URISyntaxException {
        log.debug("REST request to update AgregatTransfert : {}", agregatTransfert);
        if (agregatTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgregatTransfert result = agregatTransfertService.save(agregatTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agregatTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agregat-transferts} : get all the agregatTransferts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agregatTransferts in body.
     */
    @GetMapping("/agregat-transferts")
    public ResponseEntity<List<AgregatTransfert>> getAllAgregatTransferts(Pageable pageable) {
        log.debug("REST request to get a page of AgregatTransferts");
        Page<AgregatTransfert> page = agregatTransfertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agregat-transferts/:id} : get the "id" agregatTransfert.
     *
     * @param id the id of the agregatTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agregatTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agregat-transferts/{id}")
    public ResponseEntity<AgregatTransfert> getAgregatTransfert(@PathVariable Long id) {
        log.debug("REST request to get AgregatTransfert : {}", id);
        Optional<AgregatTransfert> agregatTransfert = agregatTransfertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agregatTransfert);
    }

    /**
     * {@code DELETE  /agregat-transferts/:id} : delete the "id" agregatTransfert.
     *
     * @param id the id of the agregatTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agregat-transferts/{id}")
    public ResponseEntity<Void> deleteAgregatTransfert(@PathVariable Long id) {
        log.debug("REST request to delete AgregatTransfert : {}", id);
        agregatTransfertService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
