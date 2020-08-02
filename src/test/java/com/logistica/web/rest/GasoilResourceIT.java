package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Gasoil;
import com.logistica.domain.Transporteur;
import com.logistica.repository.GasoilRepository;
import com.logistica.service.GasoilQueryService;
import com.logistica.service.GasoilService;
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
import java.util.List;

import static com.logistica.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GasoilResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class GasoilResourceIT {

    private static final String DEFAULT_SOCIETE = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_BON_GASOIL = 1L;
    private static final Long UPDATED_NUMERO_BON_GASOIL = 2L;
    private static final Long SMALLER_NUMERO_BON_GASOIL = 1L - 1L;

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final Float DEFAULT_QUANTITE_EN_LITRE = 1F;
    private static final Float UPDATED_QUANTITE_EN_LITRE = 2F;
    private static final Float SMALLER_QUANTITE_EN_LITRE = 1F - 1F;

    private static final Float DEFAULT_PRIX_DU_LITRE = 1F;
    private static final Float UPDATED_PRIX_DU_LITRE = 2F;
    private static final Float SMALLER_PRIX_DU_LITRE = 1F - 1F;

    private static final Float DEFAULT_PRIX_TOTAL_GASOIL = 1F;
    private static final Float UPDATED_PRIX_TOTAL_GASOIL = 2F;
    private static final Float SMALLER_PRIX_TOTAL_GASOIL = 1F - 1F;

    private static final Integer DEFAULT_KILOMETRAGE_INITIAL = 1;
    private static final Integer UPDATED_KILOMETRAGE_INITIAL = 2;
    private static final Integer SMALLER_KILOMETRAGE_INITIAL = 1 - 1;

    private static final Integer DEFAULT_KILOMETRAGE_FINAL = 1;
    private static final Integer UPDATED_KILOMETRAGE_FINAL = 2;
    private static final Integer SMALLER_KILOMETRAGE_FINAL = 1 - 1;

    private static final Integer DEFAULT_KILOMETRAGE_PARCOURU = 1;
    private static final Integer UPDATED_KILOMETRAGE_PARCOURU = 2;
    private static final Integer SMALLER_KILOMETRAGE_PARCOURU = 1 - 1;

    @Autowired
    private GasoilRepository gasoilRepository;

    @Autowired
    private GasoilService gasoilService;

    @Autowired
    private GasoilQueryService gasoilQueryService;

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

    private MockMvc restGasoilMockMvc;

    private Gasoil gasoil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GasoilResource gasoilResource = new GasoilResource(gasoilService, gasoilQueryService);
        this.restGasoilMockMvc = MockMvcBuilders.standaloneSetup(gasoilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gasoil createEntity(EntityManager em) {
        Gasoil gasoil = new Gasoil()
            .societe(DEFAULT_SOCIETE)
            .numeroBonGasoil(DEFAULT_NUMERO_BON_GASOIL)
            .quantiteEnLitre(DEFAULT_QUANTITE_EN_LITRE)
            .prixDuLitre(DEFAULT_PRIX_DU_LITRE)
            .prixTotalGasoil(DEFAULT_PRIX_TOTAL_GASOIL)
            .kilometrageInitial(DEFAULT_KILOMETRAGE_INITIAL)
            .kilometrageFinal(DEFAULT_KILOMETRAGE_FINAL)
            .kilometrageParcouru(DEFAULT_KILOMETRAGE_PARCOURU);
        return gasoil;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gasoil createUpdatedEntity(EntityManager em) {
        Gasoil gasoil = new Gasoil()
            .societe(UPDATED_SOCIETE)
            .numeroBonGasoil(UPDATED_NUMERO_BON_GASOIL)
            .quantiteEnLitre(UPDATED_QUANTITE_EN_LITRE)
            .prixDuLitre(UPDATED_PRIX_DU_LITRE)
            .prixTotalGasoil(UPDATED_PRIX_TOTAL_GASOIL)
            .kilometrageInitial(UPDATED_KILOMETRAGE_INITIAL)
            .kilometrageFinal(UPDATED_KILOMETRAGE_FINAL)
            .kilometrageParcouru(UPDATED_KILOMETRAGE_PARCOURU);
        return gasoil;
    }

    @BeforeEach
    public void initTest() {
        gasoil = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasoil() throws Exception {
        int databaseSizeBeforeCreate = gasoilRepository.findAll().size();

        // Create the Gasoil
        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isCreated());

        // Validate the Gasoil in the database
        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeCreate + 1);
        Gasoil testGasoil = gasoilList.get(gasoilList.size() - 1);
        assertThat(testGasoil.getSociete()).isEqualTo(DEFAULT_SOCIETE);
        assertThat(testGasoil.getNumeroBonGasoil()).isEqualTo(DEFAULT_NUMERO_BON_GASOIL);
        assertThat(testGasoil.getQuantiteEnLitre()).isEqualTo(DEFAULT_QUANTITE_EN_LITRE);
        assertThat(testGasoil.getPrixDuLitre()).isEqualTo(DEFAULT_PRIX_DU_LITRE);
        assertThat(testGasoil.getPrixTotalGasoil()).isEqualTo(DEFAULT_PRIX_TOTAL_GASOIL);
        assertThat(testGasoil.getKilometrageInitial()).isEqualTo(DEFAULT_KILOMETRAGE_INITIAL);
        assertThat(testGasoil.getKilometrageFinal()).isEqualTo(DEFAULT_KILOMETRAGE_FINAL);
        assertThat(testGasoil.getKilometrageParcouru()).isEqualTo(DEFAULT_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void createGasoilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gasoilRepository.findAll().size();

        // Create the Gasoil with an existing ID
        gasoil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        // Validate the Gasoil in the database
        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSocieteIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilRepository.findAll().size();
        // set the field null
        gasoil.setSociete(null);

        // Create the Gasoil, which fails.

        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroBonGasoilIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilRepository.findAll().size();
        // set the field null
        gasoil.setNumeroBonGasoil(null);

        // Create the Gasoil, which fails.

        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilRepository.findAll().size();
        // set the field null

        // Create the Gasoil, which fails.

        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteEnLitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilRepository.findAll().size();
        // set the field null
        gasoil.setQuantiteEnLitre(null);

        // Create the Gasoil, which fails.

        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixDuLitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilRepository.findAll().size();
        // set the field null
        gasoil.setPrixDuLitre(null);

        // Create the Gasoil, which fails.

        restGasoilMockMvc.perform(post("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGasoils() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList
        restGasoilMockMvc.perform(get("/api/gasoils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoil.getId().intValue())))
            .andExpect(jsonPath("$.[*].societe").value(hasItem(DEFAULT_SOCIETE)))
            .andExpect(jsonPath("$.[*].numeroBonGasoil").value(hasItem(DEFAULT_NUMERO_BON_GASOIL.intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].quantiteEnLitre").value(hasItem(DEFAULT_QUANTITE_EN_LITRE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixDuLitre").value(hasItem(DEFAULT_PRIX_DU_LITRE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTotalGasoil").value(hasItem(DEFAULT_PRIX_TOTAL_GASOIL.doubleValue())))
            .andExpect(jsonPath("$.[*].kilometrageInitial").value(hasItem(DEFAULT_KILOMETRAGE_INITIAL)))
            .andExpect(jsonPath("$.[*].kilometrageFinal").value(hasItem(DEFAULT_KILOMETRAGE_FINAL)))
            .andExpect(jsonPath("$.[*].kilometrageParcouru").value(hasItem(DEFAULT_KILOMETRAGE_PARCOURU)));
    }

    @Test
    @Transactional
    public void getGasoil() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get the gasoil
        restGasoilMockMvc.perform(get("/api/gasoils/{id}", gasoil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasoil.getId().intValue()))
            .andExpect(jsonPath("$.societe").value(DEFAULT_SOCIETE))
            .andExpect(jsonPath("$.numeroBonGasoil").value(DEFAULT_NUMERO_BON_GASOIL.intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.quantiteEnLitre").value(DEFAULT_QUANTITE_EN_LITRE.doubleValue()))
            .andExpect(jsonPath("$.prixDuLitre").value(DEFAULT_PRIX_DU_LITRE.doubleValue()))
            .andExpect(jsonPath("$.prixTotalGasoil").value(DEFAULT_PRIX_TOTAL_GASOIL.doubleValue()))
            .andExpect(jsonPath("$.kilometrageInitial").value(DEFAULT_KILOMETRAGE_INITIAL))
            .andExpect(jsonPath("$.kilometrageFinal").value(DEFAULT_KILOMETRAGE_FINAL))
            .andExpect(jsonPath("$.kilometrageParcouru").value(DEFAULT_KILOMETRAGE_PARCOURU));
    }


    @Test
    @Transactional
    public void getGasoilsByIdFiltering() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        Long id = gasoil.getId();

        defaultGasoilShouldBeFound("id.equals=" + id);
        defaultGasoilShouldNotBeFound("id.notEquals=" + id);

        defaultGasoilShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGasoilShouldNotBeFound("id.greaterThan=" + id);

        defaultGasoilShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGasoilShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGasoilsBySocieteIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe equals to DEFAULT_SOCIETE
        defaultGasoilShouldBeFound("societe.equals=" + DEFAULT_SOCIETE);

        // Get all the gasoilList where societe equals to UPDATED_SOCIETE
        defaultGasoilShouldNotBeFound("societe.equals=" + UPDATED_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllGasoilsBySocieteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe not equals to DEFAULT_SOCIETE
        defaultGasoilShouldNotBeFound("societe.notEquals=" + DEFAULT_SOCIETE);

        // Get all the gasoilList where societe not equals to UPDATED_SOCIETE
        defaultGasoilShouldBeFound("societe.notEquals=" + UPDATED_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllGasoilsBySocieteIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe in DEFAULT_SOCIETE or UPDATED_SOCIETE
        defaultGasoilShouldBeFound("societe.in=" + DEFAULT_SOCIETE + "," + UPDATED_SOCIETE);

        // Get all the gasoilList where societe equals to UPDATED_SOCIETE
        defaultGasoilShouldNotBeFound("societe.in=" + UPDATED_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllGasoilsBySocieteIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe is not null
        defaultGasoilShouldBeFound("societe.specified=true");

        // Get all the gasoilList where societe is null
        defaultGasoilShouldNotBeFound("societe.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsBySocieteContainsSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe contains DEFAULT_SOCIETE
        defaultGasoilShouldBeFound("societe.contains=" + DEFAULT_SOCIETE);

        // Get all the gasoilList where societe contains UPDATED_SOCIETE
        defaultGasoilShouldNotBeFound("societe.contains=" + UPDATED_SOCIETE);
    }

    @Test
    @Transactional
    public void getAllGasoilsBySocieteNotContainsSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where societe does not contain DEFAULT_SOCIETE
        defaultGasoilShouldNotBeFound("societe.doesNotContain=" + DEFAULT_SOCIETE);

        // Get all the gasoilList where societe does not contain UPDATED_SOCIETE
        defaultGasoilShouldBeFound("societe.doesNotContain=" + UPDATED_SOCIETE);
    }


    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil equals to DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.equals=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil equals to UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.equals=" + UPDATED_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil not equals to DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.notEquals=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil not equals to UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.notEquals=" + UPDATED_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil in DEFAULT_NUMERO_BON_GASOIL or UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.in=" + DEFAULT_NUMERO_BON_GASOIL + "," + UPDATED_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil equals to UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.in=" + UPDATED_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil is not null
        defaultGasoilShouldBeFound("numeroBonGasoil.specified=true");

        // Get all the gasoilList where numeroBonGasoil is null
        defaultGasoilShouldNotBeFound("numeroBonGasoil.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil is greater than or equal to DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.greaterThanOrEqual=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil is greater than or equal to UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.greaterThanOrEqual=" + UPDATED_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil is less than or equal to DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.lessThanOrEqual=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil is less than or equal to SMALLER_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.lessThanOrEqual=" + SMALLER_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil is less than DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.lessThan=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil is less than UPDATED_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.lessThan=" + UPDATED_NUMERO_BON_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByNumeroBonGasoilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where numeroBonGasoil is greater than DEFAULT_NUMERO_BON_GASOIL
        defaultGasoilShouldNotBeFound("numeroBonGasoil.greaterThan=" + DEFAULT_NUMERO_BON_GASOIL);

        // Get all the gasoilList where numeroBonGasoil is greater than SMALLER_NUMERO_BON_GASOIL
        defaultGasoilShouldBeFound("numeroBonGasoil.greaterThan=" + SMALLER_NUMERO_BON_GASOIL);
    }


    @Test
    @Transactional
    public void getAllGasoilsByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule equals to DEFAULT_MATRICULE
        defaultGasoilShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the gasoilList where matricule equals to UPDATED_MATRICULE
        defaultGasoilShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule not equals to DEFAULT_MATRICULE
        defaultGasoilShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the gasoilList where matricule not equals to UPDATED_MATRICULE
        defaultGasoilShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultGasoilShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the gasoilList where matricule equals to UPDATED_MATRICULE
        defaultGasoilShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule is not null
        defaultGasoilShouldBeFound("matricule.specified=true");

        // Get all the gasoilList where matricule is null
        defaultGasoilShouldNotBeFound("matricule.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule contains DEFAULT_MATRICULE
        defaultGasoilShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the gasoilList where matricule contains UPDATED_MATRICULE
        defaultGasoilShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where matricule does not contain DEFAULT_MATRICULE
        defaultGasoilShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the gasoilList where matricule does not contain UPDATED_MATRICULE
        defaultGasoilShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre equals to DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.equals=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre equals to UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.equals=" + UPDATED_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre not equals to DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.notEquals=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre not equals to UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.notEquals=" + UPDATED_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre in DEFAULT_QUANTITE_EN_LITRE or UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.in=" + DEFAULT_QUANTITE_EN_LITRE + "," + UPDATED_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre equals to UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.in=" + UPDATED_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre is not null
        defaultGasoilShouldBeFound("quantiteEnLitre.specified=true");

        // Get all the gasoilList where quantiteEnLitre is null
        defaultGasoilShouldNotBeFound("quantiteEnLitre.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre is greater than or equal to DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.greaterThanOrEqual=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre is greater than or equal to UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.greaterThanOrEqual=" + UPDATED_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre is less than or equal to DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.lessThanOrEqual=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre is less than or equal to SMALLER_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.lessThanOrEqual=" + SMALLER_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre is less than DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.lessThan=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre is less than UPDATED_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.lessThan=" + UPDATED_QUANTITE_EN_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByQuantiteEnLitreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where quantiteEnLitre is greater than DEFAULT_QUANTITE_EN_LITRE
        defaultGasoilShouldNotBeFound("quantiteEnLitre.greaterThan=" + DEFAULT_QUANTITE_EN_LITRE);

        // Get all the gasoilList where quantiteEnLitre is greater than SMALLER_QUANTITE_EN_LITRE
        defaultGasoilShouldBeFound("quantiteEnLitre.greaterThan=" + SMALLER_QUANTITE_EN_LITRE);
    }


    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre equals to DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.equals=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre equals to UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.equals=" + UPDATED_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre not equals to DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.notEquals=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre not equals to UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.notEquals=" + UPDATED_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre in DEFAULT_PRIX_DU_LITRE or UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.in=" + DEFAULT_PRIX_DU_LITRE + "," + UPDATED_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre equals to UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.in=" + UPDATED_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre is not null
        defaultGasoilShouldBeFound("prixDuLitre.specified=true");

        // Get all the gasoilList where prixDuLitre is null
        defaultGasoilShouldNotBeFound("prixDuLitre.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre is greater than or equal to DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.greaterThanOrEqual=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre is greater than or equal to UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.greaterThanOrEqual=" + UPDATED_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre is less than or equal to DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.lessThanOrEqual=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre is less than or equal to SMALLER_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.lessThanOrEqual=" + SMALLER_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre is less than DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.lessThan=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre is less than UPDATED_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.lessThan=" + UPDATED_PRIX_DU_LITRE);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixDuLitreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixDuLitre is greater than DEFAULT_PRIX_DU_LITRE
        defaultGasoilShouldNotBeFound("prixDuLitre.greaterThan=" + DEFAULT_PRIX_DU_LITRE);

        // Get all the gasoilList where prixDuLitre is greater than SMALLER_PRIX_DU_LITRE
        defaultGasoilShouldBeFound("prixDuLitre.greaterThan=" + SMALLER_PRIX_DU_LITRE);
    }


    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil equals to DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.equals=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil equals to UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.equals=" + UPDATED_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil not equals to DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.notEquals=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil not equals to UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.notEquals=" + UPDATED_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil in DEFAULT_PRIX_TOTAL_GASOIL or UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.in=" + DEFAULT_PRIX_TOTAL_GASOIL + "," + UPDATED_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil equals to UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.in=" + UPDATED_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil is not null
        defaultGasoilShouldBeFound("prixTotalGasoil.specified=true");

        // Get all the gasoilList where prixTotalGasoil is null
        defaultGasoilShouldNotBeFound("prixTotalGasoil.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil is greater than or equal to DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.greaterThanOrEqual=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil is greater than or equal to UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.greaterThanOrEqual=" + UPDATED_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil is less than or equal to DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.lessThanOrEqual=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil is less than or equal to SMALLER_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.lessThanOrEqual=" + SMALLER_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil is less than DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.lessThan=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil is less than UPDATED_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.lessThan=" + UPDATED_PRIX_TOTAL_GASOIL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByPrixTotalGasoilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where prixTotalGasoil is greater than DEFAULT_PRIX_TOTAL_GASOIL
        defaultGasoilShouldNotBeFound("prixTotalGasoil.greaterThan=" + DEFAULT_PRIX_TOTAL_GASOIL);

        // Get all the gasoilList where prixTotalGasoil is greater than SMALLER_PRIX_TOTAL_GASOIL
        defaultGasoilShouldBeFound("prixTotalGasoil.greaterThan=" + SMALLER_PRIX_TOTAL_GASOIL);
    }


    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial equals to DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.equals=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial equals to UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.equals=" + UPDATED_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial not equals to DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.notEquals=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial not equals to UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.notEquals=" + UPDATED_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial in DEFAULT_KILOMETRAGE_INITIAL or UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.in=" + DEFAULT_KILOMETRAGE_INITIAL + "," + UPDATED_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial equals to UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.in=" + UPDATED_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial is not null
        defaultGasoilShouldBeFound("kilometrageInitial.specified=true");

        // Get all the gasoilList where kilometrageInitial is null
        defaultGasoilShouldNotBeFound("kilometrageInitial.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial is greater than or equal to DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.greaterThanOrEqual=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial is greater than or equal to UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.greaterThanOrEqual=" + UPDATED_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial is less than or equal to DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.lessThanOrEqual=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial is less than or equal to SMALLER_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.lessThanOrEqual=" + SMALLER_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial is less than DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.lessThan=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial is less than UPDATED_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.lessThan=" + UPDATED_KILOMETRAGE_INITIAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageInitialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageInitial is greater than DEFAULT_KILOMETRAGE_INITIAL
        defaultGasoilShouldNotBeFound("kilometrageInitial.greaterThan=" + DEFAULT_KILOMETRAGE_INITIAL);

        // Get all the gasoilList where kilometrageInitial is greater than SMALLER_KILOMETRAGE_INITIAL
        defaultGasoilShouldBeFound("kilometrageInitial.greaterThan=" + SMALLER_KILOMETRAGE_INITIAL);
    }


    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal equals to DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.equals=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal equals to UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.equals=" + UPDATED_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal not equals to DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.notEquals=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal not equals to UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.notEquals=" + UPDATED_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal in DEFAULT_KILOMETRAGE_FINAL or UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.in=" + DEFAULT_KILOMETRAGE_FINAL + "," + UPDATED_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal equals to UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.in=" + UPDATED_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal is not null
        defaultGasoilShouldBeFound("kilometrageFinal.specified=true");

        // Get all the gasoilList where kilometrageFinal is null
        defaultGasoilShouldNotBeFound("kilometrageFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal is greater than or equal to DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.greaterThanOrEqual=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal is greater than or equal to UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.greaterThanOrEqual=" + UPDATED_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal is less than or equal to DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.lessThanOrEqual=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal is less than or equal to SMALLER_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.lessThanOrEqual=" + SMALLER_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal is less than DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.lessThan=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal is less than UPDATED_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.lessThan=" + UPDATED_KILOMETRAGE_FINAL);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageFinal is greater than DEFAULT_KILOMETRAGE_FINAL
        defaultGasoilShouldNotBeFound("kilometrageFinal.greaterThan=" + DEFAULT_KILOMETRAGE_FINAL);

        // Get all the gasoilList where kilometrageFinal is greater than SMALLER_KILOMETRAGE_FINAL
        defaultGasoilShouldBeFound("kilometrageFinal.greaterThan=" + SMALLER_KILOMETRAGE_FINAL);
    }


    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru equals to DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.equals=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru equals to UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.equals=" + UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru not equals to DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.notEquals=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru not equals to UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.notEquals=" + UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsInShouldWork() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru in DEFAULT_KILOMETRAGE_PARCOURU or UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.in=" + DEFAULT_KILOMETRAGE_PARCOURU + "," + UPDATED_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru equals to UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.in=" + UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsNullOrNotNull() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru is not null
        defaultGasoilShouldBeFound("kilometrageParcouru.specified=true");

        // Get all the gasoilList where kilometrageParcouru is null
        defaultGasoilShouldNotBeFound("kilometrageParcouru.specified=false");
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru is greater than or equal to DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.greaterThanOrEqual=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru is greater than or equal to UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.greaterThanOrEqual=" + UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru is less than or equal to DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.lessThanOrEqual=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru is less than or equal to SMALLER_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.lessThanOrEqual=" + SMALLER_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsLessThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru is less than DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.lessThan=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru is less than UPDATED_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.lessThan=" + UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void getAllGasoilsByKilometrageParcouruIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);

        // Get all the gasoilList where kilometrageParcouru is greater than DEFAULT_KILOMETRAGE_PARCOURU
        defaultGasoilShouldNotBeFound("kilometrageParcouru.greaterThan=" + DEFAULT_KILOMETRAGE_PARCOURU);

        // Get all the gasoilList where kilometrageParcouru is greater than SMALLER_KILOMETRAGE_PARCOURU
        defaultGasoilShouldBeFound("kilometrageParcouru.greaterThan=" + SMALLER_KILOMETRAGE_PARCOURU);
    }


    @Test
    @Transactional
    public void getAllGasoilsByTransporteurIsEqualToSomething() throws Exception {
        // Initialize the database
        gasoilRepository.saveAndFlush(gasoil);
        Transporteur transporteur = TransporteurResourceIT.createEntity(em);
        em.persist(transporteur);
        em.flush();
        gasoil.setTransporteur(transporteur);
        gasoilRepository.saveAndFlush(gasoil);
        Long transporteurId = transporteur.getId();

        // Get all the gasoilList where transporteur equals to transporteurId
        defaultGasoilShouldBeFound("transporteurId.equals=" + transporteurId);

        // Get all the gasoilList where transporteur equals to transporteurId + 1
        defaultGasoilShouldNotBeFound("transporteurId.equals=" + (transporteurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGasoilShouldBeFound(String filter) throws Exception {
        restGasoilMockMvc.perform(get("/api/gasoils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoil.getId().intValue())))
            .andExpect(jsonPath("$.[*].societe").value(hasItem(DEFAULT_SOCIETE)))
            .andExpect(jsonPath("$.[*].numeroBonGasoil").value(hasItem(DEFAULT_NUMERO_BON_GASOIL.intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].quantiteEnLitre").value(hasItem(DEFAULT_QUANTITE_EN_LITRE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixDuLitre").value(hasItem(DEFAULT_PRIX_DU_LITRE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTotalGasoil").value(hasItem(DEFAULT_PRIX_TOTAL_GASOIL.doubleValue())))
            .andExpect(jsonPath("$.[*].kilometrageInitial").value(hasItem(DEFAULT_KILOMETRAGE_INITIAL)))
            .andExpect(jsonPath("$.[*].kilometrageFinal").value(hasItem(DEFAULT_KILOMETRAGE_FINAL)))
            .andExpect(jsonPath("$.[*].kilometrageParcouru").value(hasItem(DEFAULT_KILOMETRAGE_PARCOURU)));

        // Check, that the count call also returns 1
        restGasoilMockMvc.perform(get("/api/gasoils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGasoilShouldNotBeFound(String filter) throws Exception {
        restGasoilMockMvc.perform(get("/api/gasoils?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGasoilMockMvc.perform(get("/api/gasoils/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGasoil() throws Exception {
        // Get the gasoil
        restGasoilMockMvc.perform(get("/api/gasoils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasoil() throws Exception {
        // Initialize the database
        gasoilService.save(gasoil);

        int databaseSizeBeforeUpdate = gasoilRepository.findAll().size();

        // Update the gasoil
        Gasoil updatedGasoil = gasoilRepository.findById(gasoil.getId()).get();
        // Disconnect from session so that the updates on updatedGasoil are not directly saved in db
        em.detach(updatedGasoil);
        updatedGasoil
            .societe(UPDATED_SOCIETE)
            .numeroBonGasoil(UPDATED_NUMERO_BON_GASOIL)
            .quantiteEnLitre(UPDATED_QUANTITE_EN_LITRE)
            .prixDuLitre(UPDATED_PRIX_DU_LITRE)
            .prixTotalGasoil(UPDATED_PRIX_TOTAL_GASOIL)
            .kilometrageInitial(UPDATED_KILOMETRAGE_INITIAL)
            .kilometrageFinal(UPDATED_KILOMETRAGE_FINAL)
            .kilometrageParcouru(UPDATED_KILOMETRAGE_PARCOURU);

        restGasoilMockMvc.perform(put("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGasoil)))
            .andExpect(status().isOk());

        // Validate the Gasoil in the database
        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeUpdate);
        Gasoil testGasoil = gasoilList.get(gasoilList.size() - 1);
        assertThat(testGasoil.getSociete()).isEqualTo(UPDATED_SOCIETE);
        assertThat(testGasoil.getNumeroBonGasoil()).isEqualTo(UPDATED_NUMERO_BON_GASOIL);
        assertThat(testGasoil.getQuantiteEnLitre()).isEqualTo(UPDATED_QUANTITE_EN_LITRE);
        assertThat(testGasoil.getPrixDuLitre()).isEqualTo(UPDATED_PRIX_DU_LITRE);
        assertThat(testGasoil.getPrixTotalGasoil()).isEqualTo(UPDATED_PRIX_TOTAL_GASOIL);
        assertThat(testGasoil.getKilometrageInitial()).isEqualTo(UPDATED_KILOMETRAGE_INITIAL);
        assertThat(testGasoil.getKilometrageFinal()).isEqualTo(UPDATED_KILOMETRAGE_FINAL);
        assertThat(testGasoil.getKilometrageParcouru()).isEqualTo(UPDATED_KILOMETRAGE_PARCOURU);
    }

    @Test
    @Transactional
    public void updateNonExistingGasoil() throws Exception {
        int databaseSizeBeforeUpdate = gasoilRepository.findAll().size();

        // Create the Gasoil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasoilMockMvc.perform(put("/api/gasoils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoil)))
            .andExpect(status().isBadRequest());

        // Validate the Gasoil in the database
        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasoil() throws Exception {
        // Initialize the database
        gasoilService.save(gasoil);

        int databaseSizeBeforeDelete = gasoilRepository.findAll().size();

        // Delete the gasoil
        restGasoilMockMvc.perform(delete("/api/gasoils/{id}", gasoil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gasoil> gasoilList = gasoilRepository.findAll();
        assertThat(gasoilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
