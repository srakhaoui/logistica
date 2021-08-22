package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifGasoilVenteGros;
import com.logistica.service.dto.RecapitulatifGasoilVenteGrosRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GasoilVenteGrosRepositoryCustom {

    Page<RecapitulatifGasoilVenteGros> getRecapitulatifGasoilVenteGros(RecapitulatifGasoilVenteGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable);
}
