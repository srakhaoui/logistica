package com.logistica.service;

import com.logistica.domain.TarifVente;
import com.logistica.domain.enumeration.Unite;
import com.logistica.repository.TarifVenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifVente}.
 */
@Service
@Transactional
public class TarifVenteService {

    private final Logger log = LoggerFactory.getLogger(TarifVenteService.class);

    private final TarifVenteRepository tarifVenteRepository;

    public TarifVenteService(TarifVenteRepository tarifVenteRepository) {
        this.tarifVenteRepository = tarifVenteRepository;
    }

    /**
     * Save a tarifVente.
     *
     * @param tarifVente the entity to save.
     * @return the persisted entity.
     */
    public TarifVente save(TarifVente tarifVente) {
        log.debug("Request to save TarifVente : {}", tarifVente);
        if (tarifVente.getId() == null) {
            boolean isAlreadyExists = isAlreadyExists(tarifVente);
            if (isAlreadyExists) {
                throw new TarifAlreadyExistsException("Tarif de vente déjà existant");
            }
        }
        return tarifVenteRepository.save(tarifVente);
    }

    private boolean isAlreadyExists(TarifVente tarifVente) {
        TarifVente tarifVenteExample = new TarifVente();
        tarifVenteExample.setClient(tarifVente.getClient());
        tarifVenteExample.setProduit(tarifVente.getProduit());
        tarifVenteExample.setUnite(tarifVente.getUnite());
        return tarifVenteRepository.exists(Example.of(tarifVenteExample));
    }

    /**
     * Get all the tarifVentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifVente> findAll(Pageable pageable) {
        log.debug("Request to get all TarifVentes");
        return tarifVenteRepository.findAll(pageable);
    }


    /**
     * Get one tarifVente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarifVente> findOne(Long id) {
        log.debug("Request to get TarifVente : {}", id);
        return tarifVenteRepository.findById(id);
    }

    /**
     * Delete the tarifVente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarifVente : {}", id);
        tarifVenteRepository.deleteById(id);
    }

    public Float findPrixByClientProduitAndUnite(Long clientId, Long produitId, Unite unite) {
    	return tarifVenteRepository.findPrixByClientProduitAndUnit(clientId, produitId, unite);
    }
}
