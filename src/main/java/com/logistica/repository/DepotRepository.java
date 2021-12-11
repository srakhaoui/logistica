package com.logistica.repository;

import com.logistica.domain.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Depot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepotRepository extends JpaRepository<Depot, Long>, JpaSpecificationExecutor<Depot> {

    @Query("Select sum(alim.quantite) From Depot d, GasoilVenteGros alim Where d.nom=:depotName and d.consommationInterne = :consommationInterne And d.alimentation = alim.client ")
    Float getEntreesVenteByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(alim.quantity) From Depot d, GasoilAchatGros alim Where d.nom=:depotName and d.consommationInterne = :consommationInterne And d.consommation = alim.fournisseurGrossiste ")
    Float getEntreesAchatByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);


    @Query("Select sum(conso.quantite) From Depot d, GasoilVenteGros conso Where d.nom=:depotName and d.consommationInterne = :consommationInterne And d.consommation = conso.achatGasoil.fournisseurGrossiste ")
    Float getSortieByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(consoIntern.quantiteEnLitre)  From Depot d, Gasoil consoIntern  Where d.nom=:depotName and d.consommationInterne = true And d = consoIntern.depot ")
    Float getConsommationInterneByDepotInterne(@Param("depotName") String depotName);

    List<Depot> findByNom(String depot);
}
