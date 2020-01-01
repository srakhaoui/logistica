package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.TarifAchat;
import com.logistica.domain.Fournisseur;
import com.logistica.domain.Produit;
import com.logistica.repository.TarifAchatRepository;
import com.logistica.service.TarifAchatService;
import com.logistica.web.rest.errors.ExceptionTranslator;
import com.logistica.service.dto.TarifAchatCriteria;
import com.logistica.service.TarifAchatQueryService;

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

import com.logistica.domain.enumeration.Unite;
/**
 * Integration tests for the {@link TarifAchatResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TarifAchatResourceIT {

    private static final Unite DEFAULT_UNITE = Unite.Tonne;
    private static final Unite UPDATED_UNITE = Unite.M3;

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;
    private static final Float SMALLER_PRIX = 1F - 1F;

    @Autowired
    private TarifAchatRepository tarifAchatRepository;

    @Autowired
    private TarifAchatService tarifAchatService;

    @Autowired
    private TarifAchatQueryService tarifAchatQueryService;

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

    private MockMvc restTarifAchatMockMvc;

    private TarifAchat tarifAchat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifAchatResource tarifAchatResource = new TarifAchatResource(tarifAchatService, tarifAchatQueryService);
        this.restTarifAchatMockMvc = MockMvcBuilders.standaloneSetup(tarifAchatResource)
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
    public static TarifAchat createEntity(EntityManager em) {
        TarifAchat tarifAchat = new TarifAchat()
            .unite(DEFAULT_UNITE)
            .prix(DEFAULT_PRIX);
        return tarifAchat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifAchat createUpdatedEntity(EntityManager em) {
        TarifAchat tarifAchat = new TarifAchat()
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);
        return tarifAchat;
    }

    @BeforeEach
    public void initTest() {
        tarifAchat = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifAchat() throws Exception {
        int databaseSizeBeforeCreate = tarifAchatRepository.findAll().size();

        // Create the TarifAchat
        restTarifAchatMockMvc.perform(post("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifAchat)))
            .andExpect(status().isCreated());

        // Validate the TarifAchat in the database
        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeCreate + 1);
        TarifAchat testTarifAchat = tarifAchatList.get(tarifAchatList.size() - 1);
        assertThat(testTarifAchat.getUnite()).isEqualTo(DEFAULT_UNITE);
        assertThat(testTarifAchat.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createTarifAchatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifAchatRepository.findAll().size();

        // Create the TarifAchat with an existing ID
        tarifAchat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifAchatMockMvc.perform(post("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifAchat)))
            .andExpect(status().isBadRequest());

        // Validate the TarifAchat in the database
        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUniteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifAchatRepository.findAll().size();
        // set the field null
        tarifAchat.setUnite(null);

        // Create the TarifAchat, which fails.

        restTarifAchatMockMvc.perform(post("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifAchat)))
            .andExpect(status().isBadRequest());

        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifAchatRepository.findAll().size();
        // set the field null
        tarifAchat.setPrix(null);

        // Create the TarifAchat, which fails.

        restTarifAchatMockMvc.perform(post("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifAchat)))
            .andExpect(status().isBadRequest());

        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifAchats() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList
        restTarifAchatMockMvc.perform(get("/api/tarif-achats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTarifAchat() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get the tarifAchat
        restTarifAchatMockMvc.perform(get("/api/tarif-achats/{id}", tarifAchat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarifAchat.getId().intValue()))
            .andExpect(jsonPath("$.unite").value(DEFAULT_UNITE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()));
    }


    @Test
    @Transactional
    public void getTarifAchatsByIdFiltering() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        Long id = tarifAchat.getId();

        defaultTarifAchatShouldBeFound("id.equals=" + id);
        defaultTarifAchatShouldNotBeFound("id.notEquals=" + id);

        defaultTarifAchatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifAchatShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifAchatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifAchatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTarifAchatsByUniteIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where unite equals to DEFAULT_UNITE
        defaultTarifAchatShouldBeFound("unite.equals=" + DEFAULT_UNITE);

        // Get all the tarifAchatList where unite equals to UPDATED_UNITE
        defaultTarifAchatShouldNotBeFound("unite.equals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByUniteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where unite not equals to DEFAULT_UNITE
        defaultTarifAchatShouldNotBeFound("unite.notEquals=" + DEFAULT_UNITE);

        // Get all the tarifAchatList where unite not equals to UPDATED_UNITE
        defaultTarifAchatShouldBeFound("unite.notEquals=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByUniteIsInShouldWork() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where unite in DEFAULT_UNITE or UPDATED_UNITE
        defaultTarifAchatShouldBeFound("unite.in=" + DEFAULT_UNITE + "," + UPDATED_UNITE);

        // Get all the tarifAchatList where unite equals to UPDATED_UNITE
        defaultTarifAchatShouldNotBeFound("unite.in=" + UPDATED_UNITE);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByUniteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where unite is not null
        defaultTarifAchatShouldBeFound("unite.specified=true");

        // Get all the tarifAchatList where unite is null
        defaultTarifAchatShouldNotBeFound("unite.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix equals to DEFAULT_PRIX
        defaultTarifAchatShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix equals to UPDATED_PRIX
        defaultTarifAchatShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix not equals to DEFAULT_PRIX
        defaultTarifAchatShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix not equals to UPDATED_PRIX
        defaultTarifAchatShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultTarifAchatShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the tarifAchatList where prix equals to UPDATED_PRIX
        defaultTarifAchatShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix is not null
        defaultTarifAchatShouldBeFound("prix.specified=true");

        // Get all the tarifAchatList where prix is null
        defaultTarifAchatShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix is greater than or equal to DEFAULT_PRIX
        defaultTarifAchatShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix is greater than or equal to UPDATED_PRIX
        defaultTarifAchatShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix is less than or equal to DEFAULT_PRIX
        defaultTarifAchatShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix is less than or equal to SMALLER_PRIX
        defaultTarifAchatShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix is less than DEFAULT_PRIX
        defaultTarifAchatShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix is less than UPDATED_PRIX
        defaultTarifAchatShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllTarifAchatsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);

        // Get all the tarifAchatList where prix is greater than DEFAULT_PRIX
        defaultTarifAchatShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the tarifAchatList where prix is greater than SMALLER_PRIX
        defaultTarifAchatShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }


    @Test
    @Transactional
    public void getAllTarifAchatsByFournisseurIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);
        Fournisseur fournisseur = FournisseurResourceIT.createEntity(em);
        em.persist(fournisseur);
        em.flush();
        tarifAchat.setFournisseur(fournisseur);
        tarifAchatRepository.saveAndFlush(tarifAchat);
        Long fournisseurId = fournisseur.getId();

        // Get all the tarifAchatList where fournisseur equals to fournisseurId
        defaultTarifAchatShouldBeFound("fournisseurId.equals=" + fournisseurId);

        // Get all the tarifAchatList where fournisseur equals to fournisseurId + 1
        defaultTarifAchatShouldNotBeFound("fournisseurId.equals=" + (fournisseurId + 1));
    }


    @Test
    @Transactional
    public void getAllTarifAchatsByProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifAchatRepository.saveAndFlush(tarifAchat);
        Produit produit = ProduitResourceIT.createEntity(em);
        em.persist(produit);
        em.flush();
        tarifAchat.setProduit(produit);
        tarifAchatRepository.saveAndFlush(tarifAchat);
        Long produitId = produit.getId();

        // Get all the tarifAchatList where produit equals to produitId
        defaultTarifAchatShouldBeFound("produitId.equals=" + produitId);

        // Get all the tarifAchatList where produit equals to produitId + 1
        defaultTarifAchatShouldNotBeFound("produitId.equals=" + (produitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifAchatShouldBeFound(String filter) throws Exception {
        restTarifAchatMockMvc.perform(get("/api/tarif-achats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifAchat.getId().intValue())))
            .andExpect(jsonPath("$.[*].unite").value(hasItem(DEFAULT_UNITE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())));

        // Check, that the count call also returns 1
        restTarifAchatMockMvc.perform(get("/api/tarif-achats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifAchatShouldNotBeFound(String filter) throws Exception {
        restTarifAchatMockMvc.perform(get("/api/tarif-achats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifAchatMockMvc.perform(get("/api/tarif-achats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTarifAchat() throws Exception {
        // Get the tarifAchat
        restTarifAchatMockMvc.perform(get("/api/tarif-achats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifAchat() throws Exception {
        // Initialize the database
        tarifAchatService.save(tarifAchat);

        int databaseSizeBeforeUpdate = tarifAchatRepository.findAll().size();

        // Update the tarifAchat
        TarifAchat updatedTarifAchat = tarifAchatRepository.findById(tarifAchat.getId()).get();
        // Disconnect from session so that the updates on updatedTarifAchat are not directly saved in db
        em.detach(updatedTarifAchat);
        updatedTarifAchat
            .unite(UPDATED_UNITE)
            .prix(UPDATED_PRIX);

        restTarifAchatMockMvc.perform(put("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifAchat)))
            .andExpect(status().isOk());

        // Validate the TarifAchat in the database
        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeUpdate);
        TarifAchat testTarifAchat = tarifAchatList.get(tarifAchatList.size() - 1);
        assertThat(testTarifAchat.getUnite()).isEqualTo(UPDATED_UNITE);
        assertThat(testTarifAchat.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifAchat() throws Exception {
        int databaseSizeBeforeUpdate = tarifAchatRepository.findAll().size();

        // Create the TarifAchat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifAchatMockMvc.perform(put("/api/tarif-achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifAchat)))
            .andExpect(status().isBadRequest());

        // Validate the TarifAchat in the database
        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarifAchat() throws Exception {
        // Initialize the database
        tarifAchatService.save(tarifAchat);

        int databaseSizeBeforeDelete = tarifAchatRepository.findAll().size();

        // Delete the tarifAchat
        restTarifAchatMockMvc.perform(delete("/api/tarif-achats/{id}", tarifAchat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TarifAchat> tarifAchatList = tarifAchatRepository.findAll();
        assertThat(tarifAchatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
