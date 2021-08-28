package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
import com.logistica.service.dto.RecapitulatifGasoilVenteGros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GasoilVenteGrosRepositoryCustom {

    Page<RecapitulatifGasoilVenteGros> getRecapitulatifGasoilVenteGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable);
}
