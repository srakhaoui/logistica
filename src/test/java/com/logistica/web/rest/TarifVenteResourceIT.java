package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.TarifVente;
import com.logistica.domain.Client;
import com.logistica.domain.Produit;
import com.logistica.repository.TarifVenteRepository;
import com.logistica.service.TarifVenteService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.TarifVenteCriteria;
import com.logistica.service.TarifVenteQueryService;

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
 * Integration tests for the {@link TarifVenteResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TarifVenteResourceIT {

    private static final Unite DEFAULT_UNITE = Unite.Tonne;
    private static final Unite UPDATED_UNITE = Unite.M3;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifVenteRepository tarifVenteRepository;

    @Autowired
    private TarifVenteService tarifVenteService;

    @Autowired
    private TarifVenteQueryService tarifVenteQueryService;

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

    private MockMvc restTarifVenteMockMvc;

    private TarifVente tarifVente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifVenteResource tarifVenteResource = new TarifVenteResource(tarifVenteService, tarifVenteQueryService);
        this.restTarifVenteMockMvc = MockMvcBuilders.standaloneSetup(tarifVenteResource)
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
    public static TarifVente createEntity(EntityManager em) {
        TarifVente tarifVente = new TarifVente()
            .unite(DEFAULT_UNITE)
            .prix(DEFAULT_PRIX);
        return tarifVente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifVente createUpdatedEntity(EntityManager em) {
        TarifVente tarifVente = new TarifVente()
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);
        return tarifVente;
    }

    @BeforeEach
    public void initTest() {
        tarifVente = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifVente() throws Exception {
        int databaseSizeBeforeCreate = tarifVenteRepository.findAll().size();

        // Create the TarifVente
        restTarifVenteMockMvc.perform(post("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifVente)))
            .andExpect(status().isCreated());

        // Validate the TarifVente in the database
        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeCreate + 1);
        TarifVente testTarifVente = tarifVenteList.get(tarifVenteList.size() - 1);
        assertThat(testTarifVente.getUnite()).isEqualTo(DEFAULT_UNITE);
        assertThat(testTarifVente.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifVenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifVenteRepository.findAll().size();

        // Create the TarifVente with an existing ID
        tarifVente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifVenteMockMvc.perform(post("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifVente)))
            .andExpect(status().isBadRequest());

        // Validate the TarifVente in the database
        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUniteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifVenteRepository.findAll().size();
        // set the field null
        tarifVente.setUnite(null);

        // Create the TarifVente, which fails.

        restTarifVenteMockMvc.perform(post("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifVente)))
            .andExpect(status().isBadRequest());

        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifVenteRepository.findAll().size();
        // set the field null
        tarifVente.setPrix(null);

        // Create the TarifVente, which fails.

        restTarifVenteMockMvc.perform(post("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifVente)))
            .andExpect(status().isBadRequest());

        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifVentes() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifVente() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get the tarifVente
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes/{id}", tarifVente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarifVente.getId().intValue()))
            .andExpect(jsonPath("$.unite").value(DEFAULT_UNITE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifVentesByIdFiltering() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        Long id = tarifVente.getId();

        defaultTarifVenteShouldBeFound("id.equals=" + id);
        defaultTarifVenteShouldNotBeFound("id.notEquals=" + id);

        defaultTarifVenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifVenteShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifVenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifVenteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifVentesByUniteIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where unite equals to DEFAULT_UNITE
        defaultTarifVenteShouldBeFound("unite.equals=" + DEFAULT_UNITE);

        // Get all the tarifVenteList where unite equals to UPDATED_UNITE
        defaultTarifVenteShouldNotBeFound("unite.equals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByUniteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where unite not equals to DEFAULT_UNITE
        defaultTarifVenteShouldNotBeFound("unite.notEquals=" + DEFAULT_UNITE);

        // Get all the tarifVenteList where unite not equals to UPDATED_UNITE
        defaultTarifVenteShouldBeFound("unite.notEquals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByUniteIsInShouldWork() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where unite in DEFAULT_UNITE or UPDATED_UNITE
        defaultTarifVenteShouldBeFound("unite.in=" + DEFAULT_UNITE + "," + UPDATED_UNITE);

        // Get all the tarifVenteList where unite equals to UPDATED_UNITE
        defaultTarifVenteShouldNotBeFound("unite.in=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByUniteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where unite is not null
        defaultTarifVenteShouldBeFound("unite.specified=true");

        // Get all the tarifVenteList where unite is null
        defaultTarifVenteShouldNotBeFound("unite.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix equals to DEFAULT_PRIX
        defaultTarifVenteShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix equals to UPDATED_PRIX
        defaultTarifVenteShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix not equals to DEFAULT_PRIX
        defaultTarifVenteShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix not equals to UPDATED_PRIX
        defaultTarifVenteShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifVenteShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifVenteList where prix equals to UPDATED_PRIX
        defaultTarifVenteShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix is not null
        defaultTarifVenteShouldBeFound("prix.specified=true");

        // Get all the tarifVenteList where prix is null
        defaultTarifVenteShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifVenteShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifVenteShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifVenteShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix is less than or equal to SMALLER_PRIX
        defaultTarifVenteShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix is less than DEFAULT_PRIX
        defaultTarifVenteShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix is less than UPDATED_PRIX
        defaultTarifVenteShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifVentesByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);

        // Get all the tarifVenteList where prix is greater than DEFAULT_PRIX
        defaultTarifVenteShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifVenteList where prix is greater than SMALLER_PRIX
        defaultTarifVenteShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }


    @Test
    @Transactional
    public void getAllTarifVentesByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        tarifVente.setClient(client);
        tarifVenteRepository.saveAndFlush(tarifVente);
        Long clientId = client.getId();

        // Get all the tarifVenteList where client equals to clientId
        defaultTarifVenteShouldBeFound("clientId.equals=" + clientId);

        // Get all the tarifVenteList where client equals to clientId + 1
        defaultTarifVenteShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }


    @Test
    @Transactional
    public void getAllTarifVentesByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifVenteRepository.saveAndFlush(tarifVente);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        tarifVente.setProduit(produit);
        tarifVenteRepository.saveAndFlush(tarifVente);
        Long produitId = produit.getId();

        // Get all the tarifVenteList where produit equals to produitId
        defaultTarifVenteShouldBeFound("produitId.equals=" + produitId);

        // Get all the tarifVenteList where produit equals to produitId + 1
        defaultTarifVenteShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifVenteShouldBeFound(String filter) throws Exception {
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifVente.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifVenteShouldNotBeFound(String filter) throws Exception {
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifVente() throws Exception {
        // Get the tarifVente
        restTarifVenteMockMvc.perform(get("/api/tarif-ventes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifVente() throws Exception {
        // Initialize the database
        tarifVenteService.save(tarifVente);

        int databaseSizeBeforeUpdate = tarifVenteRepository.findAll().size();

        // Update the tarifVente
        TarifVente updatedTarifVente = tarifVenteRepository.findById(tarifVente.getId()).get();
        // Disconnect from session so that the updates on updatedTarifVente are not directly saved in db
        em.detach(updatedTarifVente);
        updatedTarifVente
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);

        restTarifVenteMockMvc.perform(put("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifVente)))
            .andExpect(status().isOk());

        // Validate the TarifVente in the database
        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeUpdate);
        TarifVente testTarifVente = tarifVenteList.get(tarifVenteList.size() - 1);
        assertThat(testTarifVente.getUnite()).isEqualTo(UPDATED_UNITE);
        assertThat(testTarifVente.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifVente() throws Exception {
        int databaseSizeBeforeUpdate = tarifVenteRepository.findAll().size();

        // Create the TarifVente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifVenteMockMvc.perform(put("/api/tarif-ventes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifVente)))
            .andExpect(status().isBadRequest());

        // Validate the TarifVente in the database
        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifVente() throws Exception {
        // Initialize the database
        tarifVenteService.save(tarifVente);

        int databaseSizeBeforeDelete = tarifVenteRepository.findAll().size();

        // Delete the tarifVente
        restTarifVenteMockMvc.perform(delete("/api/tarif-ventes/{id}", tarifVente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifVente> tarifVenteList = tarifVenteRepository.findAll();
        assertThat(tarifVenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
