package com.logistica.service;

import com.logistica.domain.FournisseurGrossiste;
import com.logistica.repository.FournisseurGrossisteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FournisseurGrossiste}.
 */
@Service
@Transactional
public class FournisseurGrossisteService {

    private final Logger log = LoggerFactory.getLogger(FournisseurGrossisteService.class);

    private final FournisseurGrossisteRepository fournisseurGrossisteRepository;

    public FournisseurGrossisteService(FournisseurGrossisteRepository fournisseurGrossisteRepository) {
        this.fournisseurGrossisteRepository = fournisseurGrossisteRepository;
    }

    /**
     * Save a fournisseurGrossiste.
     *
     * @param fournisseurGrossiste the entity to save.
     * @return the persisted entity.
     */
    public FournisseurGrossiste save(FournisseurGrossiste fournisseurGrossiste) {
        log.debug("Request to save FournisseurGrossiste : {}", fournisseurGrossiste);
        return fournisseurGrossisteRepository.save(fournisseurGrossiste);
    }

    /**
     * Get all the fournisseurGrossistes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FournisseurGrossiste> findAll(Pageable pageable) {
        log.debug("Request to get all FournisseurGrossistes");
        return fournisseurGrossisteRepository.findAll(pageable);
    }


    /**
     * Get one fournisseurGrossiste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FournisseurGrossiste> findOne(Long id) {
        log.debug("Request to get FournisseurGrossiste : {}", id);
        return fournisseurGrossisteRepository.findById(id);
    }

    /**
     * Delete the fournisseurGrossiste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FournisseurGrossiste : {}", id);
        fournisseurGrossisteRepository.deleteById(id);
    }
}
