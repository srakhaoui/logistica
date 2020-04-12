package com.logistica.web.rest;

import com.logistica.domain.Livraison;
import com.logistica.service.dto.RecapitulatifAchat;
import com.logistica.service.LivraisonService;
import com.logistica.service.dto.*;
import com.logistica.web.rest.errors.BadRequestAlertException;
import com.logistica.service.LivraisonQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.logistica.domain.Livraison}.
 */
@RestController
@RequestMapping("/api")
public class LivraisonResource {

    private final Logger log = LoggerFactory.getLogger(LivraisonResource.class);

    private static final String ENTITY_NAME = "livraison";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivraisonService livraisonService;

    private final LivraisonQueryService livraisonQueryService;

    public LivraisonResource(LivraisonService livraisonService, LivraisonQueryService livraisonQueryService) {
        this.livraisonService = livraisonService;
        this.livraisonQueryService = livraisonQueryService;
    }

    /**
     * {@code POST  /livraisons} : Create a new livraison.
     *
     * @param livraison the livraison to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livraison, or with status {@code 400 (Bad Request)} if the livraison has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livraisons")
    public ResponseEntity<Livraison> createLivraison(@Valid @RequestBody Livraison livraison) throws URISyntaxException {
        log.debug("REST request to save Livraison : {}", livraison);
        if (livraison.getId() != null) {
            throw new BadRequestAlertException("A new livraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livraison result = livraisonService.save(livraison);
        return ResponseEntity.created(new URI("/api/livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livraisons} : Updates an existing livraison.
     *
     * @param livraison the livraison to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livraison,
     * or with status {@code 400 (Bad Request)} if the livraison is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livraison couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livraisons")
    public ResponseEntity<Livraison> updateLivraison(@Valid @RequestBody Livraison livraison) throws URISyntaxException {
        log.debug("REST request to update Livraison : {}", livraison);
        if (livraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livraison result = livraisonService.save(livraison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livraison.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livraisons} : get all the livraisons.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livraisons in body.
     */
    @GetMapping("/livraisons")
    public ResponseEntity<List<Livraison>> getAllLivraisons(LivraisonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Livraisons by criteria: {}", criteria);
        Page<Livraison> page = livraisonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /livraisons/count} : count all the livraisons.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/livraisons/count")
    public ResponseEntity<Long> countLivraisons(LivraisonCriteria criteria) {
        log.debug("REST request to count Livraisons by criteria: {}", criteria);
        return ResponseEntity.ok().body(livraisonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /livraisons/:id} : get the "id" livraison.
     *
     * @param id the id of the livraison to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livraison, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livraisons/{id}")
    public ResponseEntity<Livraison> getLivraison(@PathVariable Long id) {
        log.debug("REST request to get Livraison : {}", id);
        Optional<Livraison> livraison = livraisonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livraison);
    }

    /**
     * {@code DELETE  /livraisons/:id} : delete the "id" livraison.
     *
     * @param id the id of the livraison to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livraisons/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Long id) {
        log.debug("REST request to delete Livraison : {}", id);
        livraisonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/livraisons/achat")
    public ResponseEntity<List<RecapitulatifAchat>> getAllLivraisons(RecapitulatifAchatRequest recaptulatifAchatRequest, Pageable pageable) {
        log.debug("REST request to get RecapAchat : {}", recaptulatifAchatRequest);
        Page<RecapitulatifAchat> page = livraisonService.getRecapitulatifAchat(recaptulatifAchatRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/livraisons/achat/trajet")
    public ResponseEntity<List<Livraison>> getAllLivraisons(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable) {
        log.debug("REST request to get SuiviTrajetRequest : {}", suiviTrajetRequest);
        Page<Livraison> page = livraisonService.getSuiviTrajet(suiviTrajetRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/livraisons/vente/client")
    public ResponseEntity<List<RecapitulatifClient>> getAllLivraisons(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable) {
        log.debug("REST request to get recapitulatifClientRequest : {}", recapitulatifClientRequest);
        Page<RecapitulatifClient> page = livraisonService.getRecapitulatifClient(recapitulatifClientRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/livraisons/vente/chauffeur")
    public ResponseEntity<List<IRecapitulatifChauffeur>> getAllLivraisons(RecapitulatifChauffeurRequest recapitulatifChauffeurRequest, Pageable pageable) {
        log.debug("REST request to get recapitulatifChauffeurRequest : {}", recapitulatifChauffeurRequest);
        Page<IRecapitulatifChauffeur> page = livraisonService.getRecapitulatifChauffeur(recapitulatifChauffeurRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/livraisons/vente/facturation")
    public ResponseEntity<List<RecapitulatifFacturation>> getAllLivraisons(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable) {
        log.debug("REST request to get RecapitulatifFacturationRequest : {}", recapitulatifFacturationRequest);
        Page<RecapitulatifFacturation> page = livraisonService.getRecapitulatifFacturation(recapitulatifFacturationRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/livraisons/vente/ca-camion")
    public ResponseEntity<List<RecapitulatifCaCamion>> getAllLivraisons(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable) {
        Page<RecapitulatifCaCamion> page = livraisonService.getRecapitulatifCaCamion(recapitulatifCaCamionRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
