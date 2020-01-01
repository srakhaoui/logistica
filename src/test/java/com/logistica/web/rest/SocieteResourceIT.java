package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Societe;
import com.logistica.repository.SocieteRepository;
import com.logistica.service.SocieteService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.SocieteCriteria;
import com.logistica.service.SocieteQueryService;

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
 * Integration tests for the {@link SocieteResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class SocieteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private SocieteService societeService;

    @Autowired
    private SocieteQueryService societeQueryService;

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

    private MockMvc restSocieteMockMvc;

    private Societe societe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocieteResource societeResource = new SocieteResource(societeService, societeQueryService);
        this.restSocieteMockMvc = MockMvcBuilders.standaloneSetup(societeResource)
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
    public static Societe createEntity(EntityManager em) {
        Societe societe = new Societe()
            .nom(DEFAULT_NOM);
        return societe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societe createUpdatedEntity(EntityManager em) {
        Societe societe = new Societe()
            .nom(UPDATED_NOM);
        return societe;
    }

    @BeforeEach
    public void initTest() {
        societe = createEntity(em);
    }

    @Test
    @Transactional
    public void createSociete() throws Exception {
        int databaseSizeBeforeCreate = societeRepository.findAll().size();

        // Create the Societe
        restSocieteMockMvc.perform(post("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isCreated());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate + 1);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createSocieteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = societeRepository.findAll().size();

        // Create the Societe with an existing ID
        societe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMockMvc.perform(post("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = societeRepository.findAll().size();
        // set the field null
        societe.setNom(null);

        // Create the Societe, which fails.

        restSocieteMockMvc.perform(post("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSocietes() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList
        restSocieteMockMvc.perform(get("/api/societes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getSociete() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get the societe
        restSocieteMockMvc.perform(get("/api/societes/{id}", societe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(societe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getSocietesByIdFiltering() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        Long id = societe.getId();

        defaultSocieteShouldBeFound("id.equals=" + id);
        defaultSocieteShouldNotBeFound("id.notEquals=" + id);

        defaultSocieteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocieteShouldNotBeFound("id.greaterThan=" + id);

        defaultSocieteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocieteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSocietesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom equals to DEFAULT_NOM
        defaultSocieteShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the societeList where nom equals to UPDATED_NOM
        defaultSocieteShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSocietesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom not equals to DEFAULT_NOM
        defaultSocieteShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the societeList where nom not equals to UPDATED_NOM
        defaultSocieteShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSocietesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultSocieteShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the societeList where nom equals to UPDATED_NOM
        defaultSocieteShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSocietesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom is not null
        defaultSocieteShouldBeFound("nom.specified=true");

        // Get all the societeList where nom is null
        defaultSocieteShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllSocietesByNomContainsSomething() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom contains DEFAULT_NOM
        defaultSocieteShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the societeList where nom contains UPDATED_NOM
        defaultSocieteShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSocietesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        societeRepository.saveAndFlush(societe);

        // Get all the societeList where nom does not contain DEFAULT_NOM
        defaultSocieteShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the societeList where nom does not contain UPDATED_NOM
        defaultSocieteShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocieteShouldBeFound(String filter) throws Exception {
        restSocieteMockMvc.perform(get("/api/societes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restSocieteMockMvc.perform(get("/api/societes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocieteShouldNotBeFound(String filter) throws Exception {
        restSocieteMockMvc.perform(get("/api/societes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocieteMockMvc.perform(get("/api/societes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSociete() throws Exception {
        // Get the societe
        restSocieteMockMvc.perform(get("/api/societes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSociete() throws Exception {
        // Initialize the database
        societeService.save(societe);

        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Update the societe
        Societe updatedSociete = societeRepository.findById(societe.getId()).get();
        // Disconnect from session so that the updates on updatedSociete are not directly saved in db
        em.detach(updatedSociete);
        updatedSociete
            .nom(UPDATED_NOM);

        restSocieteMockMvc.perform(put("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSociete)))
            .andExpect(status().isOk());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
        Societe testSociete = societeList.get(societeList.size() - 1);
        assertThat(testSociete.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingSociete() throws Exception {
        int databaseSizeBeforeUpdate = societeRepository.findAll().size();

        // Create the Societe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMockMvc.perform(put("/api/societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societe)))
            .andExpect(status().isBadRequest());

        // Validate the Societe in the database
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSociete() throws Exception {
        // Initialize the database
        societeService.save(societe);

        int databaseSizeBeforeDelete = societeRepository.findAll().size();

        // Delete the societe
        restSocieteMockMvc.perform(delete("/api/societes/{id}", societe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Societe> societeList = societeRepository.findAll();
        assertThat(societeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
