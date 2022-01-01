package com.logistica.service;

import com.logistica.domain.Depot;
import com.logistica.repository.DepotRepository;
import com.logistica.service.dto.RecapitulatifStock;
import com.logistica.service.dto.RecapitulatifStockRequest;
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
    public List<RecapitulatifStock> getStocks(RecapitulatifStockRequest recapitulatifStockRequest) {
        List<Depot> depots = Optional.ofNullable(recapitulatifStockRequest.getDepot()).map(depotName -> depotRepository.findByNom(depotName)).orElseGet(() -> depotRepository.findAll());
        return depots.stream().map(depot -> {
            if (depot.isConsommationInterne()) {
                return new RecapitulatifStock(depot.getNom(), depot.isConsommationInterne(), depot.getStock(), getEntreesAchat(depot), getEntreesVente(depot), getSorties(depot), getConsommationInterne(depot));
            } else {
                return new RecapitulatifStock(depot.getNom(), depot.isConsommationInterne(), depot.getStock(), getEntreesAchat(depot), getEntreesVente(depot), getSorties(depot));
            }
        }).collect(Collectors.toList());
    }

    private Float getConsommationInterne(Depot depot) {
        return Optional.ofNullable(depotRepository.getConsommationInterneByDepotInterne(depot.getNom())).orElse(0.0F);
    }

    private Float getEntreesAchat(Depot depot) {
        return Optional.ofNullable(depotRepository.getEntreesAchatByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }

    private Float getEntreesVente(Depot depot) {
        return Optional.ofNullable(depotRepository.getEntreesVenteByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }

    private Float getSorties(Depot depot) {
        return Optional.ofNullable(depotRepository.getSortieByDepot(depot.getNom(), depot.isConsommationInterne())).orElse(0.0F);
    }
}
