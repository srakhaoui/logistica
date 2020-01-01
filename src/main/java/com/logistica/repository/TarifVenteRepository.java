package com.logistica.repository;
import com.logistica.domain.TarifVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifVenteRepository extends JpaRepository<TarifVente, Long>, JpaSpecificationExecutor<TarifVente> {

}
