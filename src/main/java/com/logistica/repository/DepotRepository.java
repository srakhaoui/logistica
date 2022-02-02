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

    @Query("Select sum(t.quantite) From GasoilTransfert t Where t.destination.nom = :depotName and t.destination.consommationInterne = :consommationInterne")
    Float getEntreesTransfertByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(alim.quantity) From GasoilAchatGros alim Where alim.depot.nom = :depotName and alim.depot.consommationInterne = :consommationInterne")
    Float getEntreesAchatByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(t.quantite) From GasoilTransfert t Where t.source.nom = :depotName and t.source.consommationInterne = :consommationInterne")
    Float getSortieTransfertByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(conso.quantite) From GasoilVenteGros conso Where conso.achatGasoil.depot.nom = :depotName and conso.achatGasoil.depot.consommationInterne = :consommationInterne")
    Float getSortieVenteByDepot(@Param("depotName") String depotName, @Param("consommationInterne") Boolean consommationInterne);

    @Query("Select sum(consoIntern.quantiteEnLitre)  From Gasoil consoIntern  Where consoIntern.depot.nom=:depotName and consoIntern.depot.consommationInterne = true")
    Float getConsommationInterneByDepotInterne(@Param("depotName") String depotName);

    List<Depot> findByNom(String depot);
}
