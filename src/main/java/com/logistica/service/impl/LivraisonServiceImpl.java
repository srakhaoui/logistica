package com.logistica.service.impl;

import com.logistica.service.CommissionTrajetUndefinedException;
import com.logistica.service.LivraisonService;
import com.logistica.service.PriceComputingException;
import com.logistica.service.TrajetService;
import com.logistica.service.tarif.Pricer;
import com.logistica.service.tarif.PricerFactory;
import com.logistica.service.tarif.TypePricer;
import com.logistica.domain.Livraison;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.repository.LivraisonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Livraison}.
 */
@Service
@Transactional
public class LivraisonServiceImpl implements LivraisonService {

    private final Logger log = LoggerFactory.getLogger(LivraisonServiceImpl.class);

    @Autowired
    private PricerFactory pricerFactory;
    
    @Autowired
    private TrajetService trajetService;
    
    private final LivraisonRepository livraisonRepository;

    public LivraisonServiceImpl(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    /**
     * Save a livraison.
     *
     * @param livraison the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Livraison save(Livraison livraison) {
    	calculerPrixTotaux(livraison);
    	calculerCommissionTotalTrajet(livraison);
        log.debug("Request to save Livraison : {}", livraison);
        return livraisonRepository.save(livraison);
    }

	private void calculerPrixTotaux(Livraison livraison) {
		Float prixTotalAchat = 0.0F;
    	Float prixTotalVente = null;
    	if(livraison.getType() == TypeLivraison.Marchandise) {
    		prixTotalAchat = getPrixTotalAchatMarchandise(livraison);
    		prixTotalVente = getPrixTotalVenteMarchandise(livraison);
    	}else if(livraison.getType() == TypeLivraison.Transport) {
    		prixTotalVente = getPrixTransport(livraison);
    	}
    	livraison.setPrixTotalAchat(prixTotalAchat);
		livraison.setPrixTotalVente(prixTotalVente);
	}

	private Float getPrixTotalAchatMarchandise(Livraison livraison) {
		Pricer achatPricer = pricerFactory.getPricer(TypePricer.Achat);
		Float prixAchat = achatPricer.price(livraison);
		Optional.ofNullable(prixAchat).orElseThrow(()-> new PriceComputingException(TypePricer.Achat));
		return livraison.getQuantiteAchetee() * achatPricer.price(livraison);
	}

	private Float getPrixTotalVenteMarchandise(Livraison livraison) {
		Pricer ventePricer = pricerFactory.getPricer(TypePricer.Vente);
		Float prixVente = ventePricer.price(livraison);
		Optional.ofNullable(prixVente).orElseThrow(()-> new PriceComputingException(TypePricer.Vente));
		return livraison.getQuantiteVendue() * prixVente;
	}
	
	private Float getPrixTransport(Livraison livraison) {
		Pricer pricer = pricerFactory.getPricer(TypePricer.Transport);
		Float prixTransport = pricer.price(livraison);
		Optional.ofNullable(prixTransport).orElseThrow(()-> new PriceComputingException(TypePricer.Transport));
		return prixTransport;
	}
	
	private void calculerCommissionTotalTrajet(Livraison livraison) {
		Assert.notNull(livraison.getTrajet().getDepart(), "Merci de renseigner le départ du trajet");
		Assert.notNull(livraison.getTrajet().getDestination(), "Merci de renseigner la destination du trajet");
		
		Float commissionTrajet = Optional.ofNullable(trajetService.getCommissionByTrajet(livraison.getTrajet().getDepart(), livraison.getTrajet().getDestination())).orElseThrow(CommissionTrajetUndefinedException::new);;
		Float reparationDivers = Optional.ofNullable(livraison.getReparationDivers()).orElse(0.0F);
		Float trax = Optional.ofNullable(livraison.getTrax()).orElse(0.0F);
		Float balance = Optional.ofNullable(livraison.getBalance()).orElse(0.0F);
		Float avance = Optional.ofNullable(livraison.getAvance()).orElse(0.0F);
		Float penaliteEse = Optional.ofNullable(livraison.getPenaliteEse()).orElse(0.0F);
		Float penaliteChfrs = Optional.ofNullable(livraison.getPenaliteChfrs()).orElse(0.0F);
		Float fraisEspece = Optional.ofNullable(livraison.getFraisEspece()).orElse(0.0F);
		Float retenu = Optional.ofNullable(livraison.getRetenu()).orElse(0.0F);
		
		Float totalCommission = commissionTrajet + reparationDivers + trax + balance - avance + penaliteEse - penaliteChfrs - fraisEspece - retenu;
		livraison.setTotalComission(totalCommission);
	}

    /**
     * Get all the livraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Livraison> findAll(Pageable pageable) {
        log.debug("Request to get all Livraisons");
        return livraisonRepository.findAll(pageable);
    }


    /**
     * Get one livraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Livraison> findOne(Long id) {
        log.debug("Request to get Livraison : {}", id);
        return livraisonRepository.findById(id);
    }

    /**
     * Delete the livraison by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Livraison : {}", id);
        livraisonRepository.deleteById(id);
    }
}
