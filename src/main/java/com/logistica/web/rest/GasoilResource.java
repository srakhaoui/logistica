package com.logistica.web.rest;

import com.logistica.domain.Gasoil;
import com.logistica.service.GasoilQueryService;
import com.logistica.service.GasoilService;
import com.logistica.service.dto.*;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.logistica.domain.Gasoil}.
 */
@RestController
@RequestMapping("/api")
public class GasoilResource {

    private final Logger log = LoggerFactory.getLogger(GasoilResource.class);

    private static final String ENTITY_NAME = "gasoil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GasoilService gasoilService;

    private final GasoilQueryService gasoilQueryService;

    public GasoilResource(GasoilService gasoilService, GasoilQueryService gasoilQueryService) {
        this.gasoilService = gasoilService;
        this.gasoilQueryService = gasoilQueryService;
    }

    /**
     * {@code POST  /gasoils} : Create a new gasoil.
     *
     * @param gasoil the gasoil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gasoil, or with status {@code 400 (Bad Request)} if the gasoil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gasoils")
    public ResponseEntity<Gasoil> createGasoil(@Valid @RequestBody Gasoil gasoil) throws URISyntaxException {
        log.debug("REST request to save Gasoil : {}", gasoil);
        if (gasoil.getId() != null) {
            throw new BadRequestAlertException("A new gasoil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gasoil result = gasoilService.save(gasoil);
        return ResponseEntity.created(new URI("/api/gasoils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gasoils} : Updates an existing gasoil.
     *
     * @param gasoil the gasoil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasoil,
     * or with status {@code 400 (Bad Request)} if the gasoil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gasoil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gasoils")
    public ResponseEntity<Gasoil> updateGasoil(@Valid @RequestBody Gasoil gasoil) throws URISyntaxException {
        log.debug("REST request to update Gasoil : {}", gasoil);
        if (gasoil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gasoil result = gasoilService.save(gasoil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasoil.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gasoils} : get all the gasoils.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasoils in body.
     */
    @GetMapping("/gasoils")
    public ResponseEntity<List<Gasoil>> getAllGasoils(GasoilCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Gasoils by criteria: {}", criteria);
        Page<Gasoil> page = gasoilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gasoils/count} : count all the gasoils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gasoils/count")
    public ResponseEntity<Long> countGasoils(GasoilCriteria criteria) {
        log.debug("REST request to count Gasoils by criteria: {}", criteria);
        return ResponseEntity.ok().body(gasoilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gasoils/:id} : get the "id" gasoil.
     *
     * @param id the id of the gasoil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gasoil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gasoils/{id}")
    public ResponseEntity<Gasoil> getGasoil(@PathVariable Long id) {
        log.debug("REST request to get Gasoil : {}", id);
        Optional<Gasoil> gasoil = gasoilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gasoil);
    }

    /**
     * {@code DELETE  /gasoils/:id} : delete the "id" gasoil.
     *
     * @param id the id of the gasoil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gasoils/{id}")
    public ResponseEntity<Void> deleteGasoil(@PathVariable Long id) {
        log.debug("REST request to delete Gasoil : {}", id);
        gasoilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(value = "/gasoils/charges")
    public ResponseEntity<List<RecapitulatifChargeGasoil>> getAllLivraisons(@Valid RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable) {
        log.debug("REST request to get RecapitulatifChargeGasoilRequest : {}", recapitulatifChargeGasoilRequest);
        Page<RecapitulatifChargeGasoil> page = gasoilService.getRecapitulatifChargeGasoil(recapitulatifChargeGasoilRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/gasoils/charges/export")
    public void getAllLivraisons(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.debug("REST request to get recapitulatifChargeGasoilRequest : {}", recapitulatifChargeGasoilRequest);
        Page<RecapitulatifChargeGasoil> page = gasoilService.getRecapitulatifChargeGasoil(recapitulatifChargeGasoilRequest, Pageable.unpaged());
        buildAndSendCsv("export-gasoil.csv", RecapitulatifClient.csvHeader(), page.getContent(), httpServletResponse);
    }

    private <T extends ICsvConvertible> void buildAndSendCsv(String filename, String csvHeader, List<T> page, HttpServletResponse reponse) throws IOException {
        reponse.setContentType("text/csv");
        reponse.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));
        ServletOutputStream outputStream = reponse.getOutputStream();
        outputStream.println(csvHeader);
        page.stream().map(T::toCsv).forEach(csvContent -> {
            try {
                outputStream.println(csvContent);
            } catch (IOException e) {
                log.error("Failed to generate csv file", e);
            }
        });
    }
}
