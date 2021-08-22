package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.*;
import com.logistica.domain.enumeration.UniteGasoilGros;
import com.logistica.repository.GasoilAchatGrosRepository;
import com.logistica.service.GasoilAchatGrosQueryService;
import com.logistica.service.GasoilAchatGrosService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link GasoilAchatGrosResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class GasoilAchatGrosResourceIT {

    private static final LocalDate DEFAULT_DATE_RECEPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEPTION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_RECEPTION = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NUMERO_BON_RECEPTION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_BON_RECEPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITY = 0F;
    private static final Float UPDATED_QUANTITY = 1F;
    private static final Float SMALLER_QUANTITY = 0F - 1F;

    private static final Float DEFAULT_PRIX_UNITAIRE = 0F;
    private static final Float UPDATED_PRIX_UNITAIRE = 1F;
    private static final Float SMALLER_PRIX_UNITAIRE = 0F - 1F;

    private static final UniteGasoilGros DEFAULT_UNITE_GASOIL_GROS = UniteGasoilGros.TONNE;
    private static final UniteGasoilGros UPDATED_UNITE_GASOIL_GROS = UniteGasoilGros.TONNE;

    @Autowired
    private GasoilAchatGrosRepository gasoilAchatGrosRepository;

    @Autowired
    private GasoilAchatGrosService gasoilAchatGrosService;

    @Autowired
    private GasoilAchatGrosQueryService gasoilAchatGrosQueryService;

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

    private MockMvc restGasoilAchatGrosMockMvc;

    private GasoilAchatGros gasoilAchatGros;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GasoilAchatGrosResource gasoilAchatGrosResource = new GasoilAchatGrosResource(gasoilAchatGrosService, gasoilAchatGrosQueryService);
        this.restGasoilAchatGrosMockMvc = MockMvcBuilders.standaloneSetup(gasoilAchatGrosResource)
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
    public static GasoilAchatGros createEntity(EntityManager em) {
        GasoilAchatGros gasoilAchatGros = new GasoilAchatGros()
            .dateReception(DEFAULT_DATE_RECEPTION)
            .numeroBonReception(DEFAULT_NUMERO_BON_RECEPTION)
            .description(DEFAULT_DESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .uniteGasoilGros(DEFAULT_UNITE_GASOIL_GROS);
        // Add required entity
        FournisseurGrossiste fournisseur;
        if (TestUtil.findAll(em, Fournisseur.class).isEmpty()) {
            fournisseur = FournisseurGrossisteResourceIT.createEntity(em);
            em.persist(fournisseur);
            em.flush();
        } else {
            fournisseur = TestUtil.findAll(em, FournisseurGrossiste.class).get(0);
        }
        gasoilAchatGros.setFournisseurGrossiste(fournisseur);
        // Add required entity
        Societe societe;
        if (TestUtil.findAll(em, Societe.class).isEmpty()) {
            societe = SocieteResourceIT.createEntity(em);
            em.persist(societe);
            em.flush();
        } else {
            societe = TestUtil.findAll(em, Societe.class).get(0);
        }
        gasoilAchatGros.setAcheteur(societe);
        // Add required entity
        Carburant produit;
        if (TestUtil.findAll(em, Carburant.class).isEmpty()) {
            produit = CarburantResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Carburant.class).get(0);
        }
        gasoilAchatGros.setCarburant(produit);
        return gasoilAchatGros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GasoilAchatGros createUpdatedEntity(EntityManager em) {
        GasoilAchatGros gasoilAchatGros = new GasoilAchatGros()
            .dateReception(UPDATED_DATE_RECEPTION)
            .numeroBonReception(UPDATED_NUMERO_BON_RECEPTION)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .uniteGasoilGros(UPDATED_UNITE_GASOIL_GROS);
        // Add required entity
        FournisseurGrossiste fournisseurGrossiste;
        if (TestUtil.findAll(em, FournisseurGrossiste.class).isEmpty()) {
            fournisseurGrossiste = FournisseurGrossisteResourceIT.createUpdatedEntity(em);
            em.persist(fournisseurGrossiste);
            em.flush();
        } else {
            fournisseurGrossiste = TestUtil.findAll(em, FournisseurGrossiste.class).get(0);
        }
        gasoilAchatGros.setFournisseurGrossiste(fournisseurGrossiste);
        // Add required entity
        Societe societe;
        if (TestUtil.findAll(em, Societe.class).isEmpty()) {
            societe = SocieteResourceIT.createUpdatedEntity(em);
            em.persist(societe);
            em.flush();
        } else {
            societe = TestUtil.findAll(em, Societe.class).get(0);
        }
        gasoilAchatGros.setAcheteur(societe);
        // Add required entity
        Carburant carburant;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            carburant = CarburantResourceIT.createUpdatedEntity(em);
            em.persist(carburant);
            em.flush();
        } else {
            carburant = TestUtil.findAll(em, Carburant.class).get(0);
        }
        gasoilAchatGros.setCarburant(carburant);
        return gasoilAchatGros;
    }

    @BeforeEach
    public void initTest() {
        gasoilAchatGros = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasoilAchatGros() throws Exception {
        int databaseSizeBeforeCreate = gasoilAchatGrosRepository.findAll().size();

        // Create the GasoilAchatGros
        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isCreated());

        // Validate the GasoilAchatGros in the database
        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeCreate + 1);
        GasoilAchatGros testGasoilAchatGros = gasoilAchatGrosList.get(gasoilAchatGrosList.size() - 1);
        assertThat(testGasoilAchatGros.getDateReception()).isEqualTo(DEFAULT_DATE_RECEPTION);
        assertThat(testGasoilAchatGros.getNumeroBonReception()).isEqualTo(DEFAULT_NUMERO_BON_RECEPTION);
        assertThat(testGasoilAchatGros.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGasoilAchatGros.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testGasoilAchatGros.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testGasoilAchatGros.getUniteGasoilGros()).isEqualTo(DEFAULT_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void createGasoilAchatGrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gasoilAchatGrosRepository.findAll().size();

        // Create the GasoilAchatGros with an existing ID
        gasoilAchatGros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilAchatGros in the database
        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateReceptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilAchatGrosRepository.findAll().size();
        // set the field null
        gasoilAchatGros.setDateReception(null);

        // Create the GasoilAchatGros, which fails.

        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroBonReceptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilAchatGrosRepository.findAll().size();
        // set the field null
        gasoilAchatGros.setNumeroBonReception(null);

        // Create the GasoilAchatGros, which fails.

        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilAchatGrosRepository.findAll().size();
        // set the field null
        gasoilAchatGros.setQuantity(null);

        // Create the GasoilAchatGros, which fails.

        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixUnitaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilAchatGrosRepository.findAll().size();
        // set the field null
        gasoilAchatGros.setPrixUnitaire(null);

        // Create the GasoilAchatGros, which fails.

        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUniteGasoilGrosIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilAchatGrosRepository.findAll().size();
        // set the field null
        gasoilAchatGros.setUniteGasoilGros(null);

        // Create the GasoilAchatGros, which fails.

        restGasoilAchatGrosMockMvc.perform(post("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGros() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoilAchatGros.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateReception").value(hasItem(DEFAULT_DATE_RECEPTION.toString())))
            .andExpect(jsonPath("$.[*].numeroBonReception").value(hasItem(DEFAULT_NUMERO_BON_RECEPTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteGasoilGros").value(hasItem(DEFAULT_UNITE_GASOIL_GROS.toString())));
    }

    @Test
    @Transactional
    public void getGasoilAchatGros() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get the gasoilAchatGros
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros/{id}", gasoilAchatGros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasoilAchatGros.getId().intValue()))
            .andExpect(jsonPath("$.dateReception").value(DEFAULT_DATE_RECEPTION.toString()))
            .andExpect(jsonPath("$.numeroBonReception").value(DEFAULT_NUMERO_BON_RECEPTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.uniteGasoilGros").value(DEFAULT_UNITE_GASOIL_GROS.toString()));
    }


    @Test
    @Transactional
    public void getGasoilAchatGrosByIdFiltering() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        Long id = gasoilAchatGros.getId();

        defaultGasoilAchatGrosShouldBeFound("id.equals=" + id);
        defaultGasoilAchatGrosShouldNotBeFound("id.notEquals=" + id);

        defaultGasoilAchatGrosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGasoilAchatGrosShouldNotBeFound("id.greaterThan=" + id);

        defaultGasoilAchatGrosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGasoilAchatGrosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception equals to DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.equals=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception equals to UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.equals=" + UPDATED_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception not equals to DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.notEquals=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception not equals to UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.notEquals=" + UPDATED_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception in DEFAULT_DATE_RECEPTION or UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.in=" + DEFAULT_DATE_RECEPTION + "," + UPDATED_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception equals to UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.in=" + UPDATED_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception is not null
        defaultGasoilAchatGrosShouldBeFound("dateReception.specified=true");

        // Get all the gasoilAchatGrosList where dateReception is null
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception is greater than or equal to DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.greaterThanOrEqual=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception is greater than or equal to UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.greaterThanOrEqual=" + UPDATED_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception is less than or equal to DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.lessThanOrEqual=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception is less than or equal to SMALLER_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.lessThanOrEqual=" + SMALLER_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception is less than DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.lessThan=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception is less than UPDATED_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.lessThan=" + UPDATED_DATE_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDateReceptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where dateReception is greater than DEFAULT_DATE_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("dateReception.greaterThan=" + DEFAULT_DATE_RECEPTION);

        // Get all the gasoilAchatGrosList where dateReception is greater than SMALLER_DATE_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("dateReception.greaterThan=" + SMALLER_DATE_RECEPTION);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception equals to DEFAULT_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.equals=" + DEFAULT_NUMERO_BON_RECEPTION);

        // Get all the gasoilAchatGrosList where numeroBonReception equals to UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.equals=" + UPDATED_NUMERO_BON_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception not equals to DEFAULT_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.notEquals=" + DEFAULT_NUMERO_BON_RECEPTION);

        // Get all the gasoilAchatGrosList where numeroBonReception not equals to UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.notEquals=" + UPDATED_NUMERO_BON_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception in DEFAULT_NUMERO_BON_RECEPTION or UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.in=" + DEFAULT_NUMERO_BON_RECEPTION + "," + UPDATED_NUMERO_BON_RECEPTION);

        // Get all the gasoilAchatGrosList where numeroBonReception equals to UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.in=" + UPDATED_NUMERO_BON_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception is not null
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.specified=true");

        // Get all the gasoilAchatGrosList where numeroBonReception is null
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionContainsSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception contains DEFAULT_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.contains=" + DEFAULT_NUMERO_BON_RECEPTION);

        // Get all the gasoilAchatGrosList where numeroBonReception contains UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.contains=" + UPDATED_NUMERO_BON_RECEPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByNumeroBonReceptionNotContainsSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where numeroBonReception does not contain DEFAULT_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldNotBeFound("numeroBonReception.doesNotContain=" + DEFAULT_NUMERO_BON_RECEPTION);

        // Get all the gasoilAchatGrosList where numeroBonReception does not contain UPDATED_NUMERO_BON_RECEPTION
        defaultGasoilAchatGrosShouldBeFound("numeroBonReception.doesNotContain=" + UPDATED_NUMERO_BON_RECEPTION);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description equals to DEFAULT_DESCRIPTION
        defaultGasoilAchatGrosShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the gasoilAchatGrosList where description equals to UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description not equals to DEFAULT_DESCRIPTION
        defaultGasoilAchatGrosShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the gasoilAchatGrosList where description not equals to UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the gasoilAchatGrosList where description equals to UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description is not null
        defaultGasoilAchatGrosShouldBeFound("description.specified=true");

        // Get all the gasoilAchatGrosList where description is null
        defaultGasoilAchatGrosShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description contains DEFAULT_DESCRIPTION
        defaultGasoilAchatGrosShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the gasoilAchatGrosList where description contains UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where description does not contain DEFAULT_DESCRIPTION
        defaultGasoilAchatGrosShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the gasoilAchatGrosList where description does not contain UPDATED_DESCRIPTION
        defaultGasoilAchatGrosShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity equals to DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity equals to UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity not equals to DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity not equals to UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity equals to UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity is not null
        defaultGasoilAchatGrosShouldBeFound("quantity.specified=true");

        // Get all the gasoilAchatGrosList where quantity is null
        defaultGasoilAchatGrosShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity is less than or equal to SMALLER_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity is less than DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity is less than UPDATED_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where quantity is greater than DEFAULT_QUANTITY
        defaultGasoilAchatGrosShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the gasoilAchatGrosList where quantity is greater than SMALLER_QUANTITY
        defaultGasoilAchatGrosShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire equals to DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.equals=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire equals to UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.equals=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire not equals to DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.notEquals=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire not equals to UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.notEquals=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire in DEFAULT_PRIX_UNITAIRE or UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.in=" + DEFAULT_PRIX_UNITAIRE + "," + UPDATED_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire equals to UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.in=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire is not null
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.specified=true");

        // Get all the gasoilAchatGrosList where prixUnitaire is null
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire is greater than or equal to DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.greaterThanOrEqual=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire is greater than or equal to UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.greaterThanOrEqual=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire is less than or equal to DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.lessThanOrEqual=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire is less than or equal to SMALLER_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.lessThanOrEqual=" + SMALLER_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire is less than DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.lessThan=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire is less than UPDATED_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.lessThan=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByPrixUnitaireIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where prixUnitaire is greater than DEFAULT_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldNotBeFound("prixUnitaire.greaterThan=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the gasoilAchatGrosList where prixUnitaire is greater than SMALLER_PRIX_UNITAIRE
        defaultGasoilAchatGrosShouldBeFound("prixUnitaire.greaterThan=" + SMALLER_PRIX_UNITAIRE);
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByUniteGasoilGrosIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where uniteGasoilGros equals to DEFAULT_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldBeFound("uniteGasoilGros.equals=" + DEFAULT_UNITE_GASOIL_GROS);

        // Get all the gasoilAchatGrosList where uniteGasoilGros equals to UPDATED_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldNotBeFound("uniteGasoilGros.equals=" + UPDATED_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByUniteGasoilGrosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where uniteGasoilGros not equals to DEFAULT_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldNotBeFound("uniteGasoilGros.notEquals=" + DEFAULT_UNITE_GASOIL_GROS);

        // Get all the gasoilAchatGrosList where uniteGasoilGros not equals to UPDATED_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldBeFound("uniteGasoilGros.notEquals=" + UPDATED_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByUniteGasoilGrosIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where uniteGasoilGros in DEFAULT_UNITE_GASOIL_GROS or UPDATED_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldBeFound("uniteGasoilGros.in=" + DEFAULT_UNITE_GASOIL_GROS + "," + UPDATED_UNITE_GASOIL_GROS);

        // Get all the gasoilAchatGrosList where uniteGasoilGros equals to UPDATED_UNITE_GASOIL_GROS
        defaultGasoilAchatGrosShouldNotBeFound("uniteGasoilGros.in=" + UPDATED_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByUniteGasoilGrosIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);

        // Get all the gasoilAchatGrosList where uniteGasoilGros is not null
        defaultGasoilAchatGrosShouldBeFound("uniteGasoilGros.specified=true");

        // Get all the gasoilAchatGrosList where uniteGasoilGros is null
        defaultGasoilAchatGrosShouldNotBeFound("uniteGasoilGros.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilAchatGrosByFournisseurIsEqualToSomething() throws Exception {
        // Get already existing entity
        FournisseurGrossiste fournisseurGrossiste = gasoilAchatGros.getFournisseurGrossiste();
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);
        Long fournisseurId = fournisseurGrossiste.getId();

        // Get all the gasoilAchatGrosList where fournisseur equals to fournisseurId
        defaultGasoilAchatGrosShouldBeFound("fournisseurId.equals=" + fournisseurId);

        // Get all the gasoilAchatGrosList where fournisseur equals to fournisseurId + 1
        defaultGasoilAchatGrosShouldNotBeFound("fournisseurId.equals=" + (fournisseurId + 1));
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByTransporteurIsEqualToSomething() throws Exception {
        // Get already existing entity
        Societe transporteur = gasoilAchatGros.getAcheteur();
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);
        Long transporteurId = transporteur.getId();

        // Get all the gasoilAchatGrosList where transporteur equals to transporteurId
        defaultGasoilAchatGrosShouldBeFound("transporteurId.equals=" + transporteurId);

        // Get all the gasoilAchatGrosList where transporteur equals to transporteurId + 1
        defaultGasoilAchatGrosShouldNotBeFound("transporteurId.equals=" + (transporteurId + 1));
    }


    @Test
    @Transactional
    public void getAllGasoilAchatGrosByProduitIsEqualToSomething() throws Exception {
        // Get already existing entity
        Carburant carburant = gasoilAchatGros.getCarburant();
        gasoilAchatGrosRepository.saveAndFlush(gasoilAchatGros);
        Long produitId = carburant.getId();

        // Get all the gasoilAchatGrosList where carburant equals to produitId
        defaultGasoilAchatGrosShouldBeFound("produitId.equals=" + produitId);

        // Get all the gasoilAchatGrosList where carburant equals to produitId + 1
        defaultGasoilAchatGrosShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGasoilAchatGrosShouldBeFound(String filter) throws Exception {
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoilAchatGros.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateReception").value(hasItem(DEFAULT_DATE_RECEPTION.toString())))
            .andExpect(jsonPath("$.[*].numeroBonReception").value(hasItem(DEFAULT_NUMERO_BON_RECEPTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteGasoilGros").value(hasItem(DEFAULT_UNITE_GASOIL_GROS.toString())));

        // Check, that the count call also returns 1
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGasoilAchatGrosShouldNotBeFound(String filter) throws Exception {
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGasoilAchatGros() throws Exception {
        // Get the gasoilAchatGros
        restGasoilAchatGrosMockMvc.perform(get("/api/gasoil-achat-gros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasoilAchatGros() throws Exception {
        // Initialize the database
        gasoilAchatGrosService.save(gasoilAchatGros);

        int databaseSizeBeforeUpdate = gasoilAchatGrosRepository.findAll().size();

        // Update the gasoilAchatGros
        GasoilAchatGros updatedGasoilAchatGros = gasoilAchatGrosRepository.findById(gasoilAchatGros.getId()).get();
        // Disconnect from session so that the updates on updatedGasoilAchatGros are not directly saved in db
        em.detach(updatedGasoilAchatGros);
        updatedGasoilAchatGros
            .dateReception(UPDATED_DATE_RECEPTION)
            .numeroBonReception(UPDATED_NUMERO_BON_RECEPTION)
            .description(UPDATED_DESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .uniteGasoilGros(UPDATED_UNITE_GASOIL_GROS);

        restGasoilAchatGrosMockMvc.perform(put("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGasoilAchatGros)))
            .andExpect(status().isOk());

        // Validate the GasoilAchatGros in the database
        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeUpdate);
        GasoilAchatGros testGasoilAchatGros = gasoilAchatGrosList.get(gasoilAchatGrosList.size() - 1);
        assertThat(testGasoilAchatGros.getDateReception()).isEqualTo(UPDATED_DATE_RECEPTION);
        assertThat(testGasoilAchatGros.getNumeroBonReception()).isEqualTo(UPDATED_NUMERO_BON_RECEPTION);
        assertThat(testGasoilAchatGros.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGasoilAchatGros.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testGasoilAchatGros.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testGasoilAchatGros.getUniteGasoilGros()).isEqualTo(UPDATED_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void updateNonExistingGasoilAchatGros() throws Exception {
        int databaseSizeBeforeUpdate = gasoilAchatGrosRepository.findAll().size();

        // Create the GasoilAchatGros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasoilAchatGrosMockMvc.perform(put("/api/gasoil-achat-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilAchatGros)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilAchatGros in the database
        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasoilAchatGros() throws Exception {
        // Initialize the database
        gasoilAchatGrosService.save(gasoilAchatGros);

        int databaseSizeBeforeDelete = gasoilAchatGrosRepository.findAll().size();

        // Delete the gasoilAchatGros
        restGasoilAchatGrosMockMvc.perform(delete("/api/gasoil-achat-gros/{id}", gasoilAchatGros.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GasoilAchatGros> gasoilAchatGrosList = gasoilAchatGrosRepository.findAll();
        assertThat(gasoilAchatGrosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
