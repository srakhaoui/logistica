package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.DepotAggregat;
import com.logistica.repository.DepotAggregatRepository;
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
 * Integration tests for the {@link DepotAggregatResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class DepotAggregatResourceIT {

    private static final Float DEFAULT_STOCK = 0F;
    private static final Float UPDATED_STOCK = 1F;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private DepotAggregatRepository depotAggregatRepository;

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

    private MockMvc restDepotAggregatMockMvc;

    private DepotAggregat depotAggregat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepotAggregatResource depotAggregatResource = new DepotAggregatResource(depotAggregatRepository);
        this.restDepotAggregatMockMvc = MockMvcBuilders.standaloneSetup(depotAggregatResource)
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
    public static DepotAggregat createEntity(EntityManager em) {
        DepotAggregat depotAggregat = new DepotAggregat()
            .stock(DEFAULT_STOCK)
            .nom(DEFAULT_NOM);
        return depotAggregat;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepotAggregat createUpdatedEntity(EntityManager em) {
        DepotAggregat depotAggregat = new DepotAggregat()
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM);
        return depotAggregat;
    }

    @BeforeEach
    public void initTest() {
        depotAggregat = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepotAggregat() throws Exception {
        int databaseSizeBeforeCreate = depotAggregatRepository.findAll().size();

        // Create the DepotAggregat
        restDepotAggregatMockMvc.perform(post("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depotAggregat)))
            .andExpect(status().isCreated());

        // Validate the DepotAggregat in the database
        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeCreate + 1);
        DepotAggregat testDepotAggregat = depotAggregatList.get(depotAggregatList.size() - 1);
        assertThat(testDepotAggregat.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testDepotAggregat.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createDepotAggregatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depotAggregatRepository.findAll().size();

        // Create the DepotAggregat with an existing ID
        depotAggregat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepotAggregatMockMvc.perform(post("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depotAggregat)))
            .andExpect(status().isBadRequest());

        // Validate the DepotAggregat in the database
        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotAggregatRepository.findAll().size();
        // set the field null
        depotAggregat.setStock(null);

        // Create the DepotAggregat, which fails.

        restDepotAggregatMockMvc.perform(post("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depotAggregat)))
            .andExpect(status().isBadRequest());

        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = depotAggregatRepository.findAll().size();
        // set the field null
        depotAggregat.setNom(null);

        // Create the DepotAggregat, which fails.

        restDepotAggregatMockMvc.perform(post("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depotAggregat)))
            .andExpect(status().isBadRequest());

        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepotAggregats() throws Exception {
        // Initialize the database
        depotAggregatRepository.saveAndFlush(depotAggregat);

        // Get all the depotAggregatList
        restDepotAggregatMockMvc.perform(get("/api/depot-aggregats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depotAggregat.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    public void getDepotAggregat() throws Exception {
        // Initialize the database
        depotAggregatRepository.saveAndFlush(depotAggregat);

        // Get the depotAggregat
        restDepotAggregatMockMvc.perform(get("/api/depot-aggregats/{id}", depotAggregat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depotAggregat.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.doubleValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    public void getNonExistingDepotAggregat() throws Exception {
        // Get the depotAggregat
        restDepotAggregatMockMvc.perform(get("/api/depot-aggregats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepotAggregat() throws Exception {
        // Initialize the database
        depotAggregatRepository.saveAndFlush(depotAggregat);

        int databaseSizeBeforeUpdate = depotAggregatRepository.findAll().size();

        // Update the depotAggregat
        DepotAggregat updatedDepotAggregat = depotAggregatRepository.findById(depotAggregat.getId()).get();
        // Disconnect from session so that the updates on updatedDepotAggregat are not directly saved in db
        em.detach(updatedDepotAggregat);
        updatedDepotAggregat
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM);

        restDepotAggregatMockMvc.perform(put("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepotAggregat)))
            .andExpect(status().isOk());

        // Validate the DepotAggregat in the database
        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeUpdate);
        DepotAggregat testDepotAggregat = depotAggregatList.get(depotAggregatList.size() - 1);
        assertThat(testDepotAggregat.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testDepotAggregat.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingDepotAggregat() throws Exception {
        int databaseSizeBeforeUpdate = depotAggregatRepository.findAll().size();

        // Create the DepotAggregat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepotAggregatMockMvc.perform(put("/api/depot-aggregats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depotAggregat)))
            .andExpect(status().isBadRequest());

        // Validate the DepotAggregat in the database
        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepotAggregat() throws Exception {
        // Initialize the database
        depotAggregatRepository.saveAndFlush(depotAggregat);

        int databaseSizeBeforeDelete = depotAggregatRepository.findAll().size();

        // Delete the depotAggregat
        restDepotAggregatMockMvc.perform(delete("/api/depot-aggregats/{id}", depotAggregat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepotAggregat> depotAggregatList = depotAggregatRepository.findAll();
        assertThat(depotAggregatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
