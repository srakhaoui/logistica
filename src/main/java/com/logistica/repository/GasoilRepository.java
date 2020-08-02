package com.logistica.repository;

import com.logistica.domain.Gasoil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gasoil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilRepository extends JpaRepository<Gasoil, Long>, JpaSpecificationExecutor<Gasoil> {

}
