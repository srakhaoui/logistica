package com.logistica.web.rest;

import com.logistica.domain.GasoilAchatGros;
import com.logistica.service.GasoilAchatGrosQueryService;
import com.logistica.service.GasoilAchatGrosService;
import com.logistica.service.dto.GasoilAchatGrosCriteria;
import com.logistica.service.dto.ICsvConvertible;
import com.logistica.service.dto.RecapitulatifGasoilAchatGros;
import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
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
 * REST controller for managing {@link com.logistica.domain.GasoilAchatGros}.
 */
@RestController
@RequestMapping("/api")
public class GasoilAchatGrosResource {

    private final Logger log = LoggerFactory.getLogger(GasoilAchatGrosResource.class);

    private static final String ENTITY_NAME = "gasoilAchatGros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GasoilAchatGrosService gasoilAchatGrosService;

    private final GasoilAchatGrosQueryService gasoilAchatGrosQueryService;

    public GasoilAchatGrosResource(GasoilAchatGrosService gasoilAchatGrosService, GasoilAchatGrosQueryService gasoilAchatGrosQueryService) {
        this.gasoilAchatGrosService = gasoilAchatGrosService;
        this.gasoilAchatGrosQueryService = gasoilAchatGrosQueryService;
    }

    /**
     * {@code POST  /gasoil-achat-gros} : Create a new gasoilAchatGros.
     *
     * @param gasoilAchatGros the gasoilAchatGros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gasoilAchatGros, or with status {@code 400 (Bad Request)} if the gasoilAchatGros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gasoil-achat-gros")
    public ResponseEntity<GasoilAchatGros> createGasoilAchatGros(@Valid @RequestBody GasoilAchatGros gasoilAchatGros) throws URISyntaxException {
        log.debug("REST request to save GasoilAchatGros : {}", gasoilAchatGros);
        if (gasoilAchatGros.getId() != null) {
            throw new BadRequestAlertException("A new gasoilAchatGros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GasoilAchatGros result = gasoilAchatGrosService.save(gasoilAchatGros);
        return ResponseEntity.created(new URI("/api/gasoil-achat-gros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gasoil-achat-gros} : Updates an existing gasoilAchatGros.
     *
     * @param gasoilAchatGros the gasoilAchatGros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasoilAchatGros,
     * or with status {@code 400 (Bad Request)} if the gasoilAchatGros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gasoilAchatGros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gasoil-achat-gros")
    public ResponseEntity<GasoilAchatGros> updateGasoilAchatGros(@Valid @RequestBody GasoilAchatGros gasoilAchatGros) throws URISyntaxException {
        log.debug("REST request to update GasoilAchatGros : {}", gasoilAchatGros);
        if (gasoilAchatGros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GasoilAchatGros result = gasoilAchatGrosService.save(gasoilAchatGros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasoilAchatGros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gasoil-achat-gros} : get all the gasoilAchatGros.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasoilAchatGros in body.
     */
    @GetMapping("/gasoil-achat-gros")
    public ResponseEntity<List<GasoilAchatGros>> getAllGasoilAchatGros(GasoilAchatGrosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GasoilAchatGros by criteria: {}", criteria);
        Page<GasoilAchatGros> page = gasoilAchatGrosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gasoil-achat-gros/count} : count all the gasoilAchatGros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gasoil-achat-gros/count")
    public ResponseEntity<Long> countGasoilAchatGros(GasoilAchatGrosCriteria criteria) {
        log.debug("REST request to count GasoilAchatGros by criteria: {}", criteria);
        return ResponseEntity.ok().body(gasoilAchatGrosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gasoil-achat-gros/:id} : get the "id" gasoilAchatGros.
     *
     * @param id the id of the gasoilAchatGros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gasoilAchatGros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gasoil-achat-gros/{id}")
    public ResponseEntity<GasoilAchatGros> getGasoilAchatGros(@PathVariable Long id) {
        log.debug("REST request to get GasoilAchatGros : {}", id);
        Optional<GasoilAchatGros> gasoilAchatGros = gasoilAchatGrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gasoilAchatGros);
    }

    /**
     * {@code DELETE  /gasoil-achat-gros/:id} : delete the "id" gasoilAchatGros.
     *
     * @param id the id of the gasoilAchatGros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gasoil-achat-gros/{id}")
    public ResponseEntity<Void> deleteGasoilAchatGros(@PathVariable Long id) {
        log.debug("REST request to delete GasoilAchatGros : {}", id);
        gasoilAchatGrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/gasoil-achat-gros/achats")
    public ResponseEntity<List<RecapitulatifGasoilAchatGros>> getAchatsGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        log.debug("REST request to get a page of RecapitulatifGasoilAchatGros");
        Page<RecapitulatifGasoilAchatGros> page = gasoilAchatGrosService.getRecapitulatifGasoilAchatGros(recapitulatifGasoilGrosRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/gasoil-achat-gros/achats/export")
    public void exportAchatGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.debug("REST request to export RecapitulatifGasoilAchatGros : {}", recapitulatifGasoilGrosRequest);
        Page<RecapitulatifGasoilAchatGros> page = gasoilAchatGrosService.getRecapitulatifGasoilAchatGros(recapitulatifGasoilGrosRequest, Pageable.unpaged());
        buildAndSendCsv("export-gasoil-achat.csv", RecapitulatifGasoilAchatGros.csvHeader(), page.getContent(), httpServletResponse);
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
