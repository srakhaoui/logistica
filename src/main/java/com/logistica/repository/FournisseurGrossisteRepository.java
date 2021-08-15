package com.logistica.repository;

import com.logistica.domain.FournisseurGrossiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FournisseurGrossiste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FournisseurGrossisteRepository extends JpaRepository<FournisseurGrossiste, Long>, JpaSpecificationExecutor<FournisseurGrossiste> {

}
