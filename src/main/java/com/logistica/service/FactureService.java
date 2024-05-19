package com.logistica.service;

import com.logistica.config.TvaProperties;
import com.logistica.domain.*;
import com.logistica.repository.FactureRepository;
import com.logistica.repository.ReglementEspeceRepository;
import com.logistica.repository.ReglementRepository;
import com.logistica.service.dto.*;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.logistica.domain.Facture.ENTITY_NAME;
import static com.logistica.service.util.DateUtils.isValidInvoicePeriod;

@Service
@Transactional
public class FactureService {

    private final Logger log = LoggerFactory.getLogger(FactureService.class);
    private final LivraisonService livraisonService;
    private final FactureRepository factureRepository;

    private final ReglementRepository reglementRepository;
    private final ReglementEspeceRepository reglementEspeceRepository;
    private final TvaProperties tvaProperties;

    public FactureService(LivraisonService livraisonService, FactureRepository factureRepository, ReglementRepository reglementRepository, ReglementEspeceRepository reglementEspeceRepository, TvaProperties tvaProperties) {
        this.livraisonService = livraisonService;
        this.factureRepository = factureRepository;
        this.reglementRepository = reglementRepository;
        this.reglementEspeceRepository = reglementEspeceRepository;
        this.tvaProperties = tvaProperties;
    }

    public ValideFactureResponse updateStatusFacture(ValiderFactureRequest validerFactureRequest){
        Assert.state(validerFactureRequest.getFactureId() >= 0, "factureId is not valide");
        factureRepository.updateStatusFacture(validerFactureRequest.getFactureId(), validerFactureRequest.getStatus());
        return ValideFactureResponse.of(factureRepository.getOne(validerFactureRequest.getFactureId()).getStatus());
    }

    @Transactional
    public FacturationResponse save(FacturationRequest facturationRequest){
        validateFacturationRequest(facturationRequest);

        List<RecapitulatifFacturationClient> recapitulatifFacturationClientList = livraisonService.getRecapitulatifFacturationClient(RecapitulatifFacturationClientRequest.from(facturationRequest, false, false));
        if(CollectionUtils.isNotEmpty(recapitulatifFacturationClientList)){
            Facture facture = getCurrentInvoiceOrBuildNewOne(facturationRequest, recapitulatifFacturationClientList);
            facture = factureRepository.saveAndFlush(facture);
            livraisonService.markAsBilled(RecapitulatifFacturationClientRequest.from(facturationRequest, false, false), facture.getId());
            return FacturationResponse.from(facture);
        }

        return FacturationResponse.noInvoice();
    }

    private void validateFacturationRequest(FacturationRequest facturationRequest) {
        if(!isValidInvoicePeriod(facturationRequest.getDateDebut(), facturationRequest.getDateFin())){
            throw new BadRequestAlertException("The billing period invalid", ENTITY_NAME, "facture.period.invalid");
        }
        if(Objects.isNull(facturationRequest.getSocieteId())){
            throw new BadRequestAlertException("The billing company is unset", ENTITY_NAME, "facture.societe.invalid");
        }
        if(Objects.isNull(facturationRequest.getClientId())){
            throw new BadRequestAlertException("The billing customer is unset", ENTITY_NAME, "facture.client.invalid");
        }
    }

    private Facture getCurrentInvoiceOrBuildNewOne(FacturationRequest facturationRequest, List<RecapitulatifFacturationClient> recapitulatifFacturationClient) {
        return factureRepository.findFactures(facturationRequest.getSocieteId(), facturationRequest.getClientId(), facturationRequest.getDateDebut(), facturationRequest.getDateFin(), facturationRequest.getChantier(), Pageable.unpaged())
            .stream().findFirst().orElseGet(() -> newFacture(facturationRequest, recapitulatifFacturationClient));
    }

    private Facture newFacture(FacturationRequest facturationRequest, List<RecapitulatifFacturationClient> recapitulatifFacturationClientList) {
        return Facture.newInstance(tvaProperties.getTva().get(facturationRequest.getTypeLivraison()))
            .withType(TypeFacture.AUTO)
            .withClient(facturationRequest.getClientId())
            .withMoisAnnee(facturationRequest.getDateDebut().getMonthValue(), facturationRequest.getDateDebut().getYear())
            .withNombreBonsLivraison(recapitulatifFacturationClientList.stream().mapToLong(RecapitulatifFacturationClient::getNombreBonsLivraison).sum())
            .withArticles(recapitulatifFacturationClientList)
            .withRemise(facturationRequest.getRemise())
            .validate();
    }

    @Transactional(readOnly = true)
    public Page<Facture> findFactures(FacturationRequest facturationRequest, Pageable pageable){
        return factureRepository.findFactures(facturationRequest.getSocieteId(), facturationRequest.getClientId(), facturationRequest.getDateDebut(), facturationRequest.getDateFin(), facturationRequest.getChantier(), pageable);
    }

    public ReglementResponse reglerFacture(ReglementRequest reglementRequest){
        Facture facture = factureRepository.getOne(reglementRequest.getFactureId());
        return ReglementResponse.of(reglementRepository.save(Reglement.of(facture).regler(reglementRequest.getMontant(), reglementRequest.getModeReglement(), reglementRequest.getReference())));
    }

    @Transactional(readOnly = true)
    public Page<Reglement> findReglements(FacturationRequest facturationRequest, Pageable pageable){
        return reglementRepository.findReglements(facturationRequest, pageable);
    }

    public ReglementEspeceResponse reglerEspece(ReglementEspeceRequest reglementRequest){
        if(!isValidPeriodForCashPayment(reglementRequest.getDateDebut(), reglementRequest.getDateFin())){
            throw new BadRequestAlertException("The cash payment period is invalid", ENTITY_NAME, "reglement.espece.period.invalid");
        }
        List<RecapitulatifFacturationClient> recapitulatifFacturationClientList = livraisonService.getRecapitulatifFacturationClient(RecapitulatifFacturationClientRequest.from(reglementRequest, false, false));
        if(CollectionUtils.isNotEmpty(recapitulatifFacturationClientList)){
            ReglementEspece reglementEspece = reglementEspeceRepository.save(ReglementEspece.of(recapitulatifFacturationClientList.stream().mapToDouble(RecapitulatifFacturationClient::getTotalPrixVente).sum()));
            livraisonService.markAsPayedCash(reglementRequest, reglementEspece.getId());
            return ReglementEspeceResponse.of(reglementEspece);
        }

        return  ReglementEspeceResponse.noPayment();
    }

    @Transactional(readOnly = true)
    public Page<ReglementEspece> findReglementsEspece(FacturationRequest facturationRequest, Pageable pageable){
        return reglementEspeceRepository.findReglementsEspece(facturationRequest, pageable);
    }

    private boolean isValidPeriodForCashPayment(LocalDate dateDebut, LocalDate dateFin) {
        return LocalDate.of(dateDebut.getYear(), dateDebut.getMonth(), 1).isEqual(LocalDate.of(dateFin.getYear(), dateFin.getMonth(), 1));
    }

    public void createFacture(CreateInvoiceRequest createInvoiceRequest){
        factureRepository.save(Facture.newInstance(tvaProperties.getTva().get(createInvoiceRequest.getTypeLivraison()))
            .withType(TypeFacture.MANUELLE)
            .withMoisAnnee(createInvoiceRequest.getMois(), createInvoiceRequest.getAnnee())
            .withNombreBonsLivraison(0)
            .withArticles(Arrays.asList(new RecapitulatifFacturationClient("", "", 1L, 1.0, createInvoiceRequest.getMontant())))
            .validate());
    }
}
