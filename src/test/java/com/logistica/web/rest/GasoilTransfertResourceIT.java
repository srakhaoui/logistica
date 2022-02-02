package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Depot;
import com.logistica.domain.GasoilTransfert;
import com.logistica.repository.GasoilTransfertRepository;
import com.logistica.service.GasoilTransfertService;
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
 * Integration tests for the {@link GasoilTransfertResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class GasoilTransfertResourceIT {

    private static final LocalDate DEFAULT_TRANSFERT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSFERT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_QUANTITE = 0D;
    private static final Double UPDATED_QUANTITE = 1D;

    @Autowired
    private GasoilTransfertRepository gasoilTransfertRepository;

    @Autowired
    private GasoilTransfertService gasoilTransfertService;

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

    private MockMvc restGasoilTransfertMockMvc;

    private GasoilTransfert gasoilTransfert;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GasoilTransfertResource gasoilTransfertResource = new GasoilTransfertResource(gasoilTransfertService);
        this.restGasoilTransfertMockMvc = MockMvcBuilders.standaloneSetup(gasoilTransfertResource)
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
    public static GasoilTransfert createEntity(EntityManager em) {
        GasoilTransfert gasoilTransfert = new GasoilTransfert()
            .transfertDate(DEFAULT_TRANSFERT_DATE)
            .quantite(DEFAULT_QUANTITE);
        // Add required entity
        Depot depot;
        if (TestUtil.findAll(em, Depot.class).isEmpty()) {
            depot = DepotResourceIT.createEntity(em);
            em.persist(depot);
            em.flush();
        } else {
            depot = TestUtil.findAll(em, Depot.class).get(0);
        }
        gasoilTransfert.setSource(depot);
        // Add required entity
        gasoilTransfert.setDestination(depot);
        return gasoilTransfert;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GasoilTransfert createUpdatedEntity(EntityManager em) {
        GasoilTransfert gasoilTransfert = new GasoilTransfert()
            .transfertDate(UPDATED_TRANSFERT_DATE)
            .quantite(UPDATED_QUANTITE);
        // Add required entity
        Depot depot;
        if (TestUtil.findAll(em, Depot.class).isEmpty()) {
            depot = DepotResourceIT.createUpdatedEntity(em);
            em.persist(depot);
            em.flush();
        } else {
            depot = TestUtil.findAll(em, Depot.class).get(0);
        }
        gasoilTransfert.setSource(depot);
        // Add required entity
        gasoilTransfert.setDestination(depot);
        return gasoilTransfert;
    }

    @BeforeEach
    public void initTest() {
        gasoilTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasoilTransfert() throws Exception {
        int databaseSizeBeforeCreate = gasoilTransfertRepository.findAll().size();

        // Create the GasoilTransfert
        restGasoilTransfertMockMvc.perform(post("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilTransfert)))
            .andExpect(status().isCreated());

        // Validate the GasoilTransfert in the database
        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        GasoilTransfert testGasoilTransfert = gasoilTransfertList.get(gasoilTransfertList.size() - 1);
        assertThat(testGasoilTransfert.getTransfertDate()).isEqualTo(DEFAULT_TRANSFERT_DATE);
        assertThat(testGasoilTransfert.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    public void createGasoilTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gasoilTransfertRepository.findAll().size();

        // Create the GasoilTransfert with an existing ID
        gasoilTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGasoilTransfertMockMvc.perform(post("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilTransfert in the database
        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransfertDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilTransfertRepository.findAll().size();
        // set the field null
        gasoilTransfert.setTransfertDate(null);

        // Create the GasoilTransfert, which fails.

        restGasoilTransfertMockMvc.perform(post("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilTransfert)))
            .andExpect(status().isBadRequest());

        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilTransfertRepository.findAll().size();
        // set the field null
        gasoilTransfert.setQuantite(null);

        // Create the GasoilTransfert, which fails.

        restGasoilTransfertMockMvc.perform(post("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilTransfert)))
            .andExpect(status().isBadRequest());

        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGasoilTransferts() throws Exception {
        // Initialize the database
        gasoilTransfertRepository.saveAndFlush(gasoilTransfert);

        // Get all the gasoilTransfertList
        restGasoilTransfertMockMvc.perform(get("/api/gasoil-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoilTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].transfertDate").value(hasItem(DEFAULT_TRANSFERT_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void getGasoilTransfert() throws Exception {
        // Initialize the database
        gasoilTransfertRepository.saveAndFlush(gasoilTransfert);

        // Get the gasoilTransfert
        restGasoilTransfertMockMvc.perform(get("/api/gasoil-transferts/{id}", gasoilTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasoilTransfert.getId().intValue()))
            .andExpect(jsonPath("$.transfertDate").value(DEFAULT_TRANSFERT_DATE.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGasoilTransfert() throws Exception {
        // Get the gasoilTransfert
        restGasoilTransfertMockMvc.perform(get("/api/gasoil-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasoilTransfert() throws Exception {
        // Initialize the database
        gasoilTransfertService.save(gasoilTransfert);

        int databaseSizeBeforeUpdate = gasoilTransfertRepository.findAll().size();

        // Update the gasoilTransfert
        GasoilTransfert updatedGasoilTransfert = gasoilTransfertRepository.findById(gasoilTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedGasoilTransfert are not directly saved in db
        em.detach(updatedGasoilTransfert);
        updatedGasoilTransfert
            .transfertDate(UPDATED_TRANSFERT_DATE)
            .quantite(UPDATED_QUANTITE);

        restGasoilTransfertMockMvc.perform(put("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGasoilTransfert)))
            .andExpect(status().isOk());

        // Validate the GasoilTransfert in the database
        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeUpdate);
        GasoilTransfert testGasoilTransfert = gasoilTransfertList.get(gasoilTransfertList.size() - 1);
        assertThat(testGasoilTransfert.getTransfertDate()).isEqualTo(UPDATED_TRANSFERT_DATE);
        assertThat(testGasoilTransfert.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void updateNonExistingGasoilTransfert() throws Exception {
        int databaseSizeBeforeUpdate = gasoilTransfertRepository.findAll().size();

        // Create the GasoilTransfert

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasoilTransfertMockMvc.perform(put("/api/gasoil-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilTransfert in the database
        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasoilTransfert() throws Exception {
        // Initialize the database
        gasoilTransfertService.save(gasoilTransfert);

        int databaseSizeBeforeDelete = gasoilTransfertRepository.findAll().size();

        // Delete the gasoilTransfert
        restGasoilTransfertMockMvc.perform(delete("/api/gasoil-transferts/{id}", gasoilTransfert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GasoilTransfert> gasoilTransfertList = gasoilTransfertRepository.findAll();
        assertThat(gasoilTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
