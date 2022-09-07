package com.logistica.repository;

import com.logistica.domain.AgregatTransfert;
import com.logistica.service.dto.StockDepot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the AgregatTransfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgregatTransfertRepository extends JpaRepository<AgregatTransfert, Long> {

    @Query("Select new com.logistica.service.dto.StockDepot(t.destination.nom, t.unite, sum(t.quantite)) From AgregatTransfert t Group By t.destination.nom, t.unite")
    List<StockDepot> getTotalTransfertEntrantsByDepotAndUnite();

    @Query("Select new com.logistica.service.dto.StockDepot(t.source.nom, t.unite, sum(t.quantite)) From AgregatTransfert t Group By t.source.nom, t.unite")
    List<StockDepot> getTotalTransfertSortantsByDepotAndUnite();

}
