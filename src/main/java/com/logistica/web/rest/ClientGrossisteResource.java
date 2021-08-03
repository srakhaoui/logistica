package com.logistica.web.rest;

import com.logistica.domain.ClientGrossiste;
import com.logistica.service.ClientGrossisteQueryService;
import com.logistica.service.ClientGrossisteService;
import com.logistica.service.dto.ClientGrossisteCriteria;
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
 * REST controller for managing {@link com.logistica.domain.ClientGrossiste}.
 */
@RestController
@RequestMapping("/api")
public class ClientGrossisteResource {

    private final Logger log = LoggerFactory.getLogger(ClientGrossisteResource.class);

    private static final String ENTITY_NAME = "clientGrossiste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientGrossisteService clientGrossisteService;

    private final ClientGrossisteQueryService clientGrossisteQueryService;

    public ClientGrossisteResource(ClientGrossisteService clientGrossisteService, ClientGrossisteQueryService clientGrossisteQueryService) {
        this.clientGrossisteService = clientGrossisteService;
        this.clientGrossisteQueryService = clientGrossisteQueryService;
    }

    /**
     * {@code POST  /client-grossistes} : Create a new clientGrossiste.
     *
     * @param clientGrossiste the clientGrossiste to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientGrossiste, or with status {@code 400 (Bad Request)} if the clientGrossiste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-grossistes")
    public ResponseEntity<ClientGrossiste> createClientGrossiste(@Valid @RequestBody ClientGrossiste clientGrossiste) throws URISyntaxException {
        log.debug("REST request to save ClientGrossiste : {}", clientGrossiste);
        if (clientGrossiste.getId() != null) {
            throw new BadRequestAlertException("A new clientGrossiste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientGrossiste result = clientGrossisteService.save(clientGrossiste);
        return ResponseEntity.created(new URI("/api/client-grossistes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-grossistes} : Updates an existing clientGrossiste.
     *
     * @param clientGrossiste the clientGrossiste to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientGrossiste,
     * or with status {@code 400 (Bad Request)} if the clientGrossiste is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientGrossiste couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-grossistes")
    public ResponseEntity<ClientGrossiste> updateClientGrossiste(@Valid @RequestBody ClientGrossiste clientGrossiste) throws URISyntaxException {
        log.debug("REST request to update ClientGrossiste : {}", clientGrossiste);
        if (clientGrossiste.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientGrossiste result = clientGrossisteService.save(clientGrossiste);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientGrossiste.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-grossistes} : get all the clientGrossistes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientGrossistes in body.
     */
    @GetMapping("/client-grossistes")
    public ResponseEntity<List<ClientGrossiste>> getAllClientGrossistes(ClientGrossisteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClientGrossistes by criteria: {}", criteria);
        Page<ClientGrossiste> page = clientGrossisteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-grossistes/count} : count all the clientGrossistes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/client-grossistes/count")
    public ResponseEntity<Long> countClientGrossistes(ClientGrossisteCriteria criteria) {
        log.debug("REST request to count ClientGrossistes by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientGrossisteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-grossistes/:id} : get the "id" clientGrossiste.
     *
     * @param id the id of the clientGrossiste to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientGrossiste, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-grossistes/{id}")
    public ResponseEntity<ClientGrossiste> getClientGrossiste(@PathVariable Long id) {
        log.debug("REST request to get ClientGrossiste : {}", id);
        Optional<ClientGrossiste> clientGrossiste = clientGrossisteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientGrossiste);
    }

    /**
     * {@code DELETE  /client-grossistes/:id} : delete the "id" clientGrossiste.
     *
     * @param id the id of the clientGrossiste to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-grossistes/{id}")
    public ResponseEntity<Void> deleteClientGrossiste(@PathVariable Long id) {
        log.debug("REST request to delete ClientGrossiste : {}", id);
        clientGrossisteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
