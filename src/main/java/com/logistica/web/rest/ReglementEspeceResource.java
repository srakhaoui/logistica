package com.logistica.web.rest;

import com.logistica.domain.Reglement;
import com.logistica.domain.ReglementEspece;
import com.logistica.service.FactureService;
import com.logistica.service.dto.FacturationRequest;
import com.logistica.service.dto.ReglementEspeceRequest;
import com.logistica.service.dto.ReglementEspeceResponse;
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
public class ReglementEspeceResource {

    private final Logger log = LoggerFactory.getLogger(ReglementEspeceResource.class);
    private static final String ENTITY_NAME = "reglementespece";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final FactureService factureService;

    public ReglementEspeceResource(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/reglementespeces")
    public ResponseEntity<ReglementEspeceResponse> reglerEnEspece(@Valid @RequestBody ReglementEspeceRequest reglementEspeceRequest) throws URISyntaxException {
        ReglementEspeceResponse reglementEspece = factureService.reglerEspece(reglementEspeceRequest);
        if(reglementEspece.isCreated()) {
            return ResponseEntity.created(new URI("/api/reglementespeces/" + reglementEspece.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(reglementEspece.getId())))
                .body(reglementEspece);
        }
        return ResponseEntity.ok(reglementEspece);
    }

    @GetMapping("/reglementespeces")
    public ResponseEntity<List<ReglementEspece>> findReglementsEspece(FacturationRequest facturationRequest, Pageable pageable) {
        Page<ReglementEspece> page = factureService.findReglementsEspece(facturationRequest, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
