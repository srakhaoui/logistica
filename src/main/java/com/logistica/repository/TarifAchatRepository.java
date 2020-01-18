package com.logistica.repository;
import com.logistica.domain.TarifAchat;
import com.logistica.domain.enumeration.Unite;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifAchatRepository extends JpaRepository<TarifAchat, Long>, JpaSpecificationExecutor<TarifAchat> {

	@Query("Select prix From TarifAchat where fournisseur.id=:fournisseurId and produit.id=:produitId and unite=:unite")
	Float findPrixByFournisseurProduitAndUnite(@Param("fournisseurId") Long fournisseurId, @Param("produitId") Long produitId,@Param("unite") Unite unite);
}
