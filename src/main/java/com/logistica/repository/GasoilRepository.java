package com.logistica.repository;

import com.logistica.domain.Gasoil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


/**
 * Spring Data  repository for the Gasoil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GasoilRepository extends JpaRepository<Gasoil, Long>, JpaSpecificationExecutor<Gasoil>, GasoilRepositoryCustom {

    @Query("Select max(kilometrageFinal) From Gasoil Where transporteur.matricule = :matricule And dateBonGasoil = (Select max(g.dateBonGasoil) From Gasoil g where g.transporteur.matricule = transporteur.matricule)")
    Integer getkilometrageFinalByMatricule(@Param("matricule") String matricule);

    @Query("Select max(prixDuLitre) From Gasoil Where audit.createdOn = (Select max(audit.createdOn) From Gasoil Where prixDuLitre is not null)")
    Float getLastPrixGasoil();

    @Query("Select id From Gasoil Where transporteur.matricule = :matricule And dateBonGasoil = :dateBonGasoil")
    Long findByMatriculeAndDateBonGasoil(@Param("matricule") String matricule, @Param("dateBonGasoil") LocalDateTime mailiIntegrationTs);

}
