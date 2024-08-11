package com.logistica.web.rest;

import com.logistica.domain.Facture;
import com.logistica.domain.Reglement;
import com.logistica.service.FactureService;
import com.logistica.service.dto.*;
import com.logistica.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
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
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class FactureResource {

    private final Logger log = LoggerFactory.getLogger(FactureResource.class);
    private static final String ENTITY_NAME = "facture";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final FactureService factureService;

    public FactureResource(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/factures")
    public ResponseEntity<FacturationResponse> save(@Valid @RequestBody FacturationRequest facturationRequest) throws URISyntaxException {
        FacturationResponse facturationResponse = factureService.save(facturationRequest);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.created(new URI("/api/factures/" + facturationResponse.getId()));
        if(Objects.nonNull(facturationResponse.getId())){
            bodyBuilder.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, facturationResponse.getId().toString()));
        }
        return bodyBuilder.body(facturationResponse);
    }

    @GetMapping("/factures")
    public ResponseEntity<List<Facture>> findFactures(RecapitulatifFacturationClientRequest recapitulatifFacturationClientRequest, Pageable pageable) {
        Page<Facture> page = factureService.findFactures(recapitulatifFacturationClientRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PutMapping("/factures")
    public ResponseEntity<ValideFactureResponse> updateStatusFacture(@Valid @RequestBody ValiderFactureRequest validerFactureRequest){
        log.debug("REST request to update Facture : {}", validerFactureRequest);
        if (validerFactureRequest.getFactureId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ValideFactureResponse valideFactureResponse = factureService.updateStatusFacture(validerFactureRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, validerFactureRequest.getFactureId().toString()))
            .body(valideFactureResponse);
    }

}
