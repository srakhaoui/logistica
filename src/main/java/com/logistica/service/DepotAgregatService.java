package com.logistica.service;

import com.logistica.domain.Depot;
import com.logistica.domain.DepotAggregat;
import com.logistica.repository.DepotAggregatRepository;
import com.logistica.service.dto.RecapitulatifDepotAggregatStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link Depot}.
 */
@Service
@Transactional
public class DepotAgregatService {

    private final Logger log = LoggerFactory.getLogger(DepotAgregatService.class);

    private final DepotAggregatRepository depotAggregatRepository;

    public DepotAgregatService(DepotAggregatRepository depotAggregatRepository) {
        this.depotAggregatRepository = depotAggregatRepository;
    }

    @Transactional(readOnly = true)
    public List<RecapitulatifDepotAggregatStock> getStocks() {
        List<DepotAggregat> depots = depotAggregatRepository.findAll();
        return null;
    }
}
