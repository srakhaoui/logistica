package com.logistica.repository;

import com.logistica.domain.Transporteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transporteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporteurRepository extends JpaRepository<Transporteur, Long>, JpaSpecificationExecutor<Transporteur> {

    @Query("From Transporteur Where matricule = :matricule and createdOn = (Select max(t1.createdOn) From Transporteur t1 where t1.matricule = :matricule)")
    Transporteur findByMatricule(@Param("matricule") String matricule);

}
