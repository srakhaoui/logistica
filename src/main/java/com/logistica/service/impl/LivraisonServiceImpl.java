package com.logistica.service.impl;

import com.logistica.domain.Bon;
import com.logistica.domain.Livraison;
import com.logistica.domain.enumeration.TypeBon;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.repository.LivraisonRepository;
import com.logistica.service.*;
import com.logistica.service.dto.*;
import com.logistica.service.tarif.Pricer;
import com.logistica.service.tarif.PricerFactory;
import com.logistica.service.tarif.TypePricer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
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
        validateLivraisonDates(livraison);
        calculerPrixTotaux(livraison);
        calculerCommissionTotalTrajet(livraison);
        log.debug("Request to save Livraison : {}", livraison);
        return livraisonRepository.save(livraison);
    }

    private void validateLivraisonDates(Livraison livraison) {
        final LocalDate dateBonCaisse = Optional.ofNullable(livraison.getDateBonCaisse()).orElse(LocalDate.now());
        final LocalDate dateLivraison = Optional.ofNullable(livraison.getDateBonLivraison()).orElseThrow(() -> new IllegalArgumentException("La date de livraison est obligatoire"));
        if (dateBonCaisse.isAfter(LocalDate.now())) {
            throw new DateBonCaisseFutureException();
        }
        if (dateLivraison.getYear() != LocalDate.now().getYear()) {
            throw new ExerciceComptableLivraisonInvalideException();
        }
        if (dateLivraison.isAfter(LocalDate.now())) {
            throw new DateLivraisonFutureException();
        }
        if (dateBonCaisse.isBefore(dateLivraison)) {
            throw new DateBonCaisseAnterieureDateLivraisonException();
        }
        Optional.ofNullable(livraison.getDateBonCommande()).ifPresent(dateBonCommande -> {
            if (dateBonCommande.getYear() != LocalDate.now().getYear()) {
                throw new ExerciceComptableCommandeInvalideException();
            }
            if (dateBonCaisse.isBefore(dateBonCommande)) {
                throw new DateBonCaisseAnterieureDateCommandeException();
            }
            if (dateLivraison.isBefore(dateBonCommande)) {
                throw new DateLivraisonAnterieureDateCommandeException();
            }
        });
    }

    private void calculerPrixTotaux(Livraison livraison) {
        Float prixTotalAchat = 0.0F;
        Float prixTotalVente = null;
        if (livraison.getType() == TypeLivraison.Marchandise) {
            prixTotalAchat = getPrixTotalAchatMarchandise(livraison);
            prixTotalVente = getPrixTotalVenteMarchandise(livraison);
        } else if (livraison.getType() == TypeLivraison.Transport) {
            prixTotalVente = getPrixTransport(livraison);
        }
        livraison.setPrixTotalAchat(prixTotalAchat);
        livraison.setPrixTotalVente(prixTotalVente);
	}

	private Float getPrixTotalAchatMarchandise(Livraison livraison) {
        Pricer achatPricer = pricerFactory.getPricer(TypePricer.Achat);
        Float prixAchat = achatPricer.price(livraison);
        Optional.ofNullable(prixAchat).orElseThrow(() -> new PriceComputingException(TypePricer.Achat));
        return livraison.getUniteAchat().getPrixTotalAchat(livraison, achatPricer.price(livraison));
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
        Optional.ofNullable(prixTransport).orElseThrow(() -> new PriceComputingException(TypePricer.Transport));
        return livraison.getUniteVente().getPrixTotalVente(livraison, prixTransport);
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

    @Override
    @Transactional(readOnly = true)
    public Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable) {
    	return livraisonRepository.getRecapitulatifAchat(recapitulatifAchatRequest, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable){
        return livraisonRepository.getSuiviTrajet(suiviTrajetRequest, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable){
        return livraisonRepository.getRecapitulatifClient(recapitulatifClientRequest, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable){
        return livraisonRepository.getRecapitulatifFacturation(recapitulatifFacturationRequest, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IRecapitulatifChauffeur> getRecapitulatifChauffeur(RecapitulatifChauffeurRequest recapitulatifChauffeurRequest, Pageable pageable){
        Long transporteurId = recapitulatifChauffeurRequest.getIdTransporteur();
        return Optional.ofNullable(transporteurId)
            .map(aTransporteurId -> livraisonRepository.getRecapitulatifChauffeur(recapitulatifChauffeurRequest.getDateDebut(), recapitulatifChauffeurRequest.getDateFin(), aTransporteurId, pageable))
            .orElseGet(() -> livraisonRepository.getRecapitulatifChauffeur(recapitulatifChauffeurRequest.getDateDebut(), recapitulatifChauffeurRequest.getDateFin(), pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable) {
        return livraisonRepository.getRecapitulatifCaCamion(recapitulatifCaCamionRequest, pageable);
    }

    @Override
    public Bon getBon(Long id, TypeBon typeBon) {
        Optional<Livraison> livraison = findOne(id);
        return livraison.map(aLivraison -> new Bon(typeBon.bonContent(aLivraison), typeBon.bonContentType(aLivraison))).orElse(new Bon());
    }

    @Override
    public Double getTotalPrixVenteBySocieteFacturation(Long societeId, LocalDate dateDebut, LocalDate dateFin) {
        Assert.notNull(societeId, "Merci de fournir l'id de la société de facturation");
        return livraisonRepository.getTotalPrixVenteBySocieteFacturation(societeId, dateDebut, dateFin);
    }
}
