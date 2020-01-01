package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Trajet;
import com.logistica.repository.TrajetRepository;
import com.logistica.service.TrajetService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.TrajetCriteria;
import com.logistica.service.TrajetQueryService;

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
 * Integration tests for the {@link TrajetResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TrajetResourceIT {

    private static final String DEFAULT_DEPART = "AAAAAAAAAA";
    private static final String UPDATED_DEPART = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final Float DEFAULT_COMMISSION = 1F;
    private static final Float UPDATED_COMMISSION = 2F;
    private static final Float SMALLER_COMMISSION = 1F - 1F;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private TrajetService trajetService;

    @Autowired
    private TrajetQueryService trajetQueryService;

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

    private MockMvc restTrajetMockMvc;

    private Trajet trajet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrajetResource trajetResource = new TrajetResource(trajetService, trajetQueryService);
        this.restTrajetMockMvc = MockMvcBuilders.standaloneSetup(trajetResource)
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
    public static Trajet createEntity(EntityManager em) {
        Trajet trajet = new Trajet()
            .depart(DEFAULT_DEPART)
            .destination(DEFAULT_DESTINATION)
            .commission(DEFAULT_COMMISSION);
        return trajet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajet createUpdatedEntity(EntityManager em) {
        Trajet trajet = new Trajet()
            .depart(UPDATED_DEPART)
            .destination(UPDATED_DESTINATION)
            .commission(UPDATED_COMMISSION);
        return trajet;
    }

    @BeforeEach
    public void initTest() {
        trajet = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrajet() throws Exception {
        int databaseSizeBeforeCreate = trajetRepository.findAll().size();

        // Create the Trajet
        restTrajetMockMvc.perform(post("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trajet)))
            .andExpect(status().isCreated());

        // Validate the Trajet in the database
        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeCreate + 1);
        Trajet testTrajet = trajetList.get(trajetList.size() - 1);
        assertThat(testTrajet.getDepart()).isEqualTo(DEFAULT_DEPART);
        assertThat(testTrajet.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testTrajet.getCommission()).isEqualTo(DEFAULT_COMMISSION);
    }

    @Test
    @Transactional
    public void createTrajetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trajetRepository.findAll().size();

        // Create the Trajet with an existing ID
        trajet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrajetMockMvc.perform(post("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trajet)))
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDepartIsRequired() throws Exception {
        int databaseSizeBeforeTest = trajetRepository.findAll().size();
        // set the field null
        trajet.setDepart(null);

        // Create the Trajet, which fails.

        restTrajetMockMvc.perform(post("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trajet)))
            .andExpect(status().isBadRequest());

        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = trajetRepository.findAll().size();
        // set the field null
        trajet.setDestination(null);

        // Create the Trajet, which fails.

        restTrajetMockMvc.perform(post("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trajet)))
            .andExpect(status().isBadRequest());

        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrajets() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList
        restTrajetMockMvc.perform(get("/api/trajets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajet.getId().intValue())))
            .andExpect(jsonPath("$.[*].depart").value(hasItem(DEFAULT_DEPART)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTrajet() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get the trajet
        restTrajetMockMvc.perform(get("/api/trajets/{id}", trajet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trajet.getId().intValue()))
            .andExpect(jsonPath("$.depart").value(DEFAULT_DEPART))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()));
    }


    @Test
    @Transactional
    public void getTrajetsByIdFiltering() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        Long id = trajet.getId();

        defaultTrajetShouldBeFound("id.equals=" + id);
        defaultTrajetShouldNotBeFound("id.notEquals=" + id);

        defaultTrajetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTrajetShouldNotBeFound("id.greaterThan=" + id);

        defaultTrajetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTrajetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTrajetsByDepartIsEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart equals to DEFAULT_DEPART
        defaultTrajetShouldBeFound("depart.equals=" + DEFAULT_DEPART);

        // Get all the trajetList where depart equals to UPDATED_DEPART
        defaultTrajetShouldNotBeFound("depart.equals=" + UPDATED_DEPART);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDepartIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart not equals to DEFAULT_DEPART
        defaultTrajetShouldNotBeFound("depart.notEquals=" + DEFAULT_DEPART);

        // Get all the trajetList where depart not equals to UPDATED_DEPART
        defaultTrajetShouldBeFound("depart.notEquals=" + UPDATED_DEPART);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDepartIsInShouldWork() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart in DEFAULT_DEPART or UPDATED_DEPART
        defaultTrajetShouldBeFound("depart.in=" + DEFAULT_DEPART + "," + UPDATED_DEPART);

        // Get all the trajetList where depart equals to UPDATED_DEPART
        defaultTrajetShouldNotBeFound("depart.in=" + UPDATED_DEPART);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDepartIsNullOrNotNull() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart is not null
        defaultTrajetShouldBeFound("depart.specified=true");

        // Get all the trajetList where depart is null
        defaultTrajetShouldNotBeFound("depart.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrajetsByDepartContainsSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart contains DEFAULT_DEPART
        defaultTrajetShouldBeFound("depart.contains=" + DEFAULT_DEPART);

        // Get all the trajetList where depart contains UPDATED_DEPART
        defaultTrajetShouldNotBeFound("depart.contains=" + UPDATED_DEPART);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDepartNotContainsSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where depart does not contain DEFAULT_DEPART
        defaultTrajetShouldNotBeFound("depart.doesNotContain=" + DEFAULT_DEPART);

        // Get all the trajetList where depart does not contain UPDATED_DEPART
        defaultTrajetShouldBeFound("depart.doesNotContain=" + UPDATED_DEPART);
    }


    @Test
    @Transactional
    public void getAllTrajetsByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination equals to DEFAULT_DESTINATION
        defaultTrajetShouldBeFound("destination.equals=" + DEFAULT_DESTINATION);

        // Get all the trajetList where destination equals to UPDATED_DESTINATION
        defaultTrajetShouldNotBeFound("destination.equals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDestinationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination not equals to DEFAULT_DESTINATION
        defaultTrajetShouldNotBeFound("destination.notEquals=" + DEFAULT_DESTINATION);

        // Get all the trajetList where destination not equals to UPDATED_DESTINATION
        defaultTrajetShouldBeFound("destination.notEquals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination in DEFAULT_DESTINATION or UPDATED_DESTINATION
        defaultTrajetShouldBeFound("destination.in=" + DEFAULT_DESTINATION + "," + UPDATED_DESTINATION);

        // Get all the trajetList where destination equals to UPDATED_DESTINATION
        defaultTrajetShouldNotBeFound("destination.in=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination is not null
        defaultTrajetShouldBeFound("destination.specified=true");

        // Get all the trajetList where destination is null
        defaultTrajetShouldNotBeFound("destination.specified=false");
    }
                @Test
    @Transactional
    public void getAllTrajetsByDestinationContainsSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination contains DEFAULT_DESTINATION
        defaultTrajetShouldBeFound("destination.contains=" + DEFAULT_DESTINATION);

        // Get all the trajetList where destination contains UPDATED_DESTINATION
        defaultTrajetShouldNotBeFound("destination.contains=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByDestinationNotContainsSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where destination does not contain DEFAULT_DESTINATION
        defaultTrajetShouldNotBeFound("destination.doesNotContain=" + DEFAULT_DESTINATION);

        // Get all the trajetList where destination does not contain UPDATED_DESTINATION
        defaultTrajetShouldBeFound("destination.doesNotContain=" + UPDATED_DESTINATION);
    }


    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission equals to DEFAULT_COMMISSION
        defaultTrajetShouldBeFound("commission.equals=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission equals to UPDATED_COMMISSION
        defaultTrajetShouldNotBeFound("commission.equals=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission not equals to DEFAULT_COMMISSION
        defaultTrajetShouldNotBeFound("commission.notEquals=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission not equals to UPDATED_COMMISSION
        defaultTrajetShouldBeFound("commission.notEquals=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsInShouldWork() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission in DEFAULT_COMMISSION or UPDATED_COMMISSION
        defaultTrajetShouldBeFound("commission.in=" + DEFAULT_COMMISSION + "," + UPDATED_COMMISSION);

        // Get all the trajetList where commission equals to UPDATED_COMMISSION
        defaultTrajetShouldNotBeFound("commission.in=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission is not null
        defaultTrajetShouldBeFound("commission.specified=true");

        // Get all the trajetList where commission is null
        defaultTrajetShouldNotBeFound("commission.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission is greater than or equal to DEFAULT_COMMISSION
        defaultTrajetShouldBeFound("commission.greaterThanOrEqual=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission is greater than or equal to UPDATED_COMMISSION
        defaultTrajetShouldNotBeFound("commission.greaterThanOrEqual=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission is less than or equal to DEFAULT_COMMISSION
        defaultTrajetShouldBeFound("commission.lessThanOrEqual=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission is less than or equal to SMALLER_COMMISSION
        defaultTrajetShouldNotBeFound("commission.lessThanOrEqual=" + SMALLER_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsLessThanSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission is less than DEFAULT_COMMISSION
        defaultTrajetShouldNotBeFound("commission.lessThan=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission is less than UPDATED_COMMISSION
        defaultTrajetShouldBeFound("commission.lessThan=" + UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void getAllTrajetsByCommissionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        trajetRepository.saveAndFlush(trajet);

        // Get all the trajetList where commission is greater than DEFAULT_COMMISSION
        defaultTrajetShouldNotBeFound("commission.greaterThan=" + DEFAULT_COMMISSION);

        // Get all the trajetList where commission is greater than SMALLER_COMMISSION
        defaultTrajetShouldBeFound("commission.greaterThan=" + SMALLER_COMMISSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTrajetShouldBeFound(String filter) throws Exception {
        restTrajetMockMvc.perform(get("/api/trajets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trajet.getId().intValue())))
            .andExpect(jsonPath("$.[*].depart").value(hasItem(DEFAULT_DEPART)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())));

        // Check, that the count call also returns 1
        restTrajetMockMvc.perform(get("/api/trajets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTrajetShouldNotBeFound(String filter) throws Exception {
        restTrajetMockMvc.perform(get("/api/trajets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTrajetMockMvc.perform(get("/api/trajets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTrajet() throws Exception {
        // Get the trajet
        restTrajetMockMvc.perform(get("/api/trajets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrajet() throws Exception {
        // Initialize the database
        trajetService.save(trajet);

        int databaseSizeBeforeUpdate = trajetRepository.findAll().size();

        // Update the trajet
        Trajet updatedTrajet = trajetRepository.findById(trajet.getId()).get();
        // Disconnect from session so that the updates on updatedTrajet are not directly saved in db
        em.detach(updatedTrajet);
        updatedTrajet
            .depart(UPDATED_DEPART)
            .destination(UPDATED_DESTINATION)
            .commission(UPDATED_COMMISSION);

        restTrajetMockMvc.perform(put("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrajet)))
            .andExpect(status().isOk());

        // Validate the Trajet in the database
        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeUpdate);
        Trajet testTrajet = trajetList.get(trajetList.size() - 1);
        assertThat(testTrajet.getDepart()).isEqualTo(UPDATED_DEPART);
        assertThat(testTrajet.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testTrajet.getCommission()).isEqualTo(UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingTrajet() throws Exception {
        int databaseSizeBeforeUpdate = trajetRepository.findAll().size();

        // Create the Trajet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrajetMockMvc.perform(put("/api/trajets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trajet)))
            .andExpect(status().isBadRequest());

        // Validate the Trajet in the database
        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrajet() throws Exception {
        // Initialize the database
        trajetService.save(trajet);

        int databaseSizeBeforeDelete = trajetRepository.findAll().size();

        // Delete the trajet
        restTrajetMockMvc.perform(delete("/api/trajets/{id}", trajet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trajet> trajetList = trajetRepository.findAll();
        assertThat(trajetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
