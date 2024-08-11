package com.logistica.web.rest;

import com.logistica.domain.Facture;
import com.logistica.service.FactureService;
import com.logistica.service.dto.*;
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

@RestController
@RequestMapping("/api")
public class ReglementResource {

    private final Logger log = LoggerFactory.getLogger(ReglementResource.class);
    private static final String ENTITY_NAME = "reglement";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final FactureService factureService;

    public ReglementResource(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/reglements")
    public ResponseEntity<ReglementResponse> reglerFacture(@Valid @RequestBody ReglementRequest reglementRequest) throws URISyntaxException {
        ReglementResponse reglementResponse = factureService.reglerFacture(reglementRequest);
        return ResponseEntity.created(new URI("/api/reglements/" + reglementResponse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(reglementResponse.getId())))
            .body(reglementResponse);
    }

    @GetMapping("/reglements")
    public ResponseEntity<List<Facture>> findReglements(RecapitulatifFacturationClientRequest recapitulatifFacturationClientRequest, Pageable pageable) {
        Page<Facture> page = factureService.findFactures(recapitulatifFacturationClientRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
