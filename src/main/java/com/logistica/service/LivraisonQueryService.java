package com.logistica.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.logistica.domain.Livraison;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.LivraisonRepository;
import com.logistica.service.dto.LivraisonCriteria;

/**
 * Service for executing complex queries for {@link Livraison} entities in the database.
 * The main input is a {@link LivraisonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Livraison} or a {@link Page} of {@link Livraison} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LivraisonQueryService extends QueryService<Livraison> {

    private final Logger log = LoggerFactory.getLogger(LivraisonQueryService.class);

    private final LivraisonRepository livraisonRepository;

    public LivraisonQueryService(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    /**
     * Return a {@link List} of {@link Livraison} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Livraison> findByCriteria(LivraisonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Livraison} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Livraison> findByCriteria(LivraisonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LivraisonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Livraison> specification = createSpecification(criteria);
        return livraisonRepository.count(specification);
    }

    /**
     * Function to convert {@link LivraisonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Livraison> createSpecification(LivraisonCriteria criteria) {
        Specification<Livraison> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Livraison_.id));
            }
            if (criteria.getDateBonCommande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateBonCommande(), Livraison_.dateBonCommande));
            }
            if (criteria.getNumeroBonCommande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroBonCommande(), Livraison_.numeroBonCommande));
            }
            if (criteria.getNumeroBonLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroBonLivraison(), Livraison_.numeroBonLivraison));
            }
            if (criteria.getDateBonLivraison() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateBonLivraison(), Livraison_.dateBonLivraison));
            }
            if (criteria.getNumeroBonFournisseur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroBonFournisseur(), Livraison_.numeroBonFournisseur));
            }
            if (criteria.getQuantiteVendue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteVendue(), Livraison_.quantiteVendue));
            }
            if (criteria.getUniteVente() != null) {
                specification = specification.and(buildSpecification(criteria.getUniteVente(), Livraison_.uniteVente));
            }
            if (criteria.getPrixTotalVente() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixTotalVente(), Livraison_.prixTotalVente));
            }
            if (criteria.getQuantiteAchetee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteAchetee(), Livraison_.quantiteAchetee));
            }
            if (criteria.getUniteAchat() != null) {
                specification = specification.and(buildSpecification(criteria.getUniteAchat(), Livraison_.uniteAchat));
            }
            if (criteria.getPrixTotalAchat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixTotalAchat(), Livraison_.prixTotalAchat));
            }
            if (criteria.getQuantiteConvertie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteConvertie(), Livraison_.quantiteConvertie));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Livraison_.type));
            }
            if (criteria.getFacture() != null) {
                specification = specification.and(buildSpecification(criteria.getFacture(), Livraison_.facture));
            }
            if (criteria.getDateBonCaisse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateBonCaisse(), Livraison_.dateBonCaisse));
            }
            if (criteria.getReparationDivers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReparationDivers(), Livraison_.reparationDivers));
            }
            if (criteria.getTrax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrax(), Livraison_.trax));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), Livraison_.balance));
            }
            if (criteria.getAvance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvance(), Livraison_.avance));
            }
            if (criteria.getAutoroute() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutoroute(), Livraison_.autoroute));
            }
            if (criteria.getDernierEtat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDernierEtat(), Livraison_.dernierEtat));
            }
            if (criteria.getPenaliteEse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPenaliteEse(), Livraison_.penaliteEse));
            }
            if (criteria.getPenaliteChfrs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPenaliteChfrs(), Livraison_.penaliteChfrs));
            }
            if (criteria.getFraisEspece() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFraisEspece(), Livraison_.fraisEspece));
            }
            if (criteria.getRetenu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRetenu(), Livraison_.retenu));
            }
            if (criteria.getTotalComission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalComission(), Livraison_.totalComission));
            }
            if (criteria.getFournisseurId() != null) {
                specification = specification.and(buildSpecification(criteria.getFournisseurId(),
                    root -> root.join(Livraison_.fournisseur, JoinType.LEFT).get(Fournisseur_.id)));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientId(),
                    root -> root.join(Livraison_.client, JoinType.LEFT).get(Client_.id)));
            }
            if (criteria.getTransporteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporteurId(),
                    root -> root.join(Livraison_.transporteur, JoinType.LEFT).get(Transporteur_.id)));
            }
            if (criteria.getTrajetId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrajetId(),
                    root -> root.join(Livraison_.trajet, JoinType.LEFT).get(Trajet_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(Livraison_.produit, JoinType.LEFT).get(Produit_.id)));
            }
            if (criteria.getSocieteFacturationId() != null) {
                specification = specification.and(buildSpecification(criteria.getSocieteFacturationId(),
                    root -> root.join(Livraison_.societeFacturation, JoinType.LEFT).get(Societe_.id)));
            }
        }
        return specification;
    }
}
