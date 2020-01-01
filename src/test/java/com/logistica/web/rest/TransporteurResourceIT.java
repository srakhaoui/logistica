package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.Transporteur;
import com.logistica.domain.Societe;
import com.logistica.repository.TransporteurRepository;
import com.logistica.service.TransporteurService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.TransporteurCriteria;
import com.logistica.service.TransporteurQueryService;

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
 * Integration tests for the {@link TransporteurResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TransporteurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "0974308146";
    private static final String UPDATED_TELEPHONE = "0499357141";

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    @Autowired
    private TransporteurRepository transporteurRepository;

    @Autowired
    private TransporteurService transporteurService;

    @Autowired
    private TransporteurQueryService transporteurQueryService;

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

    private MockMvc restTransporteurMockMvc;

    private Transporteur transporteur;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransporteurResource transporteurResource = new TransporteurResource(transporteurService, transporteurQueryService);
        this.restTransporteurMockMvc = MockMvcBuilders.standaloneSetup(transporteurResource)
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
    public static Transporteur createEntity(EntityManager em) {
        Transporteur transporteur = new Transporteur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .matricule(DEFAULT_MATRICULE);
        return transporteur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporteur createUpdatedEntity(EntityManager em) {
        Transporteur transporteur = new Transporteur()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .matricule(UPDATED_MATRICULE);
        return transporteur;
    }

    @BeforeEach
    public void initTest() {
        transporteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransporteur() throws Exception {
        int databaseSizeBeforeCreate = transporteurRepository.findAll().size();

        // Create the Transporteur
        restTransporteurMockMvc.perform(post("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isCreated());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate + 1);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testTransporteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testTransporteur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testTransporteur.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    public void createTransporteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transporteurRepository.findAll().size();

        // Create the Transporteur with an existing ID
        transporteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransporteurMockMvc.perform(post("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteurRepository.findAll().size();
        // set the field null
        transporteur.setNom(null);

        // Create the Transporteur, which fails.

        restTransporteurMockMvc.perform(post("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteurRepository.findAll().size();
        // set the field null
        transporteur.setPrenom(null);

        // Create the Transporteur, which fails.

        restTransporteurMockMvc.perform(post("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteurRepository.findAll().size();
        // set the field null
        transporteur.setMatricule(null);

        // Create the Transporteur, which fails.

        restTransporteurMockMvc.perform(post("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransporteurs() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList
        restTransporteurMockMvc.perform(get("/api/transporteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)));
    }
    
    @Test
    @Transactional
    public void getTransporteur() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get the transporteur
        restTransporteurMockMvc.perform(get("/api/transporteurs/{id}", transporteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transporteur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE));
    }


    @Test
    @Transactional
    public void getTransporteursByIdFiltering() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        Long id = transporteur.getId();

        defaultTransporteurShouldBeFound("id.equals=" + id);
        defaultTransporteurShouldNotBeFound("id.notEquals=" + id);

        defaultTransporteurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransporteurShouldNotBeFound("id.greaterThan=" + id);

        defaultTransporteurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransporteurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransporteursByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom equals to DEFAULT_NOM
        defaultTransporteurShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the transporteurList where nom equals to UPDATED_NOM
        defaultTransporteurShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom not equals to DEFAULT_NOM
        defaultTransporteurShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the transporteurList where nom not equals to UPDATED_NOM
        defaultTransporteurShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByNomIsInShouldWork() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultTransporteurShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the transporteurList where nom equals to UPDATED_NOM
        defaultTransporteurShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom is not null
        defaultTransporteurShouldBeFound("nom.specified=true");

        // Get all the transporteurList where nom is null
        defaultTransporteurShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporteursByNomContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom contains DEFAULT_NOM
        defaultTransporteurShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the transporteurList where nom contains UPDATED_NOM
        defaultTransporteurShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByNomNotContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where nom does not contain DEFAULT_NOM
        defaultTransporteurShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the transporteurList where nom does not contain UPDATED_NOM
        defaultTransporteurShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllTransporteursByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom equals to DEFAULT_PRENOM
        defaultTransporteurShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the transporteurList where prenom equals to UPDATED_PRENOM
        defaultTransporteurShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom not equals to DEFAULT_PRENOM
        defaultTransporteurShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the transporteurList where prenom not equals to UPDATED_PRENOM
        defaultTransporteurShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultTransporteurShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the transporteurList where prenom equals to UPDATED_PRENOM
        defaultTransporteurShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom is not null
        defaultTransporteurShouldBeFound("prenom.specified=true");

        // Get all the transporteurList where prenom is null
        defaultTransporteurShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporteursByPrenomContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom contains DEFAULT_PRENOM
        defaultTransporteurShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the transporteurList where prenom contains UPDATED_PRENOM
        defaultTransporteurShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllTransporteursByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where prenom does not contain DEFAULT_PRENOM
        defaultTransporteurShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the transporteurList where prenom does not contain UPDATED_PRENOM
        defaultTransporteurShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllTransporteursByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone equals to DEFAULT_TELEPHONE
        defaultTransporteurShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the transporteurList where telephone equals to UPDATED_TELEPHONE
        defaultTransporteurShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone not equals to DEFAULT_TELEPHONE
        defaultTransporteurShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the transporteurList where telephone not equals to UPDATED_TELEPHONE
        defaultTransporteurShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultTransporteurShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the transporteurList where telephone equals to UPDATED_TELEPHONE
        defaultTransporteurShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone is not null
        defaultTransporteurShouldBeFound("telephone.specified=true");

        // Get all the transporteurList where telephone is null
        defaultTransporteurShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporteursByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone contains DEFAULT_TELEPHONE
        defaultTransporteurShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the transporteurList where telephone contains UPDATED_TELEPHONE
        defaultTransporteurShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where telephone does not contain DEFAULT_TELEPHONE
        defaultTransporteurShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the transporteurList where telephone does not contain UPDATED_TELEPHONE
        defaultTransporteurShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllTransporteursByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule equals to DEFAULT_MATRICULE
        defaultTransporteurShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the transporteurList where matricule equals to UPDATED_MATRICULE
        defaultTransporteurShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule not equals to DEFAULT_MATRICULE
        defaultTransporteurShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the transporteurList where matricule not equals to UPDATED_MATRICULE
        defaultTransporteurShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultTransporteurShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the transporteurList where matricule equals to UPDATED_MATRICULE
        defaultTransporteurShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule is not null
        defaultTransporteurShouldBeFound("matricule.specified=true");

        // Get all the transporteurList where matricule is null
        defaultTransporteurShouldNotBeFound("matricule.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransporteursByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule contains DEFAULT_MATRICULE
        defaultTransporteurShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the transporteurList where matricule contains UPDATED_MATRICULE
        defaultTransporteurShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllTransporteursByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);

        // Get all the transporteurList where matricule does not contain DEFAULT_MATRICULE
        defaultTransporteurShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the transporteurList where matricule does not contain UPDATED_MATRICULE
        defaultTransporteurShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllTransporteursByProprietaireIsEqualToSomething() throws Exception {
        // Initialize the database
        transporteurRepository.saveAndFlush(transporteur);
        Societe proprietaire = SocieteResourceIT.createEntity(em);
        em.persist(proprietaire);
        em.flush();
        transporteur.setProprietaire(proprietaire);
        transporteurRepository.saveAndFlush(transporteur);
        Long proprietaireId = proprietaire.getId();

        // Get all the transporteurList where proprietaire equals to proprietaireId
        defaultTransporteurShouldBeFound("proprietaireId.equals=" + proprietaireId);

        // Get all the transporteurList where proprietaire equals to proprietaireId + 1
        defaultTransporteurShouldNotBeFound("proprietaireId.equals=" + (proprietaireId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransporteurShouldBeFound(String filter) throws Exception {
        restTransporteurMockMvc.perform(get("/api/transporteurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)));

        // Check, that the count call also returns 1
        restTransporteurMockMvc.perform(get("/api/transporteurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransporteurShouldNotBeFound(String filter) throws Exception {
        restTransporteurMockMvc.perform(get("/api/transporteurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransporteurMockMvc.perform(get("/api/transporteurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransporteur() throws Exception {
        // Get the transporteur
        restTransporteurMockMvc.perform(get("/api/transporteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporteur() throws Exception {
        // Initialize the database
        transporteurService.save(transporteur);

        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();

        // Update the transporteur
        Transporteur updatedTransporteur = transporteurRepository.findById(transporteur.getId()).get();
        // Disconnect from session so that the updates on updatedTransporteur are not directly saved in db
        em.detach(updatedTransporteur);
        updatedTransporteur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .matricule(UPDATED_MATRICULE);

        restTransporteurMockMvc.perform(put("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransporteur)))
            .andExpect(status().isOk());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
        Transporteur testTransporteur = transporteurList.get(transporteurList.size() - 1);
        assertThat(testTransporteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testTransporteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testTransporteur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testTransporteur.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransporteur() throws Exception {
        int databaseSizeBeforeUpdate = transporteurRepository.findAll().size();

        // Create the Transporteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporteurMockMvc.perform(put("/api/transporteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transporteur)))
            .andExpect(status().isBadRequest());

        // Validate the Transporteur in the database
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransporteur() throws Exception {
        // Initialize the database
        transporteurService.save(transporteur);

        int databaseSizeBeforeDelete = transporteurRepository.findAll().size();

        // Delete the transporteur
        restTransporteurMockMvc.perform(delete("/api/transporteurs/{id}", transporteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transporteur> transporteurList = transporteurRepository.findAll();
        assertThat(transporteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
