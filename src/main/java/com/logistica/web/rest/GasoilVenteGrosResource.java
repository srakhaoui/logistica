package com.logistica.web.rest;

import com.logistica.domain.GasoilVenteGros;
import com.logistica.service.GasoilVenteGrosService;
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
 * REST controller for managing {@link com.logistica.domain.GasoilVenteGros}.
 */
@RestController
@RequestMapping("/api")
public class GasoilVenteGrosResource {

    private final Logger log = LoggerFactory.getLogger(GasoilVenteGrosResource.class);

    private static final String ENTITY_NAME = "gasoilVenteGros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GasoilVenteGrosService gasoilVenteGrosService;

    public GasoilVenteGrosResource(GasoilVenteGrosService gasoilVenteGrosService) {
        this.gasoilVenteGrosService = gasoilVenteGrosService;
    }

    /**
     * {@code POST  /gasoil-vente-gros} : Create a new gasoilVenteGros.
     *
     * @param gasoilVenteGros the gasoilVenteGros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gasoilVenteGros, or with status {@code 400 (Bad Request)} if the gasoilVenteGros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gasoil-vente-gros")
    public ResponseEntity<GasoilVenteGros> createGasoilVenteGros(@Valid @RequestBody GasoilVenteGros gasoilVenteGros) throws URISyntaxException {
        log.debug("REST request to save GasoilVenteGros : {}", gasoilVenteGros);
        if (gasoilVenteGros.getId() != null) {
            throw new BadRequestAlertException("A new gasoilVenteGros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GasoilVenteGros result = gasoilVenteGrosService.save(gasoilVenteGros);
        return ResponseEntity.created(new URI("/api/gasoil-vente-gros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gasoil-vente-gros} : Updates an existing gasoilVenteGros.
     *
     * @param gasoilVenteGros the gasoilVenteGros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasoilVenteGros,
     * or with status {@code 400 (Bad Request)} if the gasoilVenteGros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gasoilVenteGros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gasoil-vente-gros")
    public ResponseEntity<GasoilVenteGros> updateGasoilVenteGros(@Valid @RequestBody GasoilVenteGros gasoilVenteGros) throws URISyntaxException {
        log.debug("REST request to update GasoilVenteGros : {}", gasoilVenteGros);
        if (gasoilVenteGros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GasoilVenteGros result = gasoilVenteGrosService.save(gasoilVenteGros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasoilVenteGros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gasoil-vente-gros} : get all the gasoilVenteGros.
     *
     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasoilVenteGros in body.
     */
    @GetMapping("/gasoil-vente-gros")
    public ResponseEntity<List<GasoilVenteGros>> getAllGasoilVenteGros(Pageable pageable) {
        log.debug("REST request to get a page of GasoilVenteGros");
        Page<GasoilVenteGros> page = gasoilVenteGrosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gasoil-vente-gros/:id} : get the "id" gasoilVenteGros.
     *
     * @param id the id of the gasoilVenteGros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gasoilVenteGros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gasoil-vente-gros/{id}")
    public ResponseEntity<GasoilVenteGros> getGasoilVenteGros(@PathVariable Long id) {
        log.debug("REST request to get GasoilVenteGros : {}", id);
        Optional<GasoilVenteGros> gasoilVenteGros = gasoilVenteGrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gasoilVenteGros);
    }

    /**
     * {@code DELETE  /gasoil-vente-gros/:id} : delete the "id" gasoilVenteGros.
     *
     * @param id the id of the gasoilVenteGros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gasoil-vente-gros/{id}")
    public ResponseEntity<Void> deleteGasoilVenteGros(@PathVariable Long id) {
        log.debug("REST request to delete GasoilVenteGros : {}", id);
        gasoilVenteGrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/gasoil-vente-gros/transactions")
    public ResponseEntity<RecapitulatifGasoilGros> getTransactionGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        log.debug("REST request to get a page of RecapitulatifGasoilTransactionGros");
        Page<RecapitulatifGasoilTransactionGros> page = gasoilVenteGrosService.getRecapitulatifGasoilTransactionGros(recapitulatifGasoilGrosRequest, pageable);
        RecapitulatifGasoilGros recapitulatifGasoilGros = gasoilVenteGrosService.getRecapitulatifGasoilGros(recapitulatifGasoilGrosRequest);
        recapitulatifGasoilGros.setRecapitulatifGasoilTransactionGrosList(page.getContent());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(recapitulatifGasoilGros);
    }

    @GetMapping(value = "/gasoil-vente-gros/transactions/export")
    public void exportTransactionsGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.debug("REST request to export RecapitulatifGasoilTransactionGros : {}", recapitulatifGasoilGrosRequest);
        Page<RecapitulatifGasoilTransactionGros> page = gasoilVenteGrosService.getRecapitulatifGasoilTransactionGros(recapitulatifGasoilGrosRequest, Pageable.unpaged());
        buildAndSendCsv("export-gasoil-vente.csv", RecapitulatifGasoilVenteGros.csvHeader(), page.getContent(), httpServletResponse);
    }

    /**
     * {@code GET  /gasoil-vente-gros/ventes} : get all the ventes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasoilVenteGros in body.
     */
    @GetMapping("/gasoil-vente-gros/ventes")
    public ResponseEntity<List<RecapitulatifGasoilVenteGros>> getVentesGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        log.debug("REST request to get a page of RecapitulatifGasoilVenteGros");
        Page<RecapitulatifGasoilVenteGros> page = gasoilVenteGrosService.getRecapitulatifGasoilVenteGros(recapitulatifGasoilGrosRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/gasoil-vente-gros/ventes/export")
    public void exportVentesGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.debug("REST request to export RecapitulatifGasoilVenteGros : {}", recapitulatifGasoilGrosRequest);
        Page<RecapitulatifGasoilVenteGros> page = gasoilVenteGrosService.getRecapitulatifGasoilVenteGros(recapitulatifGasoilGrosRequest, Pageable.unpaged());
        buildAndSendCsv("export-gasoil-vente.csv", RecapitulatifGasoilVenteGros.csvHeader(), page.getContent(), httpServletResponse);
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
