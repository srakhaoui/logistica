package com.logistica.repository;
import com.logistica.domain.Livraison;
import com.logistica.domain.RecapitulatifAchat;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Livraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long>, JpaSpecificationExecutor<Livraison>, LivraisonRepositoryCustom {
}
