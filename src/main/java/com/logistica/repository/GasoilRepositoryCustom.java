package com.logistica.repository;

import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GasoilRepositoryCustom {

    Page<RecapitulatifChargeGasoil> getRecapitulatifChargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable);

    List<ChargeGasoilParMois> getEvolutionChargeGasoilParMois(ChargeGasoilRequest chargeGasoilRequest);

    List<ChargeGasoilParMatricule> getRepartitionChargeGasoilParMatricule(ChargeGasoilRequest chargeGasoilRequest);

    List<LitrageParMois> getLitrageParMois(StatistiquesTauxConsommationRequest statistiquesTauxConsommationRequest);

    List<KilometrageParMois> getKilometrageParMois(StatistiquesTauxConsommationRequest statistiquesTauxConsommationRequest);

    List<TauxConsommationParMatricule> getTauxConsommationParMatricule(StatistiquesTauxConsommationRequest statistiquesTauxConsommationRequest);
}
