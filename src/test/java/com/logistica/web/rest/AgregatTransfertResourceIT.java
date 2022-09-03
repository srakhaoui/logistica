package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.AgregatTransfert;
import com.logistica.domain.DepotAggregat;
import com.logistica.repository.AgregatTransfertRepository;
import com.logistica.service.AgregatTransfertService;
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
 * Integration tests for the {@link AgregatTransfertResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class AgregatTransfertResourceIT {

    private static final LocalDate DEFAULT_TRANSFERT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSFERT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_QUANTITE = 0F;
    private static final Float UPDATED_QUANTITE = 1F;

    @Autowired
    private AgregatTransfertRepository agregatTransfertRepository;

    @Autowired
    private AgregatTransfertService agregatTransfertService;

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

    private MockMvc restAgregatTransfertMockMvc;

    private AgregatTransfert agregatTransfert;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgregatTransfertResource agregatTransfertResource = new AgregatTransfertResource(agregatTransfertService);
        this.restAgregatTransfertMockMvc = MockMvcBuilders.standaloneSetup(agregatTransfertResource)
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
    public static AgregatTransfert createEntity(EntityManager em) {
        AgregatTransfert agregatTransfert = new AgregatTransfert()
            .transfertDate(DEFAULT_TRANSFERT_DATE)
            .quantite(DEFAULT_QUANTITE);
        // Add required entity
        DepotAggregat depotAggregat;
        if (TestUtil.findAll(em, DepotAggregat.class).isEmpty()) {
            depotAggregat = DepotAggregatResourceIT.createEntity(em);
            em.persist(depotAggregat);
            em.flush();
        } else {
            depotAggregat = TestUtil.findAll(em, DepotAggregat.class).get(0);
        }
        agregatTransfert.setSource(depotAggregat);
        // Add required entity
        agregatTransfert.setDestination(depotAggregat);
        return agregatTransfert;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgregatTransfert createUpdatedEntity(EntityManager em) {
        AgregatTransfert agregatTransfert = new AgregatTransfert()
            .transfertDate(UPDATED_TRANSFERT_DATE)
            .quantite(UPDATED_QUANTITE);
        // Add required entity
        DepotAggregat depotAggregat;
        if (TestUtil.findAll(em, DepotAggregat.class).isEmpty()) {
            depotAggregat = DepotAggregatResourceIT.createUpdatedEntity(em);
            em.persist(depotAggregat);
            em.flush();
        } else {
            depotAggregat = TestUtil.findAll(em, DepotAggregat.class).get(0);
        }
        agregatTransfert.setSource(depotAggregat);
        // Add required entity
        agregatTransfert.setDestination(depotAggregat);
        return agregatTransfert;
    }

    @BeforeEach
    public void initTest() {
        agregatTransfert = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgregatTransfert() throws Exception {
        int databaseSizeBeforeCreate = agregatTransfertRepository.findAll().size();

        // Create the AgregatTransfert
        restAgregatTransfertMockMvc.perform(post("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agregatTransfert)))
            .andExpect(status().isCreated());

        // Validate the AgregatTransfert in the database
        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeCreate + 1);
        AgregatTransfert testAgregatTransfert = agregatTransfertList.get(agregatTransfertList.size() - 1);
        assertThat(testAgregatTransfert.getTransfertDate()).isEqualTo(DEFAULT_TRANSFERT_DATE);
        assertThat(testAgregatTransfert.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    public void createAgregatTransfertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agregatTransfertRepository.findAll().size();

        // Create the AgregatTransfert with an existing ID
        agregatTransfert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgregatTransfertMockMvc.perform(post("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agregatTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the AgregatTransfert in the database
        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransfertDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = agregatTransfertRepository.findAll().size();
        // set the field null
        agregatTransfert.setTransfertDate(null);

        // Create the AgregatTransfert, which fails.

        restAgregatTransfertMockMvc.perform(post("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agregatTransfert)))
            .andExpect(status().isBadRequest());

        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = agregatTransfertRepository.findAll().size();
        // set the field null
        agregatTransfert.setQuantite(null);

        // Create the AgregatTransfert, which fails.

        restAgregatTransfertMockMvc.perform(post("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agregatTransfert)))
            .andExpect(status().isBadRequest());

        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgregatTransferts() throws Exception {
        // Initialize the database
        agregatTransfertRepository.saveAndFlush(agregatTransfert);

        // Get all the agregatTransfertList
        restAgregatTransfertMockMvc.perform(get("/api/agregat-transferts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agregatTransfert.getId().intValue())))
            .andExpect(jsonPath("$.[*].transfertDate").value(hasItem(DEFAULT_TRANSFERT_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAgregatTransfert() throws Exception {
        // Initialize the database
        agregatTransfertRepository.saveAndFlush(agregatTransfert);

        // Get the agregatTransfert
        restAgregatTransfertMockMvc.perform(get("/api/agregat-transferts/{id}", agregatTransfert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agregatTransfert.getId().intValue()))
            .andExpect(jsonPath("$.transfertDate").value(DEFAULT_TRANSFERT_DATE.toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgregatTransfert() throws Exception {
        // Get the agregatTransfert
        restAgregatTransfertMockMvc.perform(get("/api/agregat-transferts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgregatTransfert() throws Exception {
        // Initialize the database
        agregatTransfertService.save(agregatTransfert);

        int databaseSizeBeforeUpdate = agregatTransfertRepository.findAll().size();

        // Update the agregatTransfert
        AgregatTransfert updatedAgregatTransfert = agregatTransfertRepository.findById(agregatTransfert.getId()).get();
        // Disconnect from session so that the updates on updatedAgregatTransfert are not directly saved in db
        em.detach(updatedAgregatTransfert);
        updatedAgregatTransfert
            .transfertDate(UPDATED_TRANSFERT_DATE)
            .quantite(UPDATED_QUANTITE);

        restAgregatTransfertMockMvc.perform(put("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgregatTransfert)))
            .andExpect(status().isOk());

        // Validate the AgregatTransfert in the database
        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeUpdate);
        AgregatTransfert testAgregatTransfert = agregatTransfertList.get(agregatTransfertList.size() - 1);
        assertThat(testAgregatTransfert.getTransfertDate()).isEqualTo(UPDATED_TRANSFERT_DATE);
        assertThat(testAgregatTransfert.getQuantite()).isEqualTo(UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgregatTransfert() throws Exception {
        int databaseSizeBeforeUpdate = agregatTransfertRepository.findAll().size();

        // Create the AgregatTransfert

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgregatTransfertMockMvc.perform(put("/api/agregat-transferts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agregatTransfert)))
            .andExpect(status().isBadRequest());

        // Validate the AgregatTransfert in the database
        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgregatTransfert() throws Exception {
        // Initialize the database
        agregatTransfertService.save(agregatTransfert);

        int databaseSizeBeforeDelete = agregatTransfertRepository.findAll().size();

        // Delete the agregatTransfert
        restAgregatTransfertMockMvc.perform(delete("/api/agregat-transferts/{id}", agregatTransfert.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgregatTransfert> agregatTransfertList = agregatTransfertRepository.findAll();
        assertThat(agregatTransfertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
