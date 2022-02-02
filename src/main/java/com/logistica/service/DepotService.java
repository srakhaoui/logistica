package com.logistica.service;

import com.logistica.domain.Depot;
import com.logistica.repository.DepotRepository;
import com.logistica.service.dto.RecapitulatifStock;
import com.logistica.service.dto.RecapitulatifStockRequest;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotService {

    private final Logger log = LoggerFactory.getLogger(DepotService.class);

    private final DepotRepository depotRepository;

    public DepotService(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    /**
     * Save a depot.
     *
     * @param depot the entity to save.
     * @return the persisted entity.
     */
    public Depot save(Depot depot) {
        log.debug("Request to save Depot : {}", depot);
        return depotRepository.save(depot);
    }

    /**
     * Get all the depots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Depot> findAll(Pageable pageable) {
        log.debug("Request to get all Depots");
        return depotRepository.findAll(pageable);
    }


    /**
     * Get one depot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Depot> findOne(Long id) {
        log.debug("Request to get Depot : {}", id);
        return depotRepository.findById(id);
    }

    /**
     * Delete the depot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Depot : {}", id);
        depotRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public double getStock(Depot depot) {
        return getStocks(new RecapitulatifStockRequest(depot.getNom()))
            .stream()
            .findFirst()
            .map(RecapitulatifStock::getStock)
            .orElseThrow(() -> new BadRequestAlertException(String.format("Depot introuvable %s", depot.getNom()), "Depot", "error.depot.introuvable"));
    }

    @Transactional(readOnly = true)
    public List<RecapitulatifStock> getStocks(RecapitulatifStockRequest recapitulatifStockRequest) {
        List<Depot> depots = Optional.ofNullable(recapitulatifStockRequest.getDepot()).map(depotName -> depotRepository.findByNom(depotName)).orElseGet(() -> depotRepository.findAll());
        return depots.stream().map(depot -> {
            RecapitulatifStock recapitulatifStock = new RecapitulatifStock();
            recapitulatifStock
                .depot(depot.getNom())
                .depotReserve(depot.isConsommationInterne())
                .stockInitial(depot.getStock())
                .entreesAchat(getEntreesAchat(depot))
                .entreesTransfert(getEntreesTransfert(depot))
                .sorties(getSortiesVente(depot))
                .sortiesTransfert(getSortiesTransfert(depot));
            if (depot.isConsommationInterne()) {
                recapitulatifStock.consommationInterne(getConsommationInterne(depot));
            }
            return recapitulatifStock.calculerStock();
        }).collect(Collectors.toList());
    }

    private Float getConsommationInterne(Depot depot) {
        return Optional.ofNullable(depotRepository.getConsommationInterneByDepotInterne(depot.getNom())).orElse(0.0F);
    }

    private Float getEntreesAchat(Depot depot) {
        return Optional.ofNullable(depotRepository.getEntreesAchatByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }

    private Float getEntreesTransfert(Depot depot) {
        return Optional.ofNullable(depotRepository.getEntreesTransfertByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }

    private Float getSortiesVente(Depot depot) {
        return Optional.ofNullable(depotRepository.getSortieVenteByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }

    private Float getSortiesTransfert(Depot depot) {
        return Optional.ofNullable(depotRepository.getSortieTransfertByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }
}
