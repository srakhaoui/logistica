package com.logistica.web.rest;

import com.logistica.domain.Carburant;
import com.logistica.service.CarburantQueryService;
import com.logistica.service.CarburantService;
import com.logistica.service.dto.CarburantCriteria;
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
 * REST controller for managing {@link com.logistica.domain.Carburant}.
 */
@RestController
@RequestMapping("/api")
public class CarburantResource {

    private final Logger log = LoggerFactory.getLogger(CarburantResource.class);

    private static final String ENTITY_NAME = "carburant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarburantService carburantService;

    private final CarburantQueryService carburantQueryService;

    public CarburantResource(CarburantService carburantService, CarburantQueryService carburantQueryService) {
        this.carburantService = carburantService;
        this.carburantQueryService = carburantQueryService;
    }

    /**
     * {@code POST  /carburants} : Create a new carburant.
     *
     * @param carburant the carburant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carburant, or with status {@code 400 (Bad Request)} if the carburant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carburants")
    public ResponseEntity<Carburant> createCarburant(@Valid @RequestBody Carburant carburant) throws URISyntaxException {
        log.debug("REST request to save Carburant : {}", carburant);
        if (carburant.getId() != null) {
            throw new BadRequestAlertException("A new carburant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carburant result = carburantService.save(carburant);
        return ResponseEntity.created(new URI("/api/carburants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carburants} : Updates an existing carburant.
     *
     * @param carburant the carburant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carburant,
     * or with status {@code 400 (Bad Request)} if the carburant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carburant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carburants")
    public ResponseEntity<Carburant> updateCarburant(@Valid @RequestBody Carburant carburant) throws URISyntaxException {
        log.debug("REST request to update Carburant : {}", carburant);
        if (carburant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Carburant result = carburantService.save(carburant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carburant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carburants} : get all the carburants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carburants in body.
     */
    @GetMapping("/carburants")
    public ResponseEntity<List<Carburant>> getAllCarburants(CarburantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Carburants by criteria: {}", criteria);
        Page<Carburant> page = carburantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carburants/count} : count all the carburants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/carburants/count")
    public ResponseEntity<Long> countCarburants(CarburantCriteria criteria) {
        log.debug("REST request to count Carburants by criteria: {}", criteria);
        return ResponseEntity.ok().body(carburantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carburants/:id} : get the "id" carburant.
     *
     * @param id the id of the carburant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carburant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carburants/{id}")
    public ResponseEntity<Carburant> getCarburant(@PathVariable Long id) {
        log.debug("REST request to get Carburant : {}", id);
        Optional<Carburant> carburant = carburantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carburant);
    }

    /**
     * {@code DELETE  /carburants/:id} : delete the "id" carburant.
     *
     * @param id the id of the carburant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carburants/{id}")
    public ResponseEntity<Void> deleteCarburant(@PathVariable Long id) {
        log.debug("REST request to delete Carburant : {}", id);
        carburantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
