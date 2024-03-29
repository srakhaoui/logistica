package com.logistica.repository;

import com.logistica.domain.ClientGrossiste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClientGrossiste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientGrossisteRepository extends JpaRepository<ClientGrossiste, Long>, JpaSpecificationExecutor<ClientGrossiste> {

}
