package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifChargeGasoil;
import com.logistica.service.dto.RecapitulatifChargeGasoilRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GasoilRepositoryCustom {

    Page<RecapitulatifChargeGasoil> getRecapitulatifChargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable);
}
