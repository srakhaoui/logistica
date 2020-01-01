package com.logistica.repository;
import com.logistica.domain.TarifAchat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TarifAchat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifAchatRepository extends JpaRepository<TarifAchat, Long>, JpaSpecificationExecutor<TarifAchat> {

}
