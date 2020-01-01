package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.TarifTransport;
import com.logistica.domain.Client;
import com.logistica.domain.Trajet;
import com.logistica.domain.Produit;
import com.logistica.repository.TarifTransportRepository;
import com.logistica.service.TarifTransportService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.TarifTransportCriteria;
import com.logistica.service.TarifTransportQueryService;

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

import com.logistica.domain.enumeration.Unite;
/**
 * Integration tests for the {@link TarifTransportResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TarifTransportResourceIT {

    private static final Unite DEFAULT_UNITE = Unite.Tonne;
    private static final Unite UPDATED_UNITE = Unite.M3;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifTransportRepository tarifTransportRepository;

    @Autowired
    private TarifTransportService tarifTransportService;

    @Autowired
    private TarifTransportQueryService tarifTransportQueryService;

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

    private MockMvc restTarifTransportMockMvc;

    private TarifTransport tarifTransport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifTransportResource tarifTransportResource = new TarifTransportResource(tarifTransportService, tarifTransportQueryService);
        this.restTarifTransportMockMvc = MockMvcBuilders.standaloneSetup(tarifTransportResource)
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
    public static TarifTransport createEntity(EntityManager em) {
        TarifTransport tarifTransport = new TarifTransport()
            .unite(DEFAULT_UNITE)
            .prix(DEFAULT_PRIX);
        return tarifTransport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifTransport createUpdatedEntity(EntityManager em) {
        TarifTransport tarifTransport = new TarifTransport()
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);
        return tarifTransport;
    }

    @BeforeEach
    public void initTest() {
        tarifTransport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifTransport() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport
        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isCreated());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeCreate + 1);
        TarifTransport testTarifTransport = tarifTransportList.get(tarifTransportList.size() - 1);
        assertThat(testTarifTransport.getUnite()).isEqualTo(DEFAULT_UNITE);
        assertThat(testTarifTransport.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifTransportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport with an existing ID
        tarifTransport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUniteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifTransportRepository.findAll().size();
        // set the field null
        tarifTransport.setUnite(null);

        // Create the TarifTransport, which fails.

        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifTransportRepository.findAll().size();
        // set the field null
        tarifTransport.setPrix(null);

        // Create the TarifTransport, which fails.

        restTarifTransportMockMvc.perform(post("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifTransports() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get the tarifTransport
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/{id}", tarifTransport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarifTransport.getId().intValue()))
            .andExpect(jsonPath("$.unite").value(DEFAULT_UNITE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifTransportsByIdFiltering() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        Long id = tarifTransport.getId();

        defaultTarifTransportShouldBeFound("id.equals=" + id);
        defaultTarifTransportShouldNotBeFound("id.notEquals=" + id);

        defaultTarifTransportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifTransportShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifTransportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifTransportShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByUniteIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where unite equals to DEFAULT_UNITE
        defaultTarifTransportShouldBeFound("unite.equals=" + DEFAULT_UNITE);

        // Get all the tarifTransportList where unite equals to UPDATED_UNITE
        defaultTarifTransportShouldNotBeFound("unite.equals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByUniteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where unite not equals to DEFAULT_UNITE
        defaultTarifTransportShouldNotBeFound("unite.notEquals=" + DEFAULT_UNITE);

        // Get all the tarifTransportList where unite not equals to UPDATED_UNITE
        defaultTarifTransportShouldBeFound("unite.notEquals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByUniteIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where unite in DEFAULT_UNITE or UPDATED_UNITE
        defaultTarifTransportShouldBeFound("unite.in=" + DEFAULT_UNITE + "," + UPDATED_UNITE);

        // Get all the tarifTransportList where unite equals to UPDATED_UNITE
        defaultTarifTransportShouldNotBeFound("unite.in=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByUniteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where unite is not null
        defaultTarifTransportShouldBeFound("unite.specified=true");

        // Get all the tarifTransportList where unite is null
        defaultTarifTransportShouldNotBeFound("unite.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix equals to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix equals to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix not equals to DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix not equals to UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifTransportList where prix equals to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is not null
        defaultTarifTransportShouldBeFound("prix.specified=true");

        // Get all the tarifTransportList where prix is null
        defaultTarifTransportShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifTransportShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifTransportShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is less than or equal to SMALLER_PRIX
        defaultTarifTransportShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is less than DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is less than UPDATED_PRIX
        defaultTarifTransportShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifTransportsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);

        // Get all the tarifTransportList where prix is greater than DEFAULT_PRIX
        defaultTarifTransportShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifTransportList where prix is greater than SMALLER_PRIX
        defaultTarifTransportShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        tarifTransport.setClient(client);
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Long clientId = client.getId();

        // Get all the tarifTransportList where client equals to clientId
        defaultTarifTransportShouldBeFound("clientId.equals=" + clientId);

        // Get all the tarifTransportList where client equals to clientId + 1
        defaultTarifTransportShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByTrajetIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Trajet trajet = TrajetResourceIT.createEntity(em);
        em.persist(trajet);
        em.flush();
        tarifTransport.setTrajet(trajet);
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Long trajetId = trajet.getId();

        // Get all the tarifTransportList where trajet equals to trajetId
        defaultTarifTransportShouldBeFound("trajetId.equals=" + trajetId);

        // Get all the tarifTransportList where trajet equals to trajetId + 1
        defaultTarifTransportShouldNotBeFound("trajetId.equals=" + (trajetId + 1));
    }


    @Test
    @Transactional
    public void getAllTarifTransportsByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        tarifTransport.setProduit(produit);
        tarifTransportRepository.saveAndFlush(tarifTransport);
        Long produitId = produit.getId();

        // Get all the tarifTransportList where produit equals to produitId
        defaultTarifTransportShouldBeFound("produitId.equals=" + produitId);

        // Get all the tarifTransportList where produit equals to produitId + 1
        defaultTarifTransportShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifTransportShouldBeFound(String filter) throws Exception {
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifTransport.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifTransportShouldNotBeFound(String filter) throws Exception {
        restTarifTransportMockMvc.perform(get("/api/tarif-transports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifTransport() throws Exception {
        // Get the tarifTransport
        restTarifTransportMockMvc.perform(get("/api/tarif-transports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportService.save(tarifTransport);

        int databaseSizeBeforeUpdate = tarifTransportRepository.findAll().size();

        // Update the tarifTransport
        TarifTransport updatedTarifTransport = tarifTransportRepository.findById(tarifTransport.getId()).get();
        // Disconnect from session so that the updates on updatedTarifTransport are not directly saved in db
        em.detach(updatedTarifTransport);
        updatedTarifTransport
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);

        restTarifTransportMockMvc.perform(put("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifTransport)))
            .andExpect(status().isOk());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeUpdate);
        TarifTransport testTarifTransport = tarifTransportList.get(tarifTransportList.size() - 1);
        assertThat(testTarifTransport.getUnite()).isEqualTo(UPDATED_UNITE);
        assertThat(testTarifTransport.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifTransport() throws Exception {
        int databaseSizeBeforeUpdate = tarifTransportRepository.findAll().size();

        // Create the TarifTransport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifTransportMockMvc.perform(put("/api/tarif-transports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifTransport)))
            .andExpect(status().isBadRequest());

        // Validate the TarifTransport in the database
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifTransport() throws Exception {
        // Initialize the database
        tarifTransportService.save(tarifTransport);

        int databaseSizeBeforeDelete = tarifTransportRepository.findAll().size();

        // Delete the tarifTransport
        restTarifTransportMockMvc.perform(delete("/api/tarif-transports/{id}", tarifTransport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifTransport> tarifTransportList = tarifTransportRepository.findAll();
        assertThat(tarifTransportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
