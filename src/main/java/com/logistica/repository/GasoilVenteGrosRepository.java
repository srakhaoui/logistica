package com.logistica.repository;

import com.logistica.domain.GasoilVenteGros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GasoilVenteGros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilVenteGrosRepository extends JpaRepository<GasoilVenteGros, Long>, GasoilVenteGrosRepositoryCustom {

    @Query("Select sum(quantite) From GasoilVenteGros g where g.achatGasoil.numeroBonReception = :numeroBonReception")
    Float getQuantiteVendueParNumeroBonReception(@Param("numeroBonReception") String numeroBonReception);
}
