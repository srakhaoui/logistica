package com.logistica.web.rest;

import com.logistica.domain.GasoilTransfert;
import com.logistica.service.GasoilTransfertService;
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
 * REST controller for managing {@link com.logistica.domain.GasoilTransfert}.
 */
@RestController
@RequestMapping("/api")
public class GasoilTransfertResource {

    private final Logger log = LoggerFactory.getLogger(GasoilTransfertResource.class);

    private static final String ENTITY_NAME = "gasoilTransfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GasoilTransfertService gasoilTransfertService;

    public GasoilTransfertResource(GasoilTransfertService gasoilTransfertService) {
        this.gasoilTransfertService = gasoilTransfertService;
    }

    /**
     * {@code POST  /gasoil-transferts} : Create a new gasoilTransfert.
     *
     * @param gasoilTransfert the gasoilTransfert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gasoilTransfert, or with status {@code 400 (Bad Request)} if the gasoilTransfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gasoil-transferts")
    public ResponseEntity<GasoilTransfert> createGasoilTransfert(@Valid @RequestBody GasoilTransfert gasoilTransfert) throws URISyntaxException {
        log.debug("REST request to save GasoilTransfert : {}", gasoilTransfert);
        if (gasoilTransfert.getId() != null) {
            throw new BadRequestAlertException("A new gasoilTransfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GasoilTransfert result = gasoilTransfertService.save(gasoilTransfert);
        return ResponseEntity.created(new URI("/api/gasoil-transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gasoil-transferts} : Updates an existing gasoilTransfert.
     *
     * @param gasoilTransfert the gasoilTransfert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasoilTransfert,
     * or with status {@code 400 (Bad Request)} if the gasoilTransfert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gasoilTransfert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gasoil-transferts")
    public ResponseEntity<GasoilTransfert> updateGasoilTransfert(@Valid @RequestBody GasoilTransfert gasoilTransfert) throws URISyntaxException {
        log.debug("REST request to update GasoilTransfert : {}", gasoilTransfert);
        if (gasoilTransfert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GasoilTransfert result = gasoilTransfertService.save(gasoilTransfert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasoilTransfert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gasoil-transferts} : get all the gasoilTransferts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasoilTransferts in body.
     */
    @GetMapping("/gasoil-transferts")
    public ResponseEntity<List<GasoilTransfert>> getAllGasoilTransferts(Pageable pageable) {
        log.debug("REST request to get a page of GasoilTransferts");
        Page<GasoilTransfert> page = gasoilTransfertService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gasoil-transferts/:id} : get the "id" gasoilTransfert.
     *
     * @param id the id of the gasoilTransfert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gasoilTransfert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gasoil-transferts/{id}")
    public ResponseEntity<GasoilTransfert> getGasoilTransfert(@PathVariable Long id) {
        log.debug("REST request to get GasoilTransfert : {}", id);
        Optional<GasoilTransfert> gasoilTransfert = gasoilTransfertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gasoilTransfert);
    }

    /**
     * {@code DELETE  /gasoil-transferts/:id} : delete the "id" gasoilTransfert.
     *
     * @param id the id of the gasoilTransfert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gasoil-transferts/{id}")
    public ResponseEntity<Void> deleteGasoilTransfert(@PathVariable Long id) {
        log.debug("REST request to delete GasoilTransfert : {}", id);
        gasoilTransfertService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
