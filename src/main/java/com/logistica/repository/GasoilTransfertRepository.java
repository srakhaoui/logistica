package com.logistica.repository;

import com.logistica.domain.GasoilTransfert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GasoilTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilTransfertRepository extends JpaRepository<GasoilTransfert, Long> {

}
