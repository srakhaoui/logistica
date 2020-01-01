package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Fournisseur;
import com.logistica.repository.FournisseurRepository;
import com.logistica.service.FournisseurService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.FournisseurCriteria;
import com.logistica.service.FournisseurQueryService;

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
 * Integration tests for the {@link FournisseurResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class FournisseurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "0131801849";
    private static final String UPDATED_TELEPHONE = "0478620505";

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    private FournisseurQueryService fournisseurQueryService;

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

    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FournisseurResource fournisseurResource = new FournisseurResource(fournisseurService, fournisseurQueryService);
        this.restFournisseurMockMvc = MockMvcBuilders.standaloneSetup(fournisseurResource)
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
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE);
        return fournisseur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fournisseur createUpdatedEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);
        return fournisseur;
    }

    @BeforeEach
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    public void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testFournisseur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createFournisseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = fournisseurRepository.findAll().size();
        // set the field null
        fournisseur.setNom(null);

        // Create the Fournisseur, which fails.

        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc.perform(get("/api/fournisseurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }
    
    @Test
    @Transactional
    public void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }


    @Test
    @Transactional
    public void getFournisseursByIdFiltering() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        Long id = fournisseur.getId();

        defaultFournisseurShouldBeFound("id.equals=" + id);
        defaultFournisseurShouldNotBeFound("id.notEquals=" + id);

        defaultFournisseurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFournisseurShouldNotBeFound("id.greaterThan=" + id);

        defaultFournisseurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFournisseurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFournisseursByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom equals to DEFAULT_NOM
        defaultFournisseurShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the fournisseurList where nom equals to UPDATED_NOM
        defaultFournisseurShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseursByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom not equals to DEFAULT_NOM
        defaultFournisseurShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the fournisseurList where nom not equals to UPDATED_NOM
        defaultFournisseurShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseursByNomIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultFournisseurShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the fournisseurList where nom equals to UPDATED_NOM
        defaultFournisseurShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseursByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom is not null
        defaultFournisseurShouldBeFound("nom.specified=true");

        // Get all the fournisseurList where nom is null
        defaultFournisseurShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournisseursByNomContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom contains DEFAULT_NOM
        defaultFournisseurShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the fournisseurList where nom contains UPDATED_NOM
        defaultFournisseurShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllFournisseursByNomNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where nom does not contain DEFAULT_NOM
        defaultFournisseurShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the fournisseurList where nom does not contain UPDATED_NOM
        defaultFournisseurShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllFournisseursByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse equals to DEFAULT_ADRESSE
        defaultFournisseurShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the fournisseurList where adresse equals to UPDATED_ADRESSE
        defaultFournisseurShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse not equals to DEFAULT_ADRESSE
        defaultFournisseurShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the fournisseurList where adresse not equals to UPDATED_ADRESSE
        defaultFournisseurShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultFournisseurShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the fournisseurList where adresse equals to UPDATED_ADRESSE
        defaultFournisseurShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse is not null
        defaultFournisseurShouldBeFound("adresse.specified=true");

        // Get all the fournisseurList where adresse is null
        defaultFournisseurShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournisseursByAdresseContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse contains DEFAULT_ADRESSE
        defaultFournisseurShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the fournisseurList where adresse contains UPDATED_ADRESSE
        defaultFournisseurShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where adresse does not contain DEFAULT_ADRESSE
        defaultFournisseurShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the fournisseurList where adresse does not contain UPDATED_ADRESSE
        defaultFournisseurShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllFournisseursByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone equals to DEFAULT_TELEPHONE
        defaultFournisseurShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurList where telephone equals to UPDATED_TELEPHONE
        defaultFournisseurShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone not equals to DEFAULT_TELEPHONE
        defaultFournisseurShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurList where telephone not equals to UPDATED_TELEPHONE
        defaultFournisseurShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultFournisseurShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the fournisseurList where telephone equals to UPDATED_TELEPHONE
        defaultFournisseurShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone is not null
        defaultFournisseurShouldBeFound("telephone.specified=true");

        // Get all the fournisseurList where telephone is null
        defaultFournisseurShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllFournisseursByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone contains DEFAULT_TELEPHONE
        defaultFournisseurShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurList where telephone contains UPDATED_TELEPHONE
        defaultFournisseurShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllFournisseursByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList where telephone does not contain DEFAULT_TELEPHONE
        defaultFournisseurShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the fournisseurList where telephone does not contain UPDATED_TELEPHONE
        defaultFournisseurShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFournisseurShouldBeFound(String filter) throws Exception {
        restFournisseurMockMvc.perform(get("/api/fournisseurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));

        // Check, that the count call also returns 1
        restFournisseurMockMvc.perform(get("/api/fournisseurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFournisseurShouldNotBeFound(String filter) throws Exception {
        restFournisseurMockMvc.perform(get("/api/fournisseurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFournisseurMockMvc.perform(get("/api/fournisseurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFournisseur() throws Exception {
        // Initialize the database
        fournisseurService.save(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);

        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur)))
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Create the Fournisseur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurService.save(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Delete the fournisseur
        restFournisseurMockMvc.perform(delete("/api/fournisseurs/{id}", fournisseur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
