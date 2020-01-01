package com.logistica.repository;
import com.logistica.domain.Societe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Societe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocieteRepository extends JpaRepository<Societe, Long>, JpaSpecificationExecutor<Societe> {

}
