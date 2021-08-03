package com.logistica.service;

import com.logistica.domain.ClientGrossiste;
import com.logistica.repository.ClientGrossisteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientGrossiste}.
 */
@Service
@Transactional
public class ClientGrossisteService {

    private final Logger log = LoggerFactory.getLogger(ClientGrossisteService.class);

    private final ClientGrossisteRepository clientGrossisteRepository;

    public ClientGrossisteService(ClientGrossisteRepository clientGrossisteRepository) {
        this.clientGrossisteRepository = clientGrossisteRepository;
    }

    /**
     * Save a clientGrossiste.
     *
     * @param clientGrossiste the entity to save.
     * @return the persisted entity.
     */
    public ClientGrossiste save(ClientGrossiste clientGrossiste) {
        log.debug("Request to save ClientGrossiste : {}", clientGrossiste);
        return clientGrossisteRepository.save(clientGrossiste);
    }

    /**
     * Get all the clientGrossistes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientGrossiste> findAll(Pageable pageable) {
        log.debug("Request to get all ClientGrossistes");
        return clientGrossisteRepository.findAll(pageable);
    }


    /**
     * Get one clientGrossiste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientGrossiste> findOne(Long id) {
        log.debug("Request to get ClientGrossiste : {}", id);
        return clientGrossisteRepository.findById(id);
    }

    /**
     * Delete the clientGrossiste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientGrossiste : {}", id);
        clientGrossisteRepository.deleteById(id);
    }
}
