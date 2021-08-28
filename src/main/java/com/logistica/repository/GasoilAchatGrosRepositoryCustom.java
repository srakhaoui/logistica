package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifGasoilAchatGros;
import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GasoilAchatGrosRepositoryCustom {

    Page<RecapitulatifGasoilAchatGros> getRecapitulatifGasoilAchatGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable);
}
