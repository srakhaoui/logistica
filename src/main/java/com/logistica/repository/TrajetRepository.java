package com.logistica.repository;
import com.logistica.domain.Trajet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Trajet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long>, JpaSpecificationExecutor<Trajet> {

}
