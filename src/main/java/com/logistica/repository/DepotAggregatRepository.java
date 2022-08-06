package com.logistica.repository;

import com.logistica.domain.DepotAggregat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepotAggregat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepotAggregatRepository extends JpaRepository<DepotAggregat, Long> {

}
