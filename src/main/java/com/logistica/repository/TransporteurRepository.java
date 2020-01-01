package com.logistica.repository;
import com.logistica.domain.Transporteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transporteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransporteurRepository extends JpaRepository<Transporteur, Long>, JpaSpecificationExecutor<Transporteur> {

}
