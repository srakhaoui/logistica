package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.*;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.domain.enumeration.Unite;
import com.logistica.repository.LivraisonRepository;
import com.logistica.service.LivraisonQueryService;
import com.logistica.service.LivraisonService;
import com.logistica.service.TrajetService;
import com.logistica.service.tarif.AchatPricer;
import com.logistica.service.tarif.TransportPricer;
import com.logistica.service.tarif.VentePricer;
import com.logistica.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.logistica.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link LivraisonResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class LivraisonResourceIT {

    private static final LocalDate DEFAULT_DATE_BON_COMMANDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BON_COMMANDE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_BON_COMMANDE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NUMERO_BON_COMMANDE = 1;
    private static final Integer UPDATED_NUMERO_BON_COMMANDE = 2;
    private static final Integer SMALLER_NUMERO_BON_COMMANDE = 1 - 1;

    private static final Integer DEFAULT_NUMERO_BON_LIVRAISON = 1;
    private static final Integer UPDATED_NUMERO_BON_LIVRAISON = 2;
    private static final Integer SMALLER_NUMERO_BON_LIVRAISON = 1 - 1;

    private static final LocalDate DEFAULT_DATE_BON_LIVRAISON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BON_LIVRAISON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_BON_LIVRAISON = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NUMERO_BON_FOURNISSEUR = 1;
    private static final Integer UPDATED_NUMERO_BON_FOURNISSEUR = 2;
    private static final Integer SMALLER_NUMERO_BON_FOURNISSEUR = 1 - 1;

    private static final Float DEFAULT_QUANTITE_VENDUE = 1F;
    private static final Float UPDATED_QUANTITE_VENDUE = 2F;
    private static final Float SMALLER_QUANTITE_VENDUE = 1F - 1F;

    private static final Unite DEFAULT_UNITE_VENTE = Unite.Tonne;
    private static final Unite UPDATED_UNITE_VENTE = Unite.M3;

    private static final Float DEFAULT_PRIX_TOTAL_VENTE = 0F;
    private static final Float UPDATED_PRIX_TOTAL_VENTE = 200F;
    private static final Float SMALLER_PRIX_TOTAL_VENTE = -1F;

    private static final Float DEFAULT_QUANTITE_ACHETEE = 1F;
    private static final Float UPDATED_QUANTITE_ACHETEE = 2F;
    private static final Float SMALLER_QUANTITE_ACHETEE = 1F - 1F;

    private static final Unite DEFAULT_UNITE_ACHAT = Unite.Tonne;
    private static final Unite UPDATED_UNITE_ACHAT = Unite.M3;

    private static final Float DEFAULT_PRIX_TOTAL_ACHAT = 0F;
    private static final Float UPDATED_PRIX_TOTAL_ACHAT = 200F;
    private static final Float SMALLER_PRIX_TOTAL_ACHAT = -1F;

    private static final Float DEFAULT_QUANTITE_CONVERTIE = 1F;
    private static final Float UPDATED_QUANTITE_CONVERTIE = 2F;
    private static final Float SMALLER_QUANTITE_CONVERTIE = 1F - 1F;

    private static final TypeLivraison DEFAULT_TYPE = TypeLivraison.Transport;
    private static final TypeLivraison UPDATED_TYPE = TypeLivraison.Marchandise;

    private static final Boolean DEFAULT_FACTURE = false;
    private static final Boolean UPDATED_FACTURE = true;

    private static final LocalDate DEFAULT_DATE_BON_CAISSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BON_CAISSE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_BON_CAISSE = LocalDate.ofEpochDay(-1L);

    private static final Float DEFAULT_REPARATION_DIVERS = 1F;
    private static final Float UPDATED_REPARATION_DIVERS = 2F;
    private static final Float SMALLER_REPARATION_DIVERS = 1F - 1F;

    private static final Float DEFAULT_TRAX = 1F;
    private static final Float UPDATED_TRAX = 2F;
    private static final Float SMALLER_TRAX = 1F - 1F;

    private static final Float DEFAULT_BALANCE = 1F;
    private static final Float UPDATED_BALANCE = 2F;
    private static final Float SMALLER_BALANCE = 1F - 1F;

    private static final Float DEFAULT_AVANCE = 1F;
    private static final Float UPDATED_AVANCE = 2F;
    private static final Float SMALLER_AVANCE = 1F - 1F;

    private static final Float DEFAULT_AUTOROUTE = 1F;
    private static final Float UPDATED_AUTOROUTE = 2F;
    private static final Float SMALLER_AUTOROUTE = 1F - 1F;

    private static final Float DEFAULT_DERNIER_ETAT = 1F;
    private static final Float UPDATED_DERNIER_ETAT = 2F;
    private static final Float SMALLER_DERNIER_ETAT = 1F - 1F;

    private static final Float DEFAULT_PENALITE_ESE = 1F;
    private static final Float UPDATED_PENALITE_ESE = 2F;
    private static final Float SMALLER_PENALITE_ESE = 1F - 1F;

    private static final Float DEFAULT_PENALITE_CHFRS = 1F;
    private static final Float UPDATED_PENALITE_CHFRS = 2F;
    private static final Float SMALLER_PENALITE_CHFRS = 1F - 1F;

    private static final Float DEFAULT_FRAIS_ESPECE = 1F;
    private static final Float UPDATED_FRAIS_ESPECE = 2F;
    private static final Float SMALLER_FRAIS_ESPECE = 1F - 1F;

    private static final Float DEFAULT_RETENU = 1F;
    private static final Float UPDATED_RETENU = 2F;
    private static final Float SMALLER_RETENU = 1F - 1F;

    private static final Float DEFAULT_TOTAL_COMISSION = 150F;
    private static final Float UPDATED_TOTAL_COMISSION = 150F;
    private static final Float SMALLER_TOTAL_COMISSION = 1F - 1F;
    public static final float DEFAULT_ACHAT_PRICE = 100.0F;
    public static final float DEFAULT_VENTE_PRICE = 100.0F;
    public static final float DEFAULT_TRANSPORT_PRICE = 100.0F;
    public static final float DEFAULT_COMMISSION_TRAJET = 150F;

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LivraisonService livraisonService;

    @Autowired
    private LivraisonQueryService livraisonQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @MockBean
    private VentePricer ventePricer;

    @MockBean
    private AchatPricer achatPricer;

    @MockBean
    private TransportPricer transportPricer;

    @MockBean
    private TrajetService trajetService;

    private MockMvc restLivraisonMockMvc;

    private Livraison livraison;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivraisonResource livraisonResource = new LivraisonResource(livraisonService, livraisonQueryService);
        this.restLivraisonMockMvc = MockMvcBuilders.standaloneSetup(livraisonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createEntity(EntityManager em) {
        Trajet trajet = new Trajet().depart("depart").destination("destination");
        em.persist(trajet);
        Livraison livraison = new Livraison()
            .trajet(trajet)
            .dateBonCommande(DEFAULT_DATE_BON_COMMANDE)
            .numeroBonCommande(DEFAULT_NUMERO_BON_COMMANDE)
            .numeroBonLivraison(DEFAULT_NUMERO_BON_LIVRAISON)
            .dateBonLivraison(DEFAULT_DATE_BON_LIVRAISON)
            .numeroBonFournisseur(DEFAULT_NUMERO_BON_FOURNISSEUR)
            .quantiteVendue(DEFAULT_QUANTITE_VENDUE)
            .uniteVente(DEFAULT_UNITE_VENTE)
            .prixTotalVente(DEFAULT_PRIX_TOTAL_VENTE)
            .quantiteAchetee(DEFAULT_QUANTITE_ACHETEE)
            .uniteAchat(DEFAULT_UNITE_ACHAT)
            .prixTotalAchat(DEFAULT_PRIX_TOTAL_ACHAT)
            .quantiteConvertie(DEFAULT_QUANTITE_CONVERTIE)
            .type(DEFAULT_TYPE)
            .facture(DEFAULT_FACTURE)
            .dateBonCaisse(DEFAULT_DATE_BON_CAISSE)
            .reparationDivers(DEFAULT_REPARATION_DIVERS)
            .trax(DEFAULT_TRAX)
            .balance(DEFAULT_BALANCE)
            .avance(DEFAULT_AVANCE)
            .autoroute(DEFAULT_AUTOROUTE)
            .dernierEtat(DEFAULT_DERNIER_ETAT)
            .penaliteEse(DEFAULT_PENALITE_ESE)
            .penaliteChfrs(DEFAULT_PENALITE_CHFRS)
            .fraisEspece(DEFAULT_FRAIS_ESPECE)
            .retenu(DEFAULT_RETENU)
            .totalComission(DEFAULT_TOTAL_COMISSION);
        return livraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createUpdatedEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .dateBonCommande(UPDATED_DATE_BON_COMMANDE)
            .numeroBonCommande(UPDATED_NUMERO_BON_COMMANDE)
            .numeroBonLivraison(UPDATED_NUMERO_BON_LIVRAISON)
            .dateBonLivraison(UPDATED_DATE_BON_LIVRAISON)
            .numeroBonFournisseur(UPDATED_NUMERO_BON_FOURNISSEUR)
            .quantiteVendue(UPDATED_QUANTITE_VENDUE)
            .uniteVente(UPDATED_UNITE_VENTE)
            .prixTotalVente(UPDATED_PRIX_TOTAL_VENTE)
            .quantiteAchetee(UPDATED_QUANTITE_ACHETEE)
            .uniteAchat(UPDATED_UNITE_ACHAT)
            .prixTotalAchat(UPDATED_PRIX_TOTAL_ACHAT)
            .quantiteConvertie(UPDATED_QUANTITE_CONVERTIE)
            .type(UPDATED_TYPE)
            .facture(UPDATED_FACTURE)
            .dateBonCaisse(UPDATED_DATE_BON_CAISSE)
            .reparationDivers(UPDATED_REPARATION_DIVERS)
            .trax(UPDATED_TRAX)
            .balance(UPDATED_BALANCE)
            .avance(UPDATED_AVANCE)
            .autoroute(UPDATED_AUTOROUTE)
            .dernierEtat(UPDATED_DERNIER_ETAT)
            .penaliteEse(UPDATED_PENALITE_ESE)
            .penaliteChfrs(UPDATED_PENALITE_CHFRS)
            .fraisEspece(UPDATED_FRAIS_ESPECE)
            .retenu(UPDATED_RETENU)
            .totalComission(UPDATED_TOTAL_COMISSION);
        return livraison;
    }

    @BeforeEach
    public void initTest() {
        livraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivraison() throws Exception {
        when(trajetService.getCommissionByTrajet(livraison.getTrajet().getDepart(), livraison.getTrajet().getDestination())).thenReturn(DEFAULT_COMMISSION_TRAJET);
        when(achatPricer.price(livraison)).thenReturn(DEFAULT_ACHAT_PRICE);
        when(ventePricer.price(livraison)).thenReturn(DEFAULT_VENTE_PRICE);
        when(transportPricer.price(livraison)).thenReturn(DEFAULT_TRANSPORT_PRICE);

        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // Create the Livraison
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isCreated());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate + 1);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getDateBonCommande()).isEqualTo(DEFAULT_DATE_BON_COMMANDE);
        assertThat(testLivraison.getNumeroBonCommande()).isEqualTo(DEFAULT_NUMERO_BON_COMMANDE);
        assertThat(testLivraison.getNumeroBonLivraison()).isEqualTo(DEFAULT_NUMERO_BON_LIVRAISON);
        assertThat(testLivraison.getDateBonLivraison()).isEqualTo(DEFAULT_DATE_BON_LIVRAISON);
        assertThat(testLivraison.getNumeroBonFournisseur()).isEqualTo(DEFAULT_NUMERO_BON_FOURNISSEUR);
        assertThat(testLivraison.getQuantiteVendue()).isEqualTo(DEFAULT_QUANTITE_VENDUE);
        assertThat(testLivraison.getUniteVente()).isEqualTo(DEFAULT_UNITE_VENTE);
        assertThat(testLivraison.getPrixTotalVente()).isEqualTo(DEFAULT_PRIX_TOTAL_VENTE);
        assertThat(testLivraison.getQuantiteAchetee()).isEqualTo(DEFAULT_QUANTITE_ACHETEE);
        assertThat(testLivraison.getUniteAchat()).isEqualTo(DEFAULT_UNITE_ACHAT);
        assertThat(testLivraison.getPrixTotalAchat()).isEqualTo(DEFAULT_PRIX_TOTAL_ACHAT);
        assertThat(testLivraison.getQuantiteConvertie()).isEqualTo(DEFAULT_QUANTITE_CONVERTIE);
        assertThat(testLivraison.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLivraison.isFacture()).isEqualTo(DEFAULT_FACTURE);
        assertThat(testLivraison.getDateBonCaisse()).isEqualTo(DEFAULT_DATE_BON_CAISSE);
        assertThat(testLivraison.getReparationDivers()).isEqualTo(DEFAULT_REPARATION_DIVERS);
        assertThat(testLivraison.getTrax()).isEqualTo(DEFAULT_TRAX);
        assertThat(testLivraison.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testLivraison.getAvance()).isEqualTo(DEFAULT_AVANCE);
        assertThat(testLivraison.getAutoroute()).isEqualTo(DEFAULT_AUTOROUTE);
        assertThat(testLivraison.getDernierEtat()).isEqualTo(DEFAULT_DERNIER_ETAT);
        assertThat(testLivraison.getPenaliteEse()).isEqualTo(DEFAULT_PENALITE_ESE);
        assertThat(testLivraison.getPenaliteChfrs()).isEqualTo(DEFAULT_PENALITE_CHFRS);
        assertThat(testLivraison.getFraisEspece()).isEqualTo(DEFAULT_FRAIS_ESPECE);
        assertThat(testLivraison.getRetenu()).isEqualTo(DEFAULT_RETENU);
        assertThat(testLivraison.getTotalComission()).isEqualTo(DEFAULT_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void createLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // Create the Livraison with an existing ID
        livraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroBonLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setNumeroBonLivraison(null);

        // Create the Livraison, which fails.

        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateBonLivraisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setDateBonLivraison(null);

        // Create the Livraison, which fails.

        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setType(null);

        // Create the Livraison, which fails.

        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateBonCaisseIsRequired() throws Exception {
        int databaseSizeBeforeTest = livraisonRepository.findAll().size();
        // set the field null
        livraison.setDateBonCaisse(null);

        // Create the Livraison, which fails.

        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLivraisons() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateBonCommande").value(hasItem(DEFAULT_DATE_BON_COMMANDE.toString())))
            .andExpect(jsonPath("$.[*].numeroBonCommande").value(hasItem(DEFAULT_NUMERO_BON_COMMANDE)))
            .andExpect(jsonPath("$.[*].numeroBonLivraison").value(hasItem(DEFAULT_NUMERO_BON_LIVRAISON)))
            .andExpect(jsonPath("$.[*].dateBonLivraison").value(hasItem(DEFAULT_DATE_BON_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].numeroBonFournisseur").value(hasItem(DEFAULT_NUMERO_BON_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].quantiteVendue").value(hasItem(DEFAULT_QUANTITE_VENDUE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteVente").value(hasItem(DEFAULT_UNITE_VENTE.toString())))
            .andExpect(jsonPath("$.[*].prixTotalVente").value(hasItem(DEFAULT_PRIX_TOTAL_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteAchetee").value(hasItem(DEFAULT_QUANTITE_ACHETEE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteAchat").value(hasItem(DEFAULT_UNITE_ACHAT.toString())))
            .andExpect(jsonPath("$.[*].prixTotalAchat").value(hasItem(DEFAULT_PRIX_TOTAL_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteConvertie").value(hasItem(DEFAULT_QUANTITE_CONVERTIE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].facture").value(hasItem(DEFAULT_FACTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateBonCaisse").value(hasItem(DEFAULT_DATE_BON_CAISSE.toString())))
            .andExpect(jsonPath("$.[*].reparationDivers").value(hasItem(DEFAULT_REPARATION_DIVERS.doubleValue())))
            .andExpect(jsonPath("$.[*].trax").value(hasItem(DEFAULT_TRAX.doubleValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].avance").value(hasItem(DEFAULT_AVANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].autoroute").value(hasItem(DEFAULT_AUTOROUTE.doubleValue())))
            .andExpect(jsonPath("$.[*].dernierEtat").value(hasItem(DEFAULT_DERNIER_ETAT.doubleValue())))
            .andExpect(jsonPath("$.[*].penaliteEse").value(hasItem(DEFAULT_PENALITE_ESE.doubleValue())))
            .andExpect(jsonPath("$.[*].penaliteChfrs").value(hasItem(DEFAULT_PENALITE_CHFRS.doubleValue())))
            .andExpect(jsonPath("$.[*].fraisEspece").value(hasItem(DEFAULT_FRAIS_ESPECE.doubleValue())))
            .andExpect(jsonPath("$.[*].retenu").value(hasItem(DEFAULT_RETENU.doubleValue())))
            .andExpect(jsonPath("$.[*].totalComission").value(hasItem(DEFAULT_TOTAL_COMISSION.doubleValue())));
    }

    @Test
    @Transactional
    public void getLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livraison.getId().intValue()))
            .andExpect(jsonPath("$.dateBonCommande").value(DEFAULT_DATE_BON_COMMANDE.toString()))
            .andExpect(jsonPath("$.numeroBonCommande").value(DEFAULT_NUMERO_BON_COMMANDE))
            .andExpect(jsonPath("$.numeroBonLivraison").value(DEFAULT_NUMERO_BON_LIVRAISON))
            .andExpect(jsonPath("$.dateBonLivraison").value(DEFAULT_DATE_BON_LIVRAISON.toString()))
            .andExpect(jsonPath("$.numeroBonFournisseur").value(DEFAULT_NUMERO_BON_FOURNISSEUR))
            .andExpect(jsonPath("$.quantiteVendue").value(DEFAULT_QUANTITE_VENDUE.doubleValue()))
            .andExpect(jsonPath("$.uniteVente").value(DEFAULT_UNITE_VENTE.toString()))
            .andExpect(jsonPath("$.prixTotalVente").value(DEFAULT_PRIX_TOTAL_VENTE.doubleValue()))
            .andExpect(jsonPath("$.quantiteAchetee").value(DEFAULT_QUANTITE_ACHETEE))
            .andExpect(jsonPath("$.uniteAchat").value(DEFAULT_UNITE_ACHAT.toString()))
            .andExpect(jsonPath("$.prixTotalAchat").value(DEFAULT_PRIX_TOTAL_ACHAT.doubleValue()))
            .andExpect(jsonPath("$.quantiteConvertie").value(DEFAULT_QUANTITE_CONVERTIE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.facture").value(DEFAULT_FACTURE.booleanValue()))
            .andExpect(jsonPath("$.dateBonCaisse").value(DEFAULT_DATE_BON_CAISSE.toString()))
            .andExpect(jsonPath("$.reparationDivers").value(DEFAULT_REPARATION_DIVERS.doubleValue()))
            .andExpect(jsonPath("$.trax").value(DEFAULT_TRAX.doubleValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.avance").value(DEFAULT_AVANCE.doubleValue()))
            .andExpect(jsonPath("$.autoroute").value(DEFAULT_AUTOROUTE.doubleValue()))
            .andExpect(jsonPath("$.dernierEtat").value(DEFAULT_DERNIER_ETAT.doubleValue()))
            .andExpect(jsonPath("$.penaliteEse").value(DEFAULT_PENALITE_ESE.doubleValue()))
            .andExpect(jsonPath("$.penaliteChfrs").value(DEFAULT_PENALITE_CHFRS.doubleValue()))
            .andExpect(jsonPath("$.fraisEspece").value(DEFAULT_FRAIS_ESPECE.doubleValue()))
            .andExpect(jsonPath("$.retenu").value(DEFAULT_RETENU.doubleValue()))
            .andExpect(jsonPath("$.totalComission").value(DEFAULT_TOTAL_COMISSION.doubleValue()));
    }


    @Test
    @Transactional
    public void getLivraisonsByIdFiltering() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        Long id = livraison.getId();

        defaultLivraisonShouldBeFound("id.equals=" + id);
        defaultLivraisonShouldNotBeFound("id.notEquals=" + id);

        defaultLivraisonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLivraisonShouldNotBeFound("id.greaterThan=" + id);

        defaultLivraisonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLivraisonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande equals to DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.equals=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande equals to UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.equals=" + UPDATED_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande not equals to DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.notEquals=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande not equals to UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.notEquals=" + UPDATED_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande in DEFAULT_DATE_BON_COMMANDE or UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.in=" + DEFAULT_DATE_BON_COMMANDE + "," + UPDATED_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande equals to UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.in=" + UPDATED_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande is not null
        defaultLivraisonShouldBeFound("dateBonCommande.specified=true");

        // Get all the livraisonList where dateBonCommande is null
        defaultLivraisonShouldNotBeFound("dateBonCommande.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande is greater than or equal to DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.greaterThanOrEqual=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande is greater than or equal to UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.greaterThanOrEqual=" + UPDATED_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande is less than or equal to DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.lessThanOrEqual=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande is less than or equal to SMALLER_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.lessThanOrEqual=" + SMALLER_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande is less than DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.lessThan=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande is less than UPDATED_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.lessThan=" + UPDATED_DATE_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCommandeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCommande is greater than DEFAULT_DATE_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("dateBonCommande.greaterThan=" + DEFAULT_DATE_BON_COMMANDE);

        // Get all the livraisonList where dateBonCommande is greater than SMALLER_DATE_BON_COMMANDE
        defaultLivraisonShouldBeFound("dateBonCommande.greaterThan=" + SMALLER_DATE_BON_COMMANDE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande equals to DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.equals=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande equals to UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.equals=" + UPDATED_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande not equals to DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.notEquals=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande not equals to UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.notEquals=" + UPDATED_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande in DEFAULT_NUMERO_BON_COMMANDE or UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.in=" + DEFAULT_NUMERO_BON_COMMANDE + "," + UPDATED_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande equals to UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.in=" + UPDATED_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande is not null
        defaultLivraisonShouldBeFound("numeroBonCommande.specified=true");

        // Get all the livraisonList where numeroBonCommande is null
        defaultLivraisonShouldNotBeFound("numeroBonCommande.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande is greater than or equal to DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.greaterThanOrEqual=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande is greater than or equal to UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.greaterThanOrEqual=" + UPDATED_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande is less than or equal to DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.lessThanOrEqual=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande is less than or equal to SMALLER_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.lessThanOrEqual=" + SMALLER_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande is less than DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.lessThan=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande is less than UPDATED_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.lessThan=" + UPDATED_NUMERO_BON_COMMANDE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonCommandeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonCommande is greater than DEFAULT_NUMERO_BON_COMMANDE
        defaultLivraisonShouldNotBeFound("numeroBonCommande.greaterThan=" + DEFAULT_NUMERO_BON_COMMANDE);

        // Get all the livraisonList where numeroBonCommande is greater than SMALLER_NUMERO_BON_COMMANDE
        defaultLivraisonShouldBeFound("numeroBonCommande.greaterThan=" + SMALLER_NUMERO_BON_COMMANDE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison equals to DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.equals=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison equals to UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.equals=" + UPDATED_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison not equals to DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.notEquals=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison not equals to UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.notEquals=" + UPDATED_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison in DEFAULT_NUMERO_BON_LIVRAISON or UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.in=" + DEFAULT_NUMERO_BON_LIVRAISON + "," + UPDATED_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison equals to UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.in=" + UPDATED_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison is not null
        defaultLivraisonShouldBeFound("numeroBonLivraison.specified=true");

        // Get all the livraisonList where numeroBonLivraison is null
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison is greater than or equal to DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.greaterThanOrEqual=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison is greater than or equal to UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.greaterThanOrEqual=" + UPDATED_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison is less than or equal to DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.lessThanOrEqual=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison is less than or equal to SMALLER_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.lessThanOrEqual=" + SMALLER_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison is less than DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.lessThan=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison is less than UPDATED_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.lessThan=" + UPDATED_NUMERO_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonLivraisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonLivraison is greater than DEFAULT_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("numeroBonLivraison.greaterThan=" + DEFAULT_NUMERO_BON_LIVRAISON);

        // Get all the livraisonList where numeroBonLivraison is greater than SMALLER_NUMERO_BON_LIVRAISON
        defaultLivraisonShouldBeFound("numeroBonLivraison.greaterThan=" + SMALLER_NUMERO_BON_LIVRAISON);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison equals to DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.equals=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison equals to UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.equals=" + UPDATED_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison not equals to DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.notEquals=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison not equals to UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.notEquals=" + UPDATED_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison in DEFAULT_DATE_BON_LIVRAISON or UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.in=" + DEFAULT_DATE_BON_LIVRAISON + "," + UPDATED_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison equals to UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.in=" + UPDATED_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison is not null
        defaultLivraisonShouldBeFound("dateBonLivraison.specified=true");

        // Get all the livraisonList where dateBonLivraison is null
        defaultLivraisonShouldNotBeFound("dateBonLivraison.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison is greater than or equal to DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.greaterThanOrEqual=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison is greater than or equal to UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.greaterThanOrEqual=" + UPDATED_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison is less than or equal to DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.lessThanOrEqual=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison is less than or equal to SMALLER_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.lessThanOrEqual=" + SMALLER_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison is less than DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.lessThan=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison is less than UPDATED_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.lessThan=" + UPDATED_DATE_BON_LIVRAISON);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonLivraisonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonLivraison is greater than DEFAULT_DATE_BON_LIVRAISON
        defaultLivraisonShouldNotBeFound("dateBonLivraison.greaterThan=" + DEFAULT_DATE_BON_LIVRAISON);

        // Get all the livraisonList where dateBonLivraison is greater than SMALLER_DATE_BON_LIVRAISON
        defaultLivraisonShouldBeFound("dateBonLivraison.greaterThan=" + SMALLER_DATE_BON_LIVRAISON);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur equals to DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.equals=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur equals to UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.equals=" + UPDATED_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur not equals to DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.notEquals=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur not equals to UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.notEquals=" + UPDATED_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur in DEFAULT_NUMERO_BON_FOURNISSEUR or UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.in=" + DEFAULT_NUMERO_BON_FOURNISSEUR + "," + UPDATED_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur equals to UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.in=" + UPDATED_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur is not null
        defaultLivraisonShouldBeFound("numeroBonFournisseur.specified=true");

        // Get all the livraisonList where numeroBonFournisseur is null
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur is greater than or equal to DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.greaterThanOrEqual=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur is greater than or equal to UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.greaterThanOrEqual=" + UPDATED_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur is less than or equal to DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.lessThanOrEqual=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur is less than or equal to SMALLER_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.lessThanOrEqual=" + SMALLER_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur is less than DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.lessThan=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur is less than UPDATED_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.lessThan=" + UPDATED_NUMERO_BON_FOURNISSEUR);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByNumeroBonFournisseurIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where numeroBonFournisseur is greater than DEFAULT_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldNotBeFound("numeroBonFournisseur.greaterThan=" + DEFAULT_NUMERO_BON_FOURNISSEUR);

        // Get all the livraisonList where numeroBonFournisseur is greater than SMALLER_NUMERO_BON_FOURNISSEUR
        defaultLivraisonShouldBeFound("numeroBonFournisseur.greaterThan=" + SMALLER_NUMERO_BON_FOURNISSEUR);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue equals to DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.equals=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue equals to UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.equals=" + UPDATED_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue not equals to DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.notEquals=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue not equals to UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.notEquals=" + UPDATED_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue in DEFAULT_QUANTITE_VENDUE or UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.in=" + DEFAULT_QUANTITE_VENDUE + "," + UPDATED_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue equals to UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.in=" + UPDATED_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue is not null
        defaultLivraisonShouldBeFound("quantiteVendue.specified=true");

        // Get all the livraisonList where quantiteVendue is null
        defaultLivraisonShouldNotBeFound("quantiteVendue.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue is greater than or equal to DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.greaterThanOrEqual=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue is greater than or equal to UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.greaterThanOrEqual=" + UPDATED_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue is less than or equal to DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.lessThanOrEqual=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue is less than or equal to SMALLER_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.lessThanOrEqual=" + SMALLER_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue is less than DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.lessThan=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue is less than UPDATED_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.lessThan=" + UPDATED_QUANTITE_VENDUE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteVendueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteVendue is greater than DEFAULT_QUANTITE_VENDUE
        defaultLivraisonShouldNotBeFound("quantiteVendue.greaterThan=" + DEFAULT_QUANTITE_VENDUE);

        // Get all the livraisonList where quantiteVendue is greater than SMALLER_QUANTITE_VENDUE
        defaultLivraisonShouldBeFound("quantiteVendue.greaterThan=" + SMALLER_QUANTITE_VENDUE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByUniteVenteIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteVente equals to DEFAULT_UNITE_VENTE
        defaultLivraisonShouldBeFound("uniteVente.equals=" + DEFAULT_UNITE_VENTE);

        // Get all the livraisonList where uniteVente equals to UPDATED_UNITE_VENTE
        defaultLivraisonShouldNotBeFound("uniteVente.equals=" + UPDATED_UNITE_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteVenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteVente not equals to DEFAULT_UNITE_VENTE
        defaultLivraisonShouldNotBeFound("uniteVente.notEquals=" + DEFAULT_UNITE_VENTE);

        // Get all the livraisonList where uniteVente not equals to UPDATED_UNITE_VENTE
        defaultLivraisonShouldBeFound("uniteVente.notEquals=" + UPDATED_UNITE_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteVenteIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteVente in DEFAULT_UNITE_VENTE or UPDATED_UNITE_VENTE
        defaultLivraisonShouldBeFound("uniteVente.in=" + DEFAULT_UNITE_VENTE + "," + UPDATED_UNITE_VENTE);

        // Get all the livraisonList where uniteVente equals to UPDATED_UNITE_VENTE
        defaultLivraisonShouldNotBeFound("uniteVente.in=" + UPDATED_UNITE_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteVenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteVente is not null
        defaultLivraisonShouldBeFound("uniteVente.specified=true");

        // Get all the livraisonList where uniteVente is null
        defaultLivraisonShouldNotBeFound("uniteVente.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente equals to DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.equals=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente equals to UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.equals=" + UPDATED_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente not equals to DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.notEquals=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente not equals to UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.notEquals=" + UPDATED_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente in DEFAULT_PRIX_TOTAL_VENTE or UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.in=" + DEFAULT_PRIX_TOTAL_VENTE + "," + UPDATED_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente equals to UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.in=" + UPDATED_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente is not null
        defaultLivraisonShouldBeFound("prixTotalVente.specified=true");

        // Get all the livraisonList where prixTotalVente is null
        defaultLivraisonShouldNotBeFound("prixTotalVente.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente is greater than or equal to DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.greaterThanOrEqual=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente is greater than or equal to UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.greaterThanOrEqual=" + UPDATED_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente is less than or equal to DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.lessThanOrEqual=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente is less than or equal to SMALLER_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.lessThanOrEqual=" + SMALLER_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente is less than DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.lessThan=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente is less than UPDATED_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.lessThan=" + UPDATED_PRIX_TOTAL_VENTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalVenteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalVente is greater than DEFAULT_PRIX_TOTAL_VENTE
        defaultLivraisonShouldNotBeFound("prixTotalVente.greaterThan=" + DEFAULT_PRIX_TOTAL_VENTE);

        // Get all the livraisonList where prixTotalVente is greater than SMALLER_PRIX_TOTAL_VENTE
        defaultLivraisonShouldBeFound("prixTotalVente.greaterThan=" + SMALLER_PRIX_TOTAL_VENTE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee equals to DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.equals=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee equals to UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.equals=" + UPDATED_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee not equals to DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.notEquals=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee not equals to UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.notEquals=" + UPDATED_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee in DEFAULT_QUANTITE_ACHETEE or UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.in=" + DEFAULT_QUANTITE_ACHETEE + "," + UPDATED_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee equals to UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.in=" + UPDATED_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee is not null
        defaultLivraisonShouldBeFound("quantiteAchetee.specified=true");

        // Get all the livraisonList where quantiteAchetee is null
        defaultLivraisonShouldNotBeFound("quantiteAchetee.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee is greater than or equal to DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.greaterThanOrEqual=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee is greater than or equal to UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.greaterThanOrEqual=" + UPDATED_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee is less than or equal to DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.lessThanOrEqual=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee is less than or equal to SMALLER_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.lessThanOrEqual=" + SMALLER_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee is less than DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.lessThan=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee is less than UPDATED_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.lessThan=" + UPDATED_QUANTITE_ACHETEE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteAcheteeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteAchetee is greater than DEFAULT_QUANTITE_ACHETEE
        defaultLivraisonShouldNotBeFound("quantiteAchetee.greaterThan=" + DEFAULT_QUANTITE_ACHETEE);

        // Get all the livraisonList where quantiteAchetee is greater than SMALLER_QUANTITE_ACHETEE
        defaultLivraisonShouldBeFound("quantiteAchetee.greaterThan=" + SMALLER_QUANTITE_ACHETEE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByUniteAchatIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteAchat equals to DEFAULT_UNITE_ACHAT
        defaultLivraisonShouldBeFound("uniteAchat.equals=" + DEFAULT_UNITE_ACHAT);

        // Get all the livraisonList where uniteAchat equals to UPDATED_UNITE_ACHAT
        defaultLivraisonShouldNotBeFound("uniteAchat.equals=" + UPDATED_UNITE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteAchatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteAchat not equals to DEFAULT_UNITE_ACHAT
        defaultLivraisonShouldNotBeFound("uniteAchat.notEquals=" + DEFAULT_UNITE_ACHAT);

        // Get all the livraisonList where uniteAchat not equals to UPDATED_UNITE_ACHAT
        defaultLivraisonShouldBeFound("uniteAchat.notEquals=" + UPDATED_UNITE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteAchatIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteAchat in DEFAULT_UNITE_ACHAT or UPDATED_UNITE_ACHAT
        defaultLivraisonShouldBeFound("uniteAchat.in=" + DEFAULT_UNITE_ACHAT + "," + UPDATED_UNITE_ACHAT);

        // Get all the livraisonList where uniteAchat equals to UPDATED_UNITE_ACHAT
        defaultLivraisonShouldNotBeFound("uniteAchat.in=" + UPDATED_UNITE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByUniteAchatIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where uniteAchat is not null
        defaultLivraisonShouldBeFound("uniteAchat.specified=true");

        // Get all the livraisonList where uniteAchat is null
        defaultLivraisonShouldNotBeFound("uniteAchat.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat equals to DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.equals=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat equals to UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.equals=" + UPDATED_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat not equals to DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.notEquals=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat not equals to UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.notEquals=" + UPDATED_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat in DEFAULT_PRIX_TOTAL_ACHAT or UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.in=" + DEFAULT_PRIX_TOTAL_ACHAT + "," + UPDATED_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat equals to UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.in=" + UPDATED_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat is not null
        defaultLivraisonShouldBeFound("prixTotalAchat.specified=true");

        // Get all the livraisonList where prixTotalAchat is null
        defaultLivraisonShouldNotBeFound("prixTotalAchat.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat is greater than or equal to DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.greaterThanOrEqual=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat is greater than or equal to UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.greaterThanOrEqual=" + UPDATED_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat is less than or equal to DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.lessThanOrEqual=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat is less than or equal to SMALLER_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.lessThanOrEqual=" + SMALLER_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat is less than DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.lessThan=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat is less than UPDATED_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.lessThan=" + UPDATED_PRIX_TOTAL_ACHAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPrixTotalAchatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where prixTotalAchat is greater than DEFAULT_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldNotBeFound("prixTotalAchat.greaterThan=" + DEFAULT_PRIX_TOTAL_ACHAT);

        // Get all the livraisonList where prixTotalAchat is greater than SMALLER_PRIX_TOTAL_ACHAT
        defaultLivraisonShouldBeFound("prixTotalAchat.greaterThan=" + SMALLER_PRIX_TOTAL_ACHAT);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie equals to DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.equals=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie equals to UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.equals=" + UPDATED_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie not equals to DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.notEquals=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie not equals to UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.notEquals=" + UPDATED_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie in DEFAULT_QUANTITE_CONVERTIE or UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.in=" + DEFAULT_QUANTITE_CONVERTIE + "," + UPDATED_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie equals to UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.in=" + UPDATED_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie is not null
        defaultLivraisonShouldBeFound("quantiteConvertie.specified=true");

        // Get all the livraisonList where quantiteConvertie is null
        defaultLivraisonShouldNotBeFound("quantiteConvertie.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie is greater than or equal to DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.greaterThanOrEqual=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie is greater than or equal to UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.greaterThanOrEqual=" + UPDATED_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie is less than or equal to DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.lessThanOrEqual=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie is less than or equal to SMALLER_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.lessThanOrEqual=" + SMALLER_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie is less than DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.lessThan=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie is less than UPDATED_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.lessThan=" + UPDATED_QUANTITE_CONVERTIE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByQuantiteConvertieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where quantiteConvertie is greater than DEFAULT_QUANTITE_CONVERTIE
        defaultLivraisonShouldNotBeFound("quantiteConvertie.greaterThan=" + DEFAULT_QUANTITE_CONVERTIE);

        // Get all the livraisonList where quantiteConvertie is greater than SMALLER_QUANTITE_CONVERTIE
        defaultLivraisonShouldBeFound("quantiteConvertie.greaterThan=" + SMALLER_QUANTITE_CONVERTIE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where type equals to DEFAULT_TYPE
        defaultLivraisonShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the livraisonList where type equals to UPDATED_TYPE
        defaultLivraisonShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where type not equals to DEFAULT_TYPE
        defaultLivraisonShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the livraisonList where type not equals to UPDATED_TYPE
        defaultLivraisonShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultLivraisonShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the livraisonList where type equals to UPDATED_TYPE
        defaultLivraisonShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where type is not null
        defaultLivraisonShouldBeFound("type.specified=true");

        // Get all the livraisonList where type is null
        defaultLivraisonShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFactureIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where facture equals to DEFAULT_FACTURE
        defaultLivraisonShouldBeFound("facture.equals=" + DEFAULT_FACTURE);

        // Get all the livraisonList where facture equals to UPDATED_FACTURE
        defaultLivraisonShouldNotBeFound("facture.equals=" + UPDATED_FACTURE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFactureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where facture not equals to DEFAULT_FACTURE
        defaultLivraisonShouldNotBeFound("facture.notEquals=" + DEFAULT_FACTURE);

        // Get all the livraisonList where facture not equals to UPDATED_FACTURE
        defaultLivraisonShouldBeFound("facture.notEquals=" + UPDATED_FACTURE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFactureIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where facture in DEFAULT_FACTURE or UPDATED_FACTURE
        defaultLivraisonShouldBeFound("facture.in=" + DEFAULT_FACTURE + "," + UPDATED_FACTURE);

        // Get all the livraisonList where facture equals to UPDATED_FACTURE
        defaultLivraisonShouldNotBeFound("facture.in=" + UPDATED_FACTURE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFactureIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where facture is not null
        defaultLivraisonShouldBeFound("facture.specified=true");

        // Get all the livraisonList where facture is null
        defaultLivraisonShouldNotBeFound("facture.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse equals to DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.equals=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse equals to UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.equals=" + UPDATED_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse not equals to DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.notEquals=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse not equals to UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.notEquals=" + UPDATED_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse in DEFAULT_DATE_BON_CAISSE or UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.in=" + DEFAULT_DATE_BON_CAISSE + "," + UPDATED_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse equals to UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.in=" + UPDATED_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse is not null
        defaultLivraisonShouldBeFound("dateBonCaisse.specified=true");

        // Get all the livraisonList where dateBonCaisse is null
        defaultLivraisonShouldNotBeFound("dateBonCaisse.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse is greater than or equal to DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.greaterThanOrEqual=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse is greater than or equal to UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.greaterThanOrEqual=" + UPDATED_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse is less than or equal to DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.lessThanOrEqual=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse is less than or equal to SMALLER_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.lessThanOrEqual=" + SMALLER_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse is less than DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.lessThan=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse is less than UPDATED_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.lessThan=" + UPDATED_DATE_BON_CAISSE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDateBonCaisseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dateBonCaisse is greater than DEFAULT_DATE_BON_CAISSE
        defaultLivraisonShouldNotBeFound("dateBonCaisse.greaterThan=" + DEFAULT_DATE_BON_CAISSE);

        // Get all the livraisonList where dateBonCaisse is greater than SMALLER_DATE_BON_CAISSE
        defaultLivraisonShouldBeFound("dateBonCaisse.greaterThan=" + SMALLER_DATE_BON_CAISSE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers equals to DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.equals=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers equals to UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.equals=" + UPDATED_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers not equals to DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.notEquals=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers not equals to UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.notEquals=" + UPDATED_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers in DEFAULT_REPARATION_DIVERS or UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.in=" + DEFAULT_REPARATION_DIVERS + "," + UPDATED_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers equals to UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.in=" + UPDATED_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers is not null
        defaultLivraisonShouldBeFound("reparationDivers.specified=true");

        // Get all the livraisonList where reparationDivers is null
        defaultLivraisonShouldNotBeFound("reparationDivers.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers is greater than or equal to DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.greaterThanOrEqual=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers is greater than or equal to UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.greaterThanOrEqual=" + UPDATED_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers is less than or equal to DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.lessThanOrEqual=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers is less than or equal to SMALLER_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.lessThanOrEqual=" + SMALLER_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers is less than DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.lessThan=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers is less than UPDATED_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.lessThan=" + UPDATED_REPARATION_DIVERS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByReparationDiversIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where reparationDivers is greater than DEFAULT_REPARATION_DIVERS
        defaultLivraisonShouldNotBeFound("reparationDivers.greaterThan=" + DEFAULT_REPARATION_DIVERS);

        // Get all the livraisonList where reparationDivers is greater than SMALLER_REPARATION_DIVERS
        defaultLivraisonShouldBeFound("reparationDivers.greaterThan=" + SMALLER_REPARATION_DIVERS);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax equals to DEFAULT_TRAX
        defaultLivraisonShouldBeFound("trax.equals=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax equals to UPDATED_TRAX
        defaultLivraisonShouldNotBeFound("trax.equals=" + UPDATED_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax not equals to DEFAULT_TRAX
        defaultLivraisonShouldNotBeFound("trax.notEquals=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax not equals to UPDATED_TRAX
        defaultLivraisonShouldBeFound("trax.notEquals=" + UPDATED_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax in DEFAULT_TRAX or UPDATED_TRAX
        defaultLivraisonShouldBeFound("trax.in=" + DEFAULT_TRAX + "," + UPDATED_TRAX);

        // Get all the livraisonList where trax equals to UPDATED_TRAX
        defaultLivraisonShouldNotBeFound("trax.in=" + UPDATED_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax is not null
        defaultLivraisonShouldBeFound("trax.specified=true");

        // Get all the livraisonList where trax is null
        defaultLivraisonShouldNotBeFound("trax.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax is greater than or equal to DEFAULT_TRAX
        defaultLivraisonShouldBeFound("trax.greaterThanOrEqual=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax is greater than or equal to UPDATED_TRAX
        defaultLivraisonShouldNotBeFound("trax.greaterThanOrEqual=" + UPDATED_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax is less than or equal to DEFAULT_TRAX
        defaultLivraisonShouldBeFound("trax.lessThanOrEqual=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax is less than or equal to SMALLER_TRAX
        defaultLivraisonShouldNotBeFound("trax.lessThanOrEqual=" + SMALLER_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax is less than DEFAULT_TRAX
        defaultLivraisonShouldNotBeFound("trax.lessThan=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax is less than UPDATED_TRAX
        defaultLivraisonShouldBeFound("trax.lessThan=" + UPDATED_TRAX);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTraxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where trax is greater than DEFAULT_TRAX
        defaultLivraisonShouldNotBeFound("trax.greaterThan=" + DEFAULT_TRAX);

        // Get all the livraisonList where trax is greater than SMALLER_TRAX
        defaultLivraisonShouldBeFound("trax.greaterThan=" + SMALLER_TRAX);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance equals to DEFAULT_BALANCE
        defaultLivraisonShouldBeFound("balance.equals=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance equals to UPDATED_BALANCE
        defaultLivraisonShouldNotBeFound("balance.equals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance not equals to DEFAULT_BALANCE
        defaultLivraisonShouldNotBeFound("balance.notEquals=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance not equals to UPDATED_BALANCE
        defaultLivraisonShouldBeFound("balance.notEquals=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance in DEFAULT_BALANCE or UPDATED_BALANCE
        defaultLivraisonShouldBeFound("balance.in=" + DEFAULT_BALANCE + "," + UPDATED_BALANCE);

        // Get all the livraisonList where balance equals to UPDATED_BALANCE
        defaultLivraisonShouldNotBeFound("balance.in=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance is not null
        defaultLivraisonShouldBeFound("balance.specified=true");

        // Get all the livraisonList where balance is null
        defaultLivraisonShouldNotBeFound("balance.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance is greater than or equal to DEFAULT_BALANCE
        defaultLivraisonShouldBeFound("balance.greaterThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance is greater than or equal to UPDATED_BALANCE
        defaultLivraisonShouldNotBeFound("balance.greaterThanOrEqual=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance is less than or equal to DEFAULT_BALANCE
        defaultLivraisonShouldBeFound("balance.lessThanOrEqual=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance is less than or equal to SMALLER_BALANCE
        defaultLivraisonShouldNotBeFound("balance.lessThanOrEqual=" + SMALLER_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance is less than DEFAULT_BALANCE
        defaultLivraisonShouldNotBeFound("balance.lessThan=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance is less than UPDATED_BALANCE
        defaultLivraisonShouldBeFound("balance.lessThan=" + UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where balance is greater than DEFAULT_BALANCE
        defaultLivraisonShouldNotBeFound("balance.greaterThan=" + DEFAULT_BALANCE);

        // Get all the livraisonList where balance is greater than SMALLER_BALANCE
        defaultLivraisonShouldBeFound("balance.greaterThan=" + SMALLER_BALANCE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance equals to DEFAULT_AVANCE
        defaultLivraisonShouldBeFound("avance.equals=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance equals to UPDATED_AVANCE
        defaultLivraisonShouldNotBeFound("avance.equals=" + UPDATED_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance not equals to DEFAULT_AVANCE
        defaultLivraisonShouldNotBeFound("avance.notEquals=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance not equals to UPDATED_AVANCE
        defaultLivraisonShouldBeFound("avance.notEquals=" + UPDATED_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance in DEFAULT_AVANCE or UPDATED_AVANCE
        defaultLivraisonShouldBeFound("avance.in=" + DEFAULT_AVANCE + "," + UPDATED_AVANCE);

        // Get all the livraisonList where avance equals to UPDATED_AVANCE
        defaultLivraisonShouldNotBeFound("avance.in=" + UPDATED_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance is not null
        defaultLivraisonShouldBeFound("avance.specified=true");

        // Get all the livraisonList where avance is null
        defaultLivraisonShouldNotBeFound("avance.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance is greater than or equal to DEFAULT_AVANCE
        defaultLivraisonShouldBeFound("avance.greaterThanOrEqual=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance is greater than or equal to UPDATED_AVANCE
        defaultLivraisonShouldNotBeFound("avance.greaterThanOrEqual=" + UPDATED_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance is less than or equal to DEFAULT_AVANCE
        defaultLivraisonShouldBeFound("avance.lessThanOrEqual=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance is less than or equal to SMALLER_AVANCE
        defaultLivraisonShouldNotBeFound("avance.lessThanOrEqual=" + SMALLER_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance is less than DEFAULT_AVANCE
        defaultLivraisonShouldNotBeFound("avance.lessThan=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance is less than UPDATED_AVANCE
        defaultLivraisonShouldBeFound("avance.lessThan=" + UPDATED_AVANCE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAvanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where avance is greater than DEFAULT_AVANCE
        defaultLivraisonShouldNotBeFound("avance.greaterThan=" + DEFAULT_AVANCE);

        // Get all the livraisonList where avance is greater than SMALLER_AVANCE
        defaultLivraisonShouldBeFound("avance.greaterThan=" + SMALLER_AVANCE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute equals to DEFAULT_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.equals=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute equals to UPDATED_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.equals=" + UPDATED_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute not equals to DEFAULT_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.notEquals=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute not equals to UPDATED_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.notEquals=" + UPDATED_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute in DEFAULT_AUTOROUTE or UPDATED_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.in=" + DEFAULT_AUTOROUTE + "," + UPDATED_AUTOROUTE);

        // Get all the livraisonList where autoroute equals to UPDATED_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.in=" + UPDATED_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute is not null
        defaultLivraisonShouldBeFound("autoroute.specified=true");

        // Get all the livraisonList where autoroute is null
        defaultLivraisonShouldNotBeFound("autoroute.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute is greater than or equal to DEFAULT_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.greaterThanOrEqual=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute is greater than or equal to UPDATED_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.greaterThanOrEqual=" + UPDATED_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute is less than or equal to DEFAULT_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.lessThanOrEqual=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute is less than or equal to SMALLER_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.lessThanOrEqual=" + SMALLER_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute is less than DEFAULT_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.lessThan=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute is less than UPDATED_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.lessThan=" + UPDATED_AUTOROUTE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByAutorouteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where autoroute is greater than DEFAULT_AUTOROUTE
        defaultLivraisonShouldNotBeFound("autoroute.greaterThan=" + DEFAULT_AUTOROUTE);

        // Get all the livraisonList where autoroute is greater than SMALLER_AUTOROUTE
        defaultLivraisonShouldBeFound("autoroute.greaterThan=" + SMALLER_AUTOROUTE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat equals to DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.equals=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat equals to UPDATED_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.equals=" + UPDATED_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat not equals to DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.notEquals=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat not equals to UPDATED_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.notEquals=" + UPDATED_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat in DEFAULT_DERNIER_ETAT or UPDATED_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.in=" + DEFAULT_DERNIER_ETAT + "," + UPDATED_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat equals to UPDATED_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.in=" + UPDATED_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat is not null
        defaultLivraisonShouldBeFound("dernierEtat.specified=true");

        // Get all the livraisonList where dernierEtat is null
        defaultLivraisonShouldNotBeFound("dernierEtat.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat is greater than or equal to DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.greaterThanOrEqual=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat is greater than or equal to UPDATED_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.greaterThanOrEqual=" + UPDATED_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat is less than or equal to DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.lessThanOrEqual=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat is less than or equal to SMALLER_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.lessThanOrEqual=" + SMALLER_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat is less than DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.lessThan=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat is less than UPDATED_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.lessThan=" + UPDATED_DERNIER_ETAT);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByDernierEtatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where dernierEtat is greater than DEFAULT_DERNIER_ETAT
        defaultLivraisonShouldNotBeFound("dernierEtat.greaterThan=" + DEFAULT_DERNIER_ETAT);

        // Get all the livraisonList where dernierEtat is greater than SMALLER_DERNIER_ETAT
        defaultLivraisonShouldBeFound("dernierEtat.greaterThan=" + SMALLER_DERNIER_ETAT);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse equals to DEFAULT_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.equals=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse equals to UPDATED_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.equals=" + UPDATED_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse not equals to DEFAULT_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.notEquals=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse not equals to UPDATED_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.notEquals=" + UPDATED_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse in DEFAULT_PENALITE_ESE or UPDATED_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.in=" + DEFAULT_PENALITE_ESE + "," + UPDATED_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse equals to UPDATED_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.in=" + UPDATED_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse is not null
        defaultLivraisonShouldBeFound("penaliteEse.specified=true");

        // Get all the livraisonList where penaliteEse is null
        defaultLivraisonShouldNotBeFound("penaliteEse.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse is greater than or equal to DEFAULT_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.greaterThanOrEqual=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse is greater than or equal to UPDATED_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.greaterThanOrEqual=" + UPDATED_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse is less than or equal to DEFAULT_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.lessThanOrEqual=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse is less than or equal to SMALLER_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.lessThanOrEqual=" + SMALLER_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse is less than DEFAULT_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.lessThan=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse is less than UPDATED_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.lessThan=" + UPDATED_PENALITE_ESE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteEseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteEse is greater than DEFAULT_PENALITE_ESE
        defaultLivraisonShouldNotBeFound("penaliteEse.greaterThan=" + DEFAULT_PENALITE_ESE);

        // Get all the livraisonList where penaliteEse is greater than SMALLER_PENALITE_ESE
        defaultLivraisonShouldBeFound("penaliteEse.greaterThan=" + SMALLER_PENALITE_ESE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs equals to DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.equals=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs equals to UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.equals=" + UPDATED_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs not equals to DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.notEquals=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs not equals to UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.notEquals=" + UPDATED_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs in DEFAULT_PENALITE_CHFRS or UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.in=" + DEFAULT_PENALITE_CHFRS + "," + UPDATED_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs equals to UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.in=" + UPDATED_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs is not null
        defaultLivraisonShouldBeFound("penaliteChfrs.specified=true");

        // Get all the livraisonList where penaliteChfrs is null
        defaultLivraisonShouldNotBeFound("penaliteChfrs.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs is greater than or equal to DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.greaterThanOrEqual=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs is greater than or equal to UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.greaterThanOrEqual=" + UPDATED_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs is less than or equal to DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.lessThanOrEqual=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs is less than or equal to SMALLER_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.lessThanOrEqual=" + SMALLER_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs is less than DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.lessThan=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs is less than UPDATED_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.lessThan=" + UPDATED_PENALITE_CHFRS);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByPenaliteChfrsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where penaliteChfrs is greater than DEFAULT_PENALITE_CHFRS
        defaultLivraisonShouldNotBeFound("penaliteChfrs.greaterThan=" + DEFAULT_PENALITE_CHFRS);

        // Get all the livraisonList where penaliteChfrs is greater than SMALLER_PENALITE_CHFRS
        defaultLivraisonShouldBeFound("penaliteChfrs.greaterThan=" + SMALLER_PENALITE_CHFRS);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece equals to DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.equals=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece equals to UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.equals=" + UPDATED_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece not equals to DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.notEquals=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece not equals to UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.notEquals=" + UPDATED_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece in DEFAULT_FRAIS_ESPECE or UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.in=" + DEFAULT_FRAIS_ESPECE + "," + UPDATED_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece equals to UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.in=" + UPDATED_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece is not null
        defaultLivraisonShouldBeFound("fraisEspece.specified=true");

        // Get all the livraisonList where fraisEspece is null
        defaultLivraisonShouldNotBeFound("fraisEspece.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece is greater than or equal to DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.greaterThanOrEqual=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece is greater than or equal to UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.greaterThanOrEqual=" + UPDATED_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece is less than or equal to DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.lessThanOrEqual=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece is less than or equal to SMALLER_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.lessThanOrEqual=" + SMALLER_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece is less than DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.lessThan=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece is less than UPDATED_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.lessThan=" + UPDATED_FRAIS_ESPECE);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByFraisEspeceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where fraisEspece is greater than DEFAULT_FRAIS_ESPECE
        defaultLivraisonShouldNotBeFound("fraisEspece.greaterThan=" + DEFAULT_FRAIS_ESPECE);

        // Get all the livraisonList where fraisEspece is greater than SMALLER_FRAIS_ESPECE
        defaultLivraisonShouldBeFound("fraisEspece.greaterThan=" + SMALLER_FRAIS_ESPECE);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu equals to DEFAULT_RETENU
        defaultLivraisonShouldBeFound("retenu.equals=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu equals to UPDATED_RETENU
        defaultLivraisonShouldNotBeFound("retenu.equals=" + UPDATED_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu not equals to DEFAULT_RETENU
        defaultLivraisonShouldNotBeFound("retenu.notEquals=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu not equals to UPDATED_RETENU
        defaultLivraisonShouldBeFound("retenu.notEquals=" + UPDATED_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu in DEFAULT_RETENU or UPDATED_RETENU
        defaultLivraisonShouldBeFound("retenu.in=" + DEFAULT_RETENU + "," + UPDATED_RETENU);

        // Get all the livraisonList where retenu equals to UPDATED_RETENU
        defaultLivraisonShouldNotBeFound("retenu.in=" + UPDATED_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu is not null
        defaultLivraisonShouldBeFound("retenu.specified=true");

        // Get all the livraisonList where retenu is null
        defaultLivraisonShouldNotBeFound("retenu.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu is greater than or equal to DEFAULT_RETENU
        defaultLivraisonShouldBeFound("retenu.greaterThanOrEqual=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu is greater than or equal to UPDATED_RETENU
        defaultLivraisonShouldNotBeFound("retenu.greaterThanOrEqual=" + UPDATED_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu is less than or equal to DEFAULT_RETENU
        defaultLivraisonShouldBeFound("retenu.lessThanOrEqual=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu is less than or equal to SMALLER_RETENU
        defaultLivraisonShouldNotBeFound("retenu.lessThanOrEqual=" + SMALLER_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu is less than DEFAULT_RETENU
        defaultLivraisonShouldNotBeFound("retenu.lessThan=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu is less than UPDATED_RETENU
        defaultLivraisonShouldBeFound("retenu.lessThan=" + UPDATED_RETENU);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByRetenuIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where retenu is greater than DEFAULT_RETENU
        defaultLivraisonShouldNotBeFound("retenu.greaterThan=" + DEFAULT_RETENU);

        // Get all the livraisonList where retenu is greater than SMALLER_RETENU
        defaultLivraisonShouldBeFound("retenu.greaterThan=" + SMALLER_RETENU);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission equals to DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.equals=" + DEFAULT_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission equals to UPDATED_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.equals=" + SMALLER_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission not equals to DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.notEquals=" + DEFAULT_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission not equals to UPDATED_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.notEquals=" + SMALLER_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsInShouldWork() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission in DEFAULT_TOTAL_COMISSION or UPDATED_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.in=" + DEFAULT_TOTAL_COMISSION + "," + UPDATED_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission equals to UPDATED_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.in=" + SMALLER_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission is not null
        defaultLivraisonShouldBeFound("totalComission.specified=true");

        // Get all the livraisonList where totalComission is null
        defaultLivraisonShouldNotBeFound("totalComission.specified=false");
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission is greater than or equal to DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.greaterThanOrEqual=" + DEFAULT_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission is greater than or equal to UPDATED_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.lessThan=" + UPDATED_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission is less than or equal to DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.lessThanOrEqual=" + DEFAULT_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission is less than or equal to SMALLER_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.lessThanOrEqual=" + SMALLER_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsLessThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission is less than DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.lessThan=" + DEFAULT_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void getAllLivraisonsByTotalComissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList where totalComission is greater than DEFAULT_TOTAL_COMISSION
        defaultLivraisonShouldNotBeFound("totalComission.greaterThan=" + DEFAULT_TOTAL_COMISSION);

        // Get all the livraisonList where totalComission is greater than SMALLER_TOTAL_COMISSION
        defaultLivraisonShouldBeFound("totalComission.greaterThan=" + SMALLER_TOTAL_COMISSION);
    }


    @Test
    @Transactional
    public void getAllLivraisonsByFournisseurIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Fournisseur fournisseur = FournisseurResourceIT.createEntity(em);
        em.persist(fournisseur);
        em.flush();
        livraison.setFournisseur(fournisseur);
        livraisonRepository.saveAndFlush(livraison);
        Long fournisseurId = fournisseur.getId();

        // Get all the livraisonList where fournisseur equals to fournisseurId
        defaultLivraisonShouldBeFound("fournisseurId.equals=" + fournisseurId);

        // Get all the livraisonList where fournisseur equals to fournisseurId + 1
        defaultLivraisonShouldNotBeFound("fournisseurId.equals=" + (fournisseurId + 1));
    }


    @Test
    @Transactional
    public void getAllLivraisonsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        livraison.setClient(client);
        livraisonRepository.saveAndFlush(livraison);
        Long clientId = client.getId();

        // Get all the livraisonList where client equals to clientId
        defaultLivraisonShouldBeFound("clientId.equals=" + clientId);

        // Get all the livraisonList where client equals to clientId + 1
        defaultLivraisonShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllLivraisonsByTransporteurIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Transporteur transporteur = TransporteurResourceIT.createEntity(em);
        em.persist(transporteur);
        em.flush();
        livraison.setTransporteur(transporteur);
        livraisonRepository.saveAndFlush(livraison);
        Long transporteurId = transporteur.getId();

        // Get all the livraisonList where transporteur equals to transporteurId
        defaultLivraisonShouldBeFound("transporteurId.equals=" + transporteurId);

        // Get all the livraisonList where transporteur equals to transporteurId + 1
        defaultLivraisonShouldNotBeFound("transporteurId.equals=" + (transporteurId + 1));
    }


    @Test
    @Transactional
    public void getAllLivraisonsByTrajetIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Trajet trajet = TrajetResourceIT.createEntity(em);
        em.persist(trajet);
        em.flush();
        livraison.setTrajet(trajet);
        livraisonRepository.saveAndFlush(livraison);
        Long trajetId = trajet.getId();

        // Get all the livraisonList where trajet equals to trajetId
        defaultLivraisonShouldBeFound("trajetId.equals=" + trajetId);

        // Get all the livraisonList where trajet equals to trajetId + 1
        defaultLivraisonShouldNotBeFound("trajetId.equals=" + (trajetId + 1));
    }


    @Test
    @Transactional
    public void getAllLivraisonsByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        livraison.setProduit(produit);
        livraisonRepository.saveAndFlush(livraison);
        Long produitId = produit.getId();

        // Get all the livraisonList where produit equals to produitId
        defaultLivraisonShouldBeFound("produitId.equals=" + produitId);

        // Get all the livraisonList where produit equals to produitId + 1
        defaultLivraisonShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }


    @Test
    @Transactional
    public void getAllLivraisonsBySocieteFacturationIsEqualToSomething() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        Societe societeFacturation = SocieteResourceIT.createEntity(em);
        em.persist(societeFacturation);
        em.flush();
        livraison.setSocieteFacturation(societeFacturation);
        livraisonRepository.saveAndFlush(livraison);
        Long societeFacturationId = societeFacturation.getId();

        // Get all the livraisonList where societeFacturation equals to societeFacturationId
        defaultLivraisonShouldBeFound("societeFacturationId.equals=" + societeFacturationId);

        // Get all the livraisonList where societeFacturation equals to societeFacturationId + 1
        defaultLivraisonShouldNotBeFound("societeFacturationId.equals=" + (societeFacturationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLivraisonShouldBeFound(String filter) throws Exception {
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateBonCommande").value(hasItem(DEFAULT_DATE_BON_COMMANDE.toString())))
            .andExpect(jsonPath("$.[*].numeroBonCommande").value(hasItem(DEFAULT_NUMERO_BON_COMMANDE)))
            .andExpect(jsonPath("$.[*].numeroBonLivraison").value(hasItem(DEFAULT_NUMERO_BON_LIVRAISON)))
            .andExpect(jsonPath("$.[*].dateBonLivraison").value(hasItem(DEFAULT_DATE_BON_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].numeroBonFournisseur").value(hasItem(DEFAULT_NUMERO_BON_FOURNISSEUR)))
            .andExpect(jsonPath("$.[*].quantiteVendue").value(hasItem(DEFAULT_QUANTITE_VENDUE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteVente").value(hasItem(DEFAULT_UNITE_VENTE.toString())))
            .andExpect(jsonPath("$.[*].prixTotalVente").value(hasItem(DEFAULT_PRIX_TOTAL_VENTE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteAchetee").value(hasItem(DEFAULT_QUANTITE_ACHETEE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteAchat").value(hasItem(DEFAULT_UNITE_ACHAT.toString())))
            .andExpect(jsonPath("$.[*].prixTotalAchat").value(hasItem(DEFAULT_PRIX_TOTAL_ACHAT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteConvertie").value(hasItem(DEFAULT_QUANTITE_CONVERTIE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].facture").value(hasItem(DEFAULT_FACTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateBonCaisse").value(hasItem(DEFAULT_DATE_BON_CAISSE.toString())))
            .andExpect(jsonPath("$.[*].reparationDivers").value(hasItem(DEFAULT_REPARATION_DIVERS.doubleValue())))
            .andExpect(jsonPath("$.[*].trax").value(hasItem(DEFAULT_TRAX.doubleValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].avance").value(hasItem(DEFAULT_AVANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].autoroute").value(hasItem(DEFAULT_AUTOROUTE.doubleValue())))
            .andExpect(jsonPath("$.[*].dernierEtat").value(hasItem(DEFAULT_DERNIER_ETAT.doubleValue())))
            .andExpect(jsonPath("$.[*].penaliteEse").value(hasItem(DEFAULT_PENALITE_ESE.doubleValue())))
            .andExpect(jsonPath("$.[*].penaliteChfrs").value(hasItem(DEFAULT_PENALITE_CHFRS.doubleValue())))
            .andExpect(jsonPath("$.[*].fraisEspece").value(hasItem(DEFAULT_FRAIS_ESPECE.doubleValue())))
            .andExpect(jsonPath("$.[*].retenu").value(hasItem(DEFAULT_RETENU.doubleValue())))
            .andExpect(jsonPath("$.[*].totalComission").value(hasItem(DEFAULT_TOTAL_COMISSION.doubleValue())));

        // Check, that the count call also returns 1
        restLivraisonMockMvc.perform(get("/api/livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLivraisonShouldNotBeFound(String filter) throws Exception {
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivraisonMockMvc.perform(get("/api/livraisons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLivraison() throws Exception {
        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivraison() throws Exception {
        // Initialize the database
        when(trajetService.getCommissionByTrajet(livraison.getTrajet().getDepart(), livraison.getTrajet().getDestination())).thenReturn(150F);
        when(achatPricer.price(livraison)).thenReturn(100.0F);
        when(ventePricer.price(livraison)).thenReturn(100.0F);
        when(transportPricer.price(livraison)).thenReturn(100.0F);

        livraisonService.save(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison
        Livraison updatedLivraison = livraisonRepository.findById(livraison.getId()).get();
        // Disconnect from session so that the updates on updatedLivraison are not directly saved in db
        em.detach(updatedLivraison);
        updatedLivraison
            .dateBonCommande(UPDATED_DATE_BON_COMMANDE)
            .numeroBonCommande(UPDATED_NUMERO_BON_COMMANDE)
            .numeroBonLivraison(UPDATED_NUMERO_BON_LIVRAISON)
            .dateBonLivraison(UPDATED_DATE_BON_LIVRAISON)
            .numeroBonFournisseur(UPDATED_NUMERO_BON_FOURNISSEUR)
            .quantiteVendue(UPDATED_QUANTITE_VENDUE)
            .uniteVente(UPDATED_UNITE_VENTE)
            .prixTotalVente(UPDATED_PRIX_TOTAL_VENTE)
            .quantiteAchetee(UPDATED_QUANTITE_ACHETEE)
            .uniteAchat(UPDATED_UNITE_ACHAT)
            .prixTotalAchat(UPDATED_PRIX_TOTAL_ACHAT)
            .quantiteConvertie(UPDATED_QUANTITE_CONVERTIE)
            .type(UPDATED_TYPE)
            .facture(UPDATED_FACTURE)
            .dateBonCaisse(UPDATED_DATE_BON_CAISSE)
            .reparationDivers(UPDATED_REPARATION_DIVERS)
            .trax(UPDATED_TRAX)
            .balance(UPDATED_BALANCE)
            .avance(UPDATED_AVANCE)
            .autoroute(UPDATED_AUTOROUTE)
            .dernierEtat(UPDATED_DERNIER_ETAT)
            .penaliteEse(UPDATED_PENALITE_ESE)
            .penaliteChfrs(UPDATED_PENALITE_CHFRS)
            .fraisEspece(UPDATED_FRAIS_ESPECE)
            .retenu(UPDATED_RETENU)
            .totalComission(UPDATED_TOTAL_COMISSION);

        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivraison)))
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getDateBonCommande()).isEqualTo(UPDATED_DATE_BON_COMMANDE);
        assertThat(testLivraison.getNumeroBonCommande()).isEqualTo(UPDATED_NUMERO_BON_COMMANDE);
        assertThat(testLivraison.getNumeroBonLivraison()).isEqualTo(UPDATED_NUMERO_BON_LIVRAISON);
        assertThat(testLivraison.getDateBonLivraison()).isEqualTo(UPDATED_DATE_BON_LIVRAISON);
        assertThat(testLivraison.getNumeroBonFournisseur()).isEqualTo(UPDATED_NUMERO_BON_FOURNISSEUR);
        assertThat(testLivraison.getQuantiteVendue()).isEqualTo(UPDATED_QUANTITE_VENDUE);
        assertThat(testLivraison.getUniteVente()).isEqualTo(UPDATED_UNITE_VENTE);
        assertThat(testLivraison.getPrixTotalVente()).isEqualTo(UPDATED_PRIX_TOTAL_VENTE);
        assertThat(testLivraison.getQuantiteAchetee()).isEqualTo(UPDATED_QUANTITE_ACHETEE);
        assertThat(testLivraison.getUniteAchat()).isEqualTo(UPDATED_UNITE_ACHAT);
        assertThat(testLivraison.getPrixTotalAchat()).isEqualTo(UPDATED_PRIX_TOTAL_ACHAT);
        assertThat(testLivraison.getQuantiteConvertie()).isEqualTo(UPDATED_QUANTITE_CONVERTIE);
        assertThat(testLivraison.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLivraison.isFacture()).isEqualTo(UPDATED_FACTURE);
        assertThat(testLivraison.getDateBonCaisse()).isEqualTo(UPDATED_DATE_BON_CAISSE);
        assertThat(testLivraison.getReparationDivers()).isEqualTo(UPDATED_REPARATION_DIVERS);
        assertThat(testLivraison.getTrax()).isEqualTo(UPDATED_TRAX);
        assertThat(testLivraison.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testLivraison.getAvance()).isEqualTo(UPDATED_AVANCE);
        assertThat(testLivraison.getAutoroute()).isEqualTo(UPDATED_AUTOROUTE);
        assertThat(testLivraison.getDernierEtat()).isEqualTo(UPDATED_DERNIER_ETAT);
        assertThat(testLivraison.getPenaliteEse()).isEqualTo(UPDATED_PENALITE_ESE);
        assertThat(testLivraison.getPenaliteChfrs()).isEqualTo(UPDATED_PENALITE_CHFRS);
        assertThat(testLivraison.getFraisEspece()).isEqualTo(UPDATED_FRAIS_ESPECE);
        assertThat(testLivraison.getRetenu()).isEqualTo(UPDATED_RETENU);
        assertThat(testLivraison.getTotalComission()).isEqualTo(UPDATED_TOTAL_COMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Create the Livraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livraison)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivraison() throws Exception {
        // Initialize the database
        livraisonService.save(livraison);

        int databaseSizeBeforeDelete = livraisonRepository.findAll().size();

        // Delete the livraison
        restLivraisonMockMvc.perform(delete("/api/livraisons/{id}", livraison.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
