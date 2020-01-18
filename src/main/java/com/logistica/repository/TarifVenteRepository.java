package com.logistica.repository;
import com.logistica.domain.TarifVente;
import com.logistica.domain.enumeration.Unite;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifVenteRepository extends JpaRepository<TarifVente, Long>, JpaSpecificationExecutor<TarifVente> {
	
	@Query("Select prix From TarifVente where client.id=:clientId and produit.id=:produitId and unite=:unite")
	Float findPrixByClientProduitAndUnit(@Param("clientId") Long clientId,@Param("produitId") Long produitId, @Param("unite") Unite unite);
}
