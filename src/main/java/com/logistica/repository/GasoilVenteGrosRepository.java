package com.logistica.repository;

import com.logistica.domain.GasoilVenteGros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GasoilVenteGros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilVenteGrosRepository extends JpaRepository<GasoilVenteGros, Long> {

}
