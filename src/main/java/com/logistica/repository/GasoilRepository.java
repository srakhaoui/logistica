package com.logistica.repository;

import com.logistica.domain.Gasoil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gasoil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilRepository extends JpaRepository<Gasoil, Long>, JpaSpecificationExecutor<Gasoil>, GasoilRepositoryCustom {

    @Query("Select kilometrageFinal From Gasoil Where transporteur.matricule = :matricule")
    Integer getkilometrageFinalByMatricule(@Param("matricule") String matricule);
}
