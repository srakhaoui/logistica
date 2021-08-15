package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Carburant;
import com.logistica.repository.CarburantRepository;
import com.logistica.service.CarburantQueryService;
import com.logistica.service.CarburantService;
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
 * Integration tests for the {@link CarburantResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class CarburantResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    @Autowired
    private CarburantRepository carburantRepository;

    @Autowired
    private CarburantService carburantService;

    @Autowired
    private CarburantQueryService carburantQueryService;

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

    private MockMvc restCarburantMockMvc;

    private Carburant carburant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarburantResource carburantResource = new CarburantResource(carburantService, carburantQueryService);
        this.restCarburantMockMvc = MockMvcBuilders.standaloneSetup(carburantResource)
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
    public static Carburant createEntity(EntityManager em) {
        Carburant carburant = new Carburant()
            .code(DEFAULT_CODE)
            .categorie(DEFAULT_CATEGORIE);
        return carburant;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carburant createUpdatedEntity(EntityManager em) {
        Carburant carburant = new Carburant()
            .code(UPDATED_CODE)
            .categorie(UPDATED_CATEGORIE);
        return carburant;
    }

    @BeforeEach
    public void initTest() {
        carburant = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarburant() throws Exception {
        int databaseSizeBeforeCreate = carburantRepository.findAll().size();

        // Create the Carburant
        restCarburantMockMvc.perform(post("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburant)))
            .andExpect(status().isCreated());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeCreate + 1);
        Carburant testCarburant = carburantList.get(carburantList.size() - 1);
        assertThat(testCarburant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCarburant.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
    }

    @Test
    @Transactional
    public void createCarburantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carburantRepository.findAll().size();

        // Create the Carburant with an existing ID
        carburant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarburantMockMvc.perform(post("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburant)))
            .andExpect(status().isBadRequest());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carburantRepository.findAll().size();
        // set the field null
        carburant.setCode(null);

        // Create the Carburant, which fails.

        restCarburantMockMvc.perform(post("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburant)))
            .andExpect(status().isBadRequest());

        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarburants() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList
        restCarburantMockMvc.perform(get("/api/carburants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)));
    }

    @Test
    @Transactional
    public void getCarburant() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get the carburant
        restCarburantMockMvc.perform(get("/api/carburants/{id}", carburant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carburant.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE));
    }


    @Test
    @Transactional
    public void getCarburantsByIdFiltering() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        Long id = carburant.getId();

        defaultCarburantShouldBeFound("id.equals=" + id);
        defaultCarburantShouldNotBeFound("id.notEquals=" + id);

        defaultCarburantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCarburantShouldNotBeFound("id.greaterThan=" + id);

        defaultCarburantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCarburantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCarburantsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code equals to DEFAULT_CODE
        defaultCarburantShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the carburantList where code equals to UPDATED_CODE
        defaultCarburantShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code not equals to DEFAULT_CODE
        defaultCarburantShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the carburantList where code not equals to UPDATED_CODE
        defaultCarburantShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCarburantShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the carburantList where code equals to UPDATED_CODE
        defaultCarburantShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code is not null
        defaultCarburantShouldBeFound("code.specified=true");

        // Get all the carburantList where code is null
        defaultCarburantShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarburantsByCodeContainsSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code contains DEFAULT_CODE
        defaultCarburantShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the carburantList where code contains UPDATED_CODE
        defaultCarburantShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where code does not contain DEFAULT_CODE
        defaultCarburantShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the carburantList where code does not contain UPDATED_CODE
        defaultCarburantShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCarburantsByCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie equals to DEFAULT_CATEGORIE
        defaultCarburantShouldBeFound("categorie.equals=" + DEFAULT_CATEGORIE);

        // Get all the carburantList where categorie equals to UPDATED_CATEGORIE
        defaultCarburantShouldNotBeFound("categorie.equals=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCategorieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie not equals to DEFAULT_CATEGORIE
        defaultCarburantShouldNotBeFound("categorie.notEquals=" + DEFAULT_CATEGORIE);

        // Get all the carburantList where categorie not equals to UPDATED_CATEGORIE
        defaultCarburantShouldBeFound("categorie.notEquals=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCategorieIsInShouldWork() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie in DEFAULT_CATEGORIE or UPDATED_CATEGORIE
        defaultCarburantShouldBeFound("categorie.in=" + DEFAULT_CATEGORIE + "," + UPDATED_CATEGORIE);

        // Get all the carburantList where categorie equals to UPDATED_CATEGORIE
        defaultCarburantShouldNotBeFound("categorie.in=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCategorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie is not null
        defaultCarburantShouldBeFound("categorie.specified=true");

        // Get all the carburantList where categorie is null
        defaultCarburantShouldNotBeFound("categorie.specified=false");
    }

    @Test
    @Transactional
    public void getAllCarburantsByCategorieContainsSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie contains DEFAULT_CATEGORIE
        defaultCarburantShouldBeFound("categorie.contains=" + DEFAULT_CATEGORIE);

        // Get all the carburantList where categorie contains UPDATED_CATEGORIE
        defaultCarburantShouldNotBeFound("categorie.contains=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllCarburantsByCategorieNotContainsSomething() throws Exception {
        // Initialize the database
        carburantRepository.saveAndFlush(carburant);

        // Get all the carburantList where categorie does not contain DEFAULT_CATEGORIE
        defaultCarburantShouldNotBeFound("categorie.doesNotContain=" + DEFAULT_CATEGORIE);

        // Get all the carburantList where categorie does not contain UPDATED_CATEGORIE
        defaultCarburantShouldBeFound("categorie.doesNotContain=" + UPDATED_CATEGORIE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCarburantShouldBeFound(String filter) throws Exception {
        restCarburantMockMvc.perform(get("/api/carburants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carburant.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)));

        // Check, that the count call also returns 1
        restCarburantMockMvc.perform(get("/api/carburants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCarburantShouldNotBeFound(String filter) throws Exception {
        restCarburantMockMvc.perform(get("/api/carburants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCarburantMockMvc.perform(get("/api/carburants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCarburant() throws Exception {
        // Get the carburant
        restCarburantMockMvc.perform(get("/api/carburants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarburant() throws Exception {
        // Initialize the database
        carburantService.save(carburant);

        int databaseSizeBeforeUpdate = carburantRepository.findAll().size();

        // Update the carburant
        Carburant updatedCarburant = carburantRepository.findById(carburant.getId()).get();
        // Disconnect from session so that the updates on updatedCarburant are not directly saved in db
        em.detach(updatedCarburant);
        updatedCarburant
            .code(UPDATED_CODE)
            .categorie(UPDATED_CATEGORIE);

        restCarburantMockMvc.perform(put("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarburant)))
            .andExpect(status().isOk());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeUpdate);
        Carburant testCarburant = carburantList.get(carburantList.size() - 1);
        assertThat(testCarburant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCarburant.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void updateNonExistingCarburant() throws Exception {
        int databaseSizeBeforeUpdate = carburantRepository.findAll().size();

        // Create the Carburant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarburantMockMvc.perform(put("/api/carburants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carburant)))
            .andExpect(status().isBadRequest());

        // Validate the Carburant in the database
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarburant() throws Exception {
        // Initialize the database
        carburantService.save(carburant);

        int databaseSizeBeforeDelete = carburantRepository.findAll().size();

        // Delete the carburant
        restCarburantMockMvc.perform(delete("/api/carburants/{id}", carburant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carburant> carburantList = carburantRepository.findAll();
        assertThat(carburantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
