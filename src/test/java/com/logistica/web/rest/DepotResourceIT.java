package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.ClientGrossiste;
import com.logistica.domain.Depot;
import com.logistica.domain.FournisseurGrossiste;
import com.logistica.repository.DepotRepository;
import com.logistica.service.DepotQueryService;
import com.logistica.service.DepotService;
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
 * Integration tests for the {@link DepotResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class DepotResourceIT {

    private static final Float DEFAULT_STOCK = 0F;
    private static final Float UPDATED_STOCK = 1F;
    private static final Float SMALLER_STOCK = 0F - 1F;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONSOMMATION_INTERNE = false;
    private static final Boolean UPDATED_CONSOMMATION_INTERNE = true;

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private DepotService depotService;

    @Autowired
    private DepotQueryService depotQueryService;

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

    private MockMvc restDepotMockMvc;

    private Depot depot;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepotResource depotResource = new DepotResource(depotService, depotQueryService);
        this.restDepotMockMvc = MockMvcBuilders.standaloneSetup(depotResource)
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
    public static Depot createEntity(EntityManager em) {
        Depot depot = new Depot()
            .stock(DEFAULT_STOCK)
            .nom(DEFAULT_NOM)
            .consommationInterne(DEFAULT_CONSOMMATION_INTERNE);
        // Add required entity
        ClientGrossiste clientGrossiste;
        if (TestUtil.findAll(em, ClientGrossiste.class).isEmpty()) {
            clientGrossiste = ClientGrossisteResourceIT.createEntity(em);
            em.persist(clientGrossiste);
            em.flush();
        } else {
            clientGrossiste = TestUtil.findAll(em, ClientGrossiste.class).get(0);
        }
        // Add required entity
        FournisseurGrossiste fournisseurGrossiste;
        if (TestUtil.findAll(em, FournisseurGrossiste.class).isEmpty()) {
            fournisseurGrossiste = FournisseurGrossisteResourceIT.createEntity(em);
            em.persist(fournisseurGrossiste);
            em.flush();
        } else {
            fournisseurGrossiste = TestUtil.findAll(em, FournisseurGrossiste.class).get(0);
        }
        return depot;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depot createUpdatedEntity(EntityManager em) {
        Depot depot = new Depot()
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM)
            .consommationInterne(UPDATED_CONSOMMATION_INTERNE);
        // Add required entity
        ClientGrossiste clientGrossiste;
        if (TestUtil.findAll(em, ClientGrossiste.class).isEmpty()) {
            clientGrossiste = ClientGrossisteResourceIT.createUpdatedEntity(em);
            em.persist(clientGrossiste);
            em.flush();
        } else {
            clientGrossiste = TestUtil.findAll(em, ClientGrossiste.class).get(0);
        }
        // Add required entity
        FournisseurGrossiste fournisseurGrossiste;
        if (TestUtil.findAll(em, FournisseurGrossiste.class).isEmpty()) {
            fournisseurGrossiste = FournisseurGrossisteResourceIT.createUpdatedEntity(em);
            em.persist(fournisseurGrossiste);
            em.flush();
        } else {
            fournisseurGrossiste = TestUtil.findAll(em, FournisseurGrossiste.class).get(0);
        }
        return depot;
    }

    @BeforeEach
    public void initTest() {
        depot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepot() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isCreated());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate + 1);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testDepot.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDepot.isConsommationInterne()).isEqualTo(DEFAULT_CONSOMMATION_INTERNE);
    }

    @Test
    @Transactional
    public void createDepotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotRepository.findAll().size();

        // Create the Depot with an existing ID
        depot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setStock(null);

        // Create the Depot, which fails.

        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotRepository.findAll().size();
        // set the field null
        depot.setNom(null);

        // Create the Depot, which fails.

        restDepotMockMvc.perform(post("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepots() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].consommationInterne").value(hasItem(DEFAULT_CONSOMMATION_INTERNE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDepot() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", depot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depot.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.doubleValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.consommationInterne").value(DEFAULT_CONSOMMATION_INTERNE.booleanValue()));
    }


    @Test
    @Transactional
    public void getDepotsByIdFiltering() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        Long id = depot.getId();

        defaultDepotShouldBeFound("id.equals=" + id);
        defaultDepotShouldNotBeFound("id.notEquals=" + id);

        defaultDepotShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepotShouldNotBeFound("id.greaterThan=" + id);

        defaultDepotShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepotShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDepotsByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock equals to DEFAULT_STOCK
        defaultDepotShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the depotList where stock equals to UPDATED_STOCK
        defaultDepotShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock not equals to DEFAULT_STOCK
        defaultDepotShouldNotBeFound("stock.notEquals=" + DEFAULT_STOCK);

        // Get all the depotList where stock not equals to UPDATED_STOCK
        defaultDepotShouldBeFound("stock.notEquals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultDepotShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the depotList where stock equals to UPDATED_STOCK
        defaultDepotShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock is not null
        defaultDepotShouldBeFound("stock.specified=true");

        // Get all the depotList where stock is null
        defaultDepotShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock is greater than or equal to DEFAULT_STOCK
        defaultDepotShouldBeFound("stock.greaterThanOrEqual=" + DEFAULT_STOCK);

        // Get all the depotList where stock is greater than or equal to UPDATED_STOCK
        defaultDepotShouldNotBeFound("stock.greaterThanOrEqual=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock is less than or equal to DEFAULT_STOCK
        defaultDepotShouldBeFound("stock.lessThanOrEqual=" + DEFAULT_STOCK);

        // Get all the depotList where stock is less than or equal to SMALLER_STOCK
        defaultDepotShouldNotBeFound("stock.lessThanOrEqual=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock is less than DEFAULT_STOCK
        defaultDepotShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the depotList where stock is less than UPDATED_STOCK
        defaultDepotShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void getAllDepotsByStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where stock is greater than DEFAULT_STOCK
        defaultDepotShouldNotBeFound("stock.greaterThan=" + DEFAULT_STOCK);

        // Get all the depotList where stock is greater than SMALLER_STOCK
        defaultDepotShouldBeFound("stock.greaterThan=" + SMALLER_STOCK);
    }


    @Test
    @Transactional
    public void getAllDepotsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom equals to DEFAULT_NOM
        defaultDepotShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the depotList where nom equals to UPDATED_NOM
        defaultDepotShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDepotsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom not equals to DEFAULT_NOM
        defaultDepotShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the depotList where nom not equals to UPDATED_NOM
        defaultDepotShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDepotsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultDepotShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the depotList where nom equals to UPDATED_NOM
        defaultDepotShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDepotsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom is not null
        defaultDepotShouldBeFound("nom.specified=true");

        // Get all the depotList where nom is null
        defaultDepotShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepotsByNomContainsSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom contains DEFAULT_NOM
        defaultDepotShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the depotList where nom contains UPDATED_NOM
        defaultDepotShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllDepotsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where nom does not contain DEFAULT_NOM
        defaultDepotShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the depotList where nom does not contain UPDATED_NOM
        defaultDepotShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllDepotsByConsommationInterneIsEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where consommationInterne equals to DEFAULT_CONSOMMATION_INTERNE
        defaultDepotShouldBeFound("consommationInterne.equals=" + DEFAULT_CONSOMMATION_INTERNE);

        // Get all the depotList where consommationInterne equals to UPDATED_CONSOMMATION_INTERNE
        defaultDepotShouldNotBeFound("consommationInterne.equals=" + UPDATED_CONSOMMATION_INTERNE);
    }

    @Test
    @Transactional
    public void getAllDepotsByConsommationInterneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where consommationInterne not equals to DEFAULT_CONSOMMATION_INTERNE
        defaultDepotShouldNotBeFound("consommationInterne.notEquals=" + DEFAULT_CONSOMMATION_INTERNE);

        // Get all the depotList where consommationInterne not equals to UPDATED_CONSOMMATION_INTERNE
        defaultDepotShouldBeFound("consommationInterne.notEquals=" + UPDATED_CONSOMMATION_INTERNE);
    }

    @Test
    @Transactional
    public void getAllDepotsByConsommationInterneIsInShouldWork() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where consommationInterne in DEFAULT_CONSOMMATION_INTERNE or UPDATED_CONSOMMATION_INTERNE
        defaultDepotShouldBeFound("consommationInterne.in=" + DEFAULT_CONSOMMATION_INTERNE + "," + UPDATED_CONSOMMATION_INTERNE);

        // Get all the depotList where consommationInterne equals to UPDATED_CONSOMMATION_INTERNE
        defaultDepotShouldNotBeFound("consommationInterne.in=" + UPDATED_CONSOMMATION_INTERNE);
    }

    @Test
    @Transactional
    public void getAllDepotsByConsommationInterneIsNullOrNotNull() throws Exception {
        // Initialize the database
        depotRepository.saveAndFlush(depot);

        // Get all the depotList where consommationInterne is not null
        defaultDepotShouldBeFound("consommationInterne.specified=true");

        // Get all the depotList where consommationInterne is null
        defaultDepotShouldNotBeFound("consommationInterne.specified=false");
    }


    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepotShouldBeFound(String filter) throws Exception {
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depot.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].consommationInterne").value(hasItem(DEFAULT_CONSOMMATION_INTERNE.booleanValue())));

        // Check, that the count call also returns 1
        restDepotMockMvc.perform(get("/api/depots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepotShouldNotBeFound(String filter) throws Exception {
        restDepotMockMvc.perform(get("/api/depots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepotMockMvc.perform(get("/api/depots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepot() throws Exception {
        // Get the depot
        restDepotMockMvc.perform(get("/api/depots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Update the depot
        Depot updatedDepot = depotRepository.findById(depot.getId()).get();
        // Disconnect from session so that the updates on updatedDepot are not directly saved in db
        em.detach(updatedDepot);
        updatedDepot
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM)
            .consommationInterne(UPDATED_CONSOMMATION_INTERNE);

        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepot)))
            .andExpect(status().isOk());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
        Depot testDepot = depotList.get(depotList.size() - 1);
        assertThat(testDepot.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testDepot.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDepot.isConsommationInterne()).isEqualTo(UPDATED_CONSOMMATION_INTERNE);
    }

    @Test
    @Transactional
    public void updateNonExistingDepot() throws Exception {
        int databaseSizeBeforeUpdate = depotRepository.findAll().size();

        // Create the Depot

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotMockMvc.perform(put("/api/depots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depot)))
            .andExpect(status().isBadRequest());

        // Validate the Depot in the database
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepot() throws Exception {
        // Initialize the database
        depotService.save(depot);

        int databaseSizeBeforeDelete = depotRepository.findAll().size();

        // Delete the depot
        restDepotMockMvc.perform(delete("/api/depots/{id}", depot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Depot> depotList = depotRepository.findAll();
        assertThat(depotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
