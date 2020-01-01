package com.logistica.service;

import com.logistica.domain.TarifAchat;
import com.logistica.repository.TarifAchatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return tarifAchatRepository.save(tarifAchat);
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
}
