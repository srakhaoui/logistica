package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.service.dto.IRecapitulatifChauffeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Livraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>, JpaSpecificationExecutor<Livraison>, LivraisonRepositoryCustom {

    @Query("Select l.transporteur.id as id, l.transporteur.prenom as prenomChauffeur, l.transporteur.nom as nomChauffeur, l.trajet.description as description, count(l.trajet.id) as nombreTrajets, sum(l.trajet.commission) as commissionTrajet, sum(l.reparationDivers) as reparationDivers, sum(l.trax) as trax, sum(l.balance) as balance, sum(l.avance) as avance, sum(l.penaliteEse) as penaliteEse, sum(l.penaliteChfrs) as penaliteChfrs, sum(l.fraisEspece) as fraisEspece, sum(l.retenu) as retenu, sum(l.totalComission) as totalComission From Livraison l where l.dateBonCaisse >= :dateDebut And l.dateBonCaisse <= :dateFin Group By l.transporteur.prenom, l.transporteur.nom, l.trajet.description")
    Page<IRecapitulatifChauffeur> getRecapitulatifChauffeur(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin, Pageable pageable);

    @Query("Select l.transporteur.id as id, l.transporteur.prenom as prenomChauffeur, l.transporteur.nom as nomChauffeur, l.trajet.description as description, count(l.trajet.id) as nombreTrajets, sum(l.trajet.commission) as commissionTrajet, sum(l.reparationDivers) as reparationDivers, sum(l.trax) as trax, sum(l.balance) as balance, sum(l.avance) as avance, sum(l.penaliteEse) as penaliteEse, sum(l.penaliteChfrs) as penaliteChfrs, sum(l.fraisEspece) as fraisEspece, sum(l.retenu) as retenu, sum(l.totalComission) as totalComission From Livraison l where l.dateBonCaisse >= :dateDebut And l.dateBonCaisse <= :dateFin And l.transporteur.id = :idTransporteur Group By l.transporteur.prenom, l.transporteur.nom, l.trajet.description")
    Page<IRecapitulatifChauffeur> getRecapitulatifChauffeur(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin, @Param("idTransporteur") Long idTransporteur, Pageable pageable);

    @Query("Select sum(l.prixTotalVente) from Livraison l where l.societeFacturation.id = :societeFacturationId and l.dateBonLivraison >= :dateDebut and l.dateBonLivraison <= :dateFin")
    Double getTotalPrixVenteBySocieteFacturation(@Param("societeFacturationId") Long societeFacturationId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("Select sum(l.totalComission) from Livraison l where l.transporteur.id = :transporteurId and l.dateBonCaisse >= :dateDebut and l.dateBonCaisse <= :dateFin")
    Double getTotalCommissionByChauffeur(@Param("transporteurId") Long transporteurId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("Select distinct l.chantier From Livraison l Where l.chantier is not null And l.client.id = :clientId And l.dateBonLivraison >= :dateDebut and l.dateBonLivraison <= :dateFin")
    List<String> getChantiersByClient(@Param("clientId") Long clientId, @Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}
