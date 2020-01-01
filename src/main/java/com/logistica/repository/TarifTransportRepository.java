package com.logistica.repository;
import com.logistica.domain.TarifTransport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifTransportRepository extends JpaRepository<TarifTransport, Long>, JpaSpecificationExecutor<TarifTransport> {

}
