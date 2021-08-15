package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.FournisseurGrossiste;
import com.logistica.repository.FournisseurGrossisteRepository;
import com.logistica.service.FournisseurGrossisteQueryService;
import com.logistica.service.FournisseurGrossisteService;
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
 * Integration tests for the {@link FournisseurGrossisteResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class FournisseurGrossisteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private FournisseurGrossisteRepository fournisseurGrossisteRepository;

    @Autowired
    private FournisseurGrossisteService fournisseurGrossisteService;

    @Autowired
    private FournisseurGrossisteQueryService fournisseurGrossisteQueryService;

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

    private MockMvc restFournisseurGrossisteMockMvc;

    private FournisseurGrossiste fournisseurGrossiste;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FournisseurGrossisteResource fournisseurGrossisteResource = new FournisseurGrossisteResource(fournisseurGrossisteService, fournisseurGrossisteQueryService);
        this.restFournisseurGrossisteMockMvc = MockMvcBuilders.standaloneSetup(fournisseurGrossisteResource)
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
    public static FournisseurGrossiste createEntity(EntityManager em) {
        FournisseurGrossiste fournisseurGrossiste = new FournisseurGrossiste()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE);
        return fournisseurGrossiste;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FournisseurGrossiste createUpdatedEntity(EntityManager em) {
        FournisseurGrossiste fournisseurGrossiste = new FournisseurGrossiste()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);
        return fournisseurGrossiste;
    }

    @BeforeEach
    public void initTest() {
        fournisseurGrossiste = createEntity(em);
    }

    @Test
    @Transactional
    public void createFournisseurGrossiste() throws Exception {
        int databaseSizeBeforeCreate = fournisseurGrossisteRepository.findAll().size();

        // Create the FournisseurGrossiste
        restFournisseurGrossisteMockMvc.perform(post("/api/fournisseur-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseurGrossiste)))
            .andExpect(status().isCreated());

        // Validate the FournisseurGrossiste in the database
        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeCreate + 1);
        FournisseurGrossiste testFournisseurGrossiste = fournisseurGrossisteList.get(fournisseurGrossisteList.size() - 1);
        assertThat(testFournisseurGrossiste.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseurGrossiste.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseurGrossiste.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createFournisseurGrossisteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fournisseurGrossisteRepository.findAll().size();

        // Create the FournisseurGrossiste with an existing ID
        fournisseurGrossiste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurGrossisteMockMvc.perform(post("/api/fournisseur-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseurGrossiste)))
            .andExpect(status().isBadRequest());

        // Validate the FournisseurGrossiste in the database
        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurGrossisteRepository.findAll().size();
        // set the field null
        fournisseurGrossiste.setNom(null);

        // Create the FournisseurGrossiste, which fails.

        restFournisseurGrossisteMockMvc.perform(post("/api/fournisseur-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseurGrossiste)))
            .andExpect(status().isBadRequest());

        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistes() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseurGrossiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    @Transactional
    public void getFournisseurGrossiste() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get the fournisseurGrossiste
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes/{id}", fournisseurGrossiste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseurGrossiste.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }


    @Test
    @Transactional
    public void getFournisseurGrossistesByIdFiltering() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        Long id = fournisseurGrossiste.getId();

        defaultFournisseurGrossisteShouldBeFound("id.equals=" + id);
        defaultFournisseurGrossisteShouldNotBeFound("id.notEquals=" + id);

        defaultFournisseurGrossisteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFournisseurGrossisteShouldNotBeFound("id.greaterThan=" + id);

        defaultFournisseurGrossisteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFournisseurGrossisteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom equals to DEFAULT_NOM
        defaultFournisseurGrossisteShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the fournisseurGrossisteList where nom equals to UPDATED_NOM
        defaultFournisseurGrossisteShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom not equals to DEFAULT_NOM
        defaultFournisseurGrossisteShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the fournisseurGrossisteList where nom not equals to UPDATED_NOM
        defaultFournisseurGrossisteShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultFournisseurGrossisteShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the fournisseurGrossisteList where nom equals to UPDATED_NOM
        defaultFournisseurGrossisteShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom is not null
        defaultFournisseurGrossisteShouldBeFound("nom.specified=true");

        // Get all the fournisseurGrossisteList where nom is null
        defaultFournisseurGrossisteShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom contains DEFAULT_NOM
        defaultFournisseurGrossisteShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the fournisseurGrossisteList where nom contains UPDATED_NOM
        defaultFournisseurGrossisteShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where nom does not contain DEFAULT_NOM
        defaultFournisseurGrossisteShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the fournisseurGrossisteList where nom does not contain UPDATED_NOM
        defaultFournisseurGrossisteShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse equals to DEFAULT_ADRESSE
        defaultFournisseurGrossisteShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the fournisseurGrossisteList where adresse equals to UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse not equals to DEFAULT_ADRESSE
        defaultFournisseurGrossisteShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the fournisseurGrossisteList where adresse not equals to UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the fournisseurGrossisteList where adresse equals to UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse is not null
        defaultFournisseurGrossisteShouldBeFound("adresse.specified=true");

        // Get all the fournisseurGrossisteList where adresse is null
        defaultFournisseurGrossisteShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse contains DEFAULT_ADRESSE
        defaultFournisseurGrossisteShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the fournisseurGrossisteList where adresse contains UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where adresse does not contain DEFAULT_ADRESSE
        defaultFournisseurGrossisteShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the fournisseurGrossisteList where adresse does not contain UPDATED_ADRESSE
        defaultFournisseurGrossisteShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone equals to DEFAULT_TELEPHONE
        defaultFournisseurGrossisteShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurGrossisteList where telephone equals to UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone not equals to DEFAULT_TELEPHONE
        defaultFournisseurGrossisteShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurGrossisteList where telephone not equals to UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the fournisseurGrossisteList where telephone equals to UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone is not null
        defaultFournisseurGrossisteShouldBeFound("telephone.specified=true");

        // Get all the fournisseurGrossisteList where telephone is null
        defaultFournisseurGrossisteShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone contains DEFAULT_TELEPHONE
        defaultFournisseurGrossisteShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurGrossisteList where telephone contains UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseurGrossistesByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurGrossisteRepository.saveAndFlush(fournisseurGrossiste);

        // Get all the fournisseurGrossisteList where telephone does not contain DEFAULT_TELEPHONE
        defaultFournisseurGrossisteShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurGrossisteList where telephone does not contain UPDATED_TELEPHONE
        defaultFournisseurGrossisteShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFournisseurGrossisteShouldBeFound(String filter) throws Exception {
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseurGrossiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));

        // Check, that the count call also returns 1
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFournisseurGrossisteShouldNotBeFound(String filter) throws Exception {
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFournisseurGrossiste() throws Exception {
        // Get the fournisseurGrossiste
        restFournisseurGrossisteMockMvc.perform(get("/api/fournisseur-grossistes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFournisseurGrossiste() throws Exception {
        // Initialize the database
        fournisseurGrossisteService.save(fournisseurGrossiste);

        int databaseSizeBeforeUpdate = fournisseurGrossisteRepository.findAll().size();

        // Update the fournisseurGrossiste
        FournisseurGrossiste updatedFournisseurGrossiste = fournisseurGrossisteRepository.findById(fournisseurGrossiste.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseurGrossiste are not directly saved in db
        em.detach(updatedFournisseurGrossiste);
        updatedFournisseurGrossiste
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);

        restFournisseurGrossisteMockMvc.perform(put("/api/fournisseur-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFournisseurGrossiste)))
            .andExpect(status().isOk());

        // Validate the FournisseurGrossiste in the database
        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeUpdate);
        FournisseurGrossiste testFournisseurGrossiste = fournisseurGrossisteList.get(fournisseurGrossisteList.size() - 1);
        assertThat(testFournisseurGrossiste.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseurGrossiste.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseurGrossiste.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingFournisseurGrossiste() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurGrossisteRepository.findAll().size();

        // Create the FournisseurGrossiste

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurGrossisteMockMvc.perform(put("/api/fournisseur-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseurGrossiste)))
            .andExpect(status().isBadRequest());

        // Validate the FournisseurGrossiste in the database
        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFournisseurGrossiste() throws Exception {
        // Initialize the database
        fournisseurGrossisteService.save(fournisseurGrossiste);

        int databaseSizeBeforeDelete = fournisseurGrossisteRepository.findAll().size();

        // Delete the fournisseurGrossiste
        restFournisseurGrossisteMockMvc.perform(delete("/api/fournisseur-grossistes/{id}", fournisseurGrossiste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FournisseurGrossiste> fournisseurGrossisteList = fournisseurGrossisteRepository.findAll();
        assertThat(fournisseurGrossisteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
