package com.logistica.repository;

import com.logistica.domain.Facture;
import com.logistica.domain.enumeration.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureRepository extends JpaRepository<Facture, Long>, JpaSpecificationExecutor<Facture>, FactureRepositoryCustom {

    @Query("Update Facture f set f.status = :status where f.id=:factureId")
    void updateStatusFacture(Long factureId, InvoiceStatus status);
}
