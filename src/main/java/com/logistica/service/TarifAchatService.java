package com.logistica.service;

import com.logistica.domain.TarifAchat;
import com.logistica.domain.enumeration.Unite;
import com.logistica.repository.TarifAchatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifAchat}.
 */
@Service
@Transactional
public class TarifAchatService {

    private final Logger log = LoggerFactory.getLogger(TarifAchatService.class);

    private final TarifAchatRepository tarifAchatRepository;

    public TarifAchatService(TarifAchatRepository tarifAchatRepository) {
        this.tarifAchatRepository = tarifAchatRepository;
    }

    /**
     * Save a tarifAchat.
     *
     * @param tarifAchat the entity to save.
     * @return the persisted entity.
     */
    public TarifAchat save(TarifAchat tarifAchat) {
        log.debug("Request to save TarifAchat : {}", tarifAchat);
        if (tarifAchat.getId() == null) {
            boolean isAlreadyExists = isAlreadyExists(tarifAchat);
            if (isAlreadyExists) {
                throw new TarifAlreadyExistsException("Tarif d'achat déjà existant");
            }
        }
        return tarifAchatRepository.save(tarifAchat);
    }

    private boolean isAlreadyExists(TarifAchat tarifAchat) {
        TarifAchat tarifAchatExample = new TarifAchat();
        tarifAchatExample.setFournisseur(tarifAchat.getFournisseur());
        tarifAchatExample.setProduit(tarifAchat.getProduit());
        tarifAchatExample.setUnite(tarifAchat.getUnite());
        return tarifAchatRepository.exists(Example.of(tarifAchatExample));
    }

    /**
     * Get all the tarifAchats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifAchat> findAll(Pageable pageable) {
        log.debug("Request to get all TarifAchats");
        return tarifAchatRepository.findAll(pageable);
    }


    /**
     * Get one tarifAchat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarifAchat> findOne(Long id) {
        log.debug("Request to get TarifAchat : {}", id);
        return tarifAchatRepository.findById(id);
    }

    /**
     * Delete the tarifAchat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarifAchat : {}", id);
        tarifAchatRepository.deleteById(id);
    }

    public Float findPrixByFournisseurProduitAndUnite(Long fournisseurId, Long produitId, Unite unite) {
    	return tarifAchatRepository.findPrixByFournisseurProduitAndUnite(fournisseurId, produitId, unite);
    }
}
