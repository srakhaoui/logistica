package com.logistica.repository;
import com.logistica.domain.TarifTransport;
import com.logistica.domain.enumeration.Unite;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifTransportRepository extends JpaRepository<TarifTransport, Long>, JpaSpecificationExecutor<TarifTransport> {

	@Query("Select prix From TarifTransport where client.id=:clientId and produit.id=:produitId and unite=:unite and trajet.id=:trajetId")
	Float findPrixByClientProduitUniteAndTrajet(@Param("clientId") Long clientId,@Param("produitId") Long produitId, @Param("unite") Unite unite, @Param("trajetId") Long trajetId);
}
