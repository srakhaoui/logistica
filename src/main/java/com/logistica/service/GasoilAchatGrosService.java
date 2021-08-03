package com.logistica.service;

import com.logistica.domain.GasoilAchatGros;
import com.logistica.repository.GasoilAchatGrosRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.util.Assert.isTrue;

/**
 * Service Implementation for managing {@link GasoilAchatGros}.
 */
@Service
@Transactional
public class GasoilAchatGrosService {

    private final Logger log = LoggerFactory.getLogger(GasoilAchatGrosService.class);

    private final GasoilAchatGrosRepository gasoilAchatGrosRepository;

    public GasoilAchatGrosService(GasoilAchatGrosRepository gasoilAchatGrosRepository) {
        this.gasoilAchatGrosRepository = gasoilAchatGrosRepository;
    }

    /**
     * Save a gasoilAchatGros.
     *
     * @param gasoilAchatGros the entity to save.
     * @return the persisted entity.
     */
    public GasoilAchatGros save(GasoilAchatGros gasoilAchatGros) {
        log.debug("Request to save GasoilAchatGros : {}", gasoilAchatGros);
        isTrue(StringUtils.isNotBlank(gasoilAchatGros.getNumeroBonReception()), "NumeroBonReception is missing");
        isTrue(StringUtils.isNotBlank(gasoilAchatGros.getFournisseur().getNom()), "Fournisseur is missing");
        isTrue(gasoilAchatGros.getDateReception() != null, "DateReception is missing");

        if (gasoilAchatGros.getDateReception().isAfter(LocalDate.now())) {
            throw new DateReceptionGasoilFutureException();
        }

        gasoilAchatGros.setDescription(descriptionFrom(gasoilAchatGros));
        return gasoilAchatGrosRepository.save(gasoilAchatGros);
    }

    private String descriptionFrom(GasoilAchatGros gasoilAchatGros) {
        return gasoilAchatGros.getNumeroBonReception() + gasoilAchatGros.getFournisseur().getNom() + gasoilAchatGros.getDateReception();
    }

    /**
     * Get all the gasoilAchatGros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GasoilAchatGros> findAll(Pageable pageable) {
        log.debug("Request to get all GasoilAchatGros");
        return gasoilAchatGrosRepository.findAll(pageable);
    }


    /**
     * Get one gasoilAchatGros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GasoilAchatGros> findOne(Long id) {
        log.debug("Request to get GasoilAchatGros : {}", id);
        return gasoilAchatGrosRepository.findById(id);
    }

    /**
     * Delete the gasoilAchatGros by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GasoilAchatGros : {}", id);
        gasoilAchatGrosRepository.deleteById(id);
    }
}
