package com.logistica.service;

import com.logistica.domain.Depot;
import com.logistica.domain.DepotAggregat;
import com.logistica.domain.enumeration.Unite;
import com.logistica.repository.DepotAggregatRepository;
import com.logistica.service.dto.RecapitulatifDepotAggregatStock;
import com.logistica.service.dto.RecapitulatifDepotAggregatStockRequest;
import com.logistica.service.dto.StockDepot;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotAgregatService {

    private final Logger log = LoggerFactory.getLogger(DepotAgregatService.class);

    private final DepotAggregatRepository depotAggregatRepository;
    private final LivraisonService livraisonService;
    private final AgregatTransfertService agregatTransfertService;

    public DepotAgregatService(DepotAggregatRepository depotAggregatRepository, LivraisonService livraisonService, AgregatTransfertService agregatTransfertService) {
        this.depotAggregatRepository = depotAggregatRepository;
        this.livraisonService = livraisonService;
        this.agregatTransfertService = agregatTransfertService;
    }

    @Transactional(readOnly = true)
    public List<RecapitulatifDepotAggregatStock> getStocks(RecapitulatifDepotAggregatStockRequest request) {
        Map<String, List<StockDepot>> achatsByDepotMap = livraisonService.getTotalAchatMarchandisesByDepotAndUnite(request).stream().collect(Collectors.groupingBy(StockDepot::getDepot));
        Map<String, List<StockDepot>> ventesByDepotMap = livraisonService.getTotalVenteMarchandisesByDepotAndUnite(request).stream().collect(Collectors.groupingBy(StockDepot::getDepot));
        Map<String, List<StockDepot>> transfertEntrantsByDepotMap = agregatTransfertService.getTotalTransfertEntrantsByDepotAndUnite().stream().collect(Collectors.groupingBy(StockDepot::getDepot));
        Map<String, List<StockDepot>> transfertSortantsByDepotMap = agregatTransfertService.getTotalTransfertSortantsByDepotAndUnite().stream().collect(Collectors.groupingBy(StockDepot::getDepot));

        return depotAggregatRepository.findAll()
            .stream()
            .map(depotAggregat ->
                new RecapitulatifDepotAggregatStock(depotAggregat.getNom(), depotAggregat.getStock(), depotAggregat.getUnite())
                    .achats(achatsByDepotMap.getOrDefault(depotAggregat.getNom(), new ArrayList<>()))
                    .ventes(ventesByDepotMap.getOrDefault(depotAggregat.getNom(), new ArrayList<>()))
                    .transfertsEntrants(transfertEntrantsByDepotMap.getOrDefault(depotAggregat.getNom(), new ArrayList<>()))
                    .transfertsSortants(transfertSortantsByDepotMap.getOrDefault(depotAggregat.getNom(), new ArrayList<>()))
                    .calculerStockParUnite())
            .collect(Collectors.toList());
    }

    public Optional<Float> getStock(DepotAggregat source, Unite unite) {
        return getStocks(new RecapitulatifDepotAggregatStockRequest())
            .stream()
            .filter(recapStock -> recapStock.getNom().equalsIgnoreCase(source.getNom()))
            .map(recapStock -> Optional.ofNullable(recapStock.getStockByUnite().get(unite)))
            .findAny()
            .get();
    }
}
