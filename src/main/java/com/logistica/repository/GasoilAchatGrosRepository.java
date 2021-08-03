package com.logistica.repository;

import com.logistica.domain.GasoilAchatGros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GasoilAchatGros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilAchatGrosRepository extends JpaRepository<GasoilAchatGros, Long>, JpaSpecificationExecutor<GasoilAchatGros> {

}
