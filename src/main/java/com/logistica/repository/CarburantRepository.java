package com.logistica.repository;

import com.logistica.domain.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Carburant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarburantRepository extends JpaRepository<Carburant, Long>, JpaSpecificationExecutor<Carburant> {

}
