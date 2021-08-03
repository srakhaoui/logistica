package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.ClientGrossiste;
import com.logistica.repository.ClientGrossisteRepository;
import com.logistica.service.ClientGrossisteQueryService;
import com.logistica.service.ClientGrossisteService;
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
 * Integration tests for the {@link ClientGrossisteResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class ClientGrossisteResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private ClientGrossisteRepository clientGrossisteRepository;

    @Autowired
    private ClientGrossisteService clientGrossisteService;

    @Autowired
    private ClientGrossisteQueryService clientGrossisteQueryService;

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

    private MockMvc restClientGrossisteMockMvc;

    private ClientGrossiste clientGrossiste;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientGrossisteResource clientGrossisteResource = new ClientGrossisteResource(clientGrossisteService, clientGrossisteQueryService);
        this.restClientGrossisteMockMvc = MockMvcBuilders.standaloneSetup(clientGrossisteResource)
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
    public static ClientGrossiste createEntity(EntityManager em) {
        ClientGrossiste clientGrossiste = new ClientGrossiste()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE);
        return clientGrossiste;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientGrossiste createUpdatedEntity(EntityManager em) {
        ClientGrossiste clientGrossiste = new ClientGrossiste()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);
        return clientGrossiste;
    }

    @BeforeEach
    public void initTest() {
        clientGrossiste = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientGrossiste() throws Exception {
        int databaseSizeBeforeCreate = clientGrossisteRepository.findAll().size();

        // Create the ClientGrossiste
        restClientGrossisteMockMvc.perform(post("/api/client-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientGrossiste)))
            .andExpect(status().isCreated());

        // Validate the ClientGrossiste in the database
        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeCreate + 1);
        ClientGrossiste testClientGrossiste = clientGrossisteList.get(clientGrossisteList.size() - 1);
        assertThat(testClientGrossiste.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClientGrossiste.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testClientGrossiste.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createClientGrossisteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientGrossisteRepository.findAll().size();

        // Create the ClientGrossiste with an existing ID
        clientGrossiste.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientGrossisteMockMvc.perform(post("/api/client-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientGrossiste)))
            .andExpect(status().isBadRequest());

        // Validate the ClientGrossiste in the database
        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientGrossisteRepository.findAll().size();
        // set the field null
        clientGrossiste.setNom(null);

        // Create the ClientGrossiste, which fails.

        restClientGrossisteMockMvc.perform(post("/api/client-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientGrossiste)))
            .andExpect(status().isBadRequest());

        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientGrossistes() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientGrossiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }

    @Test
    @Transactional
    public void getClientGrossiste() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get the clientGrossiste
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes/{id}", clientGrossiste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientGrossiste.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }


    @Test
    @Transactional
    public void getClientGrossistesByIdFiltering() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        Long id = clientGrossiste.getId();

        defaultClientGrossisteShouldBeFound("id.equals=" + id);
        defaultClientGrossisteShouldNotBeFound("id.notEquals=" + id);

        defaultClientGrossisteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientGrossisteShouldNotBeFound("id.greaterThan=" + id);

        defaultClientGrossisteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientGrossisteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientGrossistesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom equals to DEFAULT_NOM
        defaultClientGrossisteShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the clientGrossisteList where nom equals to UPDATED_NOM
        defaultClientGrossisteShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom not equals to DEFAULT_NOM
        defaultClientGrossisteShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the clientGrossisteList where nom not equals to UPDATED_NOM
        defaultClientGrossisteShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultClientGrossisteShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the clientGrossisteList where nom equals to UPDATED_NOM
        defaultClientGrossisteShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom is not null
        defaultClientGrossisteShouldBeFound("nom.specified=true");

        // Get all the clientGrossisteList where nom is null
        defaultClientGrossisteShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByNomContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom contains DEFAULT_NOM
        defaultClientGrossisteShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the clientGrossisteList where nom contains UPDATED_NOM
        defaultClientGrossisteShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where nom does not contain DEFAULT_NOM
        defaultClientGrossisteShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the clientGrossisteList where nom does not contain UPDATED_NOM
        defaultClientGrossisteShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse equals to DEFAULT_ADRESSE
        defaultClientGrossisteShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the clientGrossisteList where adresse equals to UPDATED_ADRESSE
        defaultClientGrossisteShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse not equals to DEFAULT_ADRESSE
        defaultClientGrossisteShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the clientGrossisteList where adresse not equals to UPDATED_ADRESSE
        defaultClientGrossisteShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultClientGrossisteShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the clientGrossisteList where adresse equals to UPDATED_ADRESSE
        defaultClientGrossisteShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse is not null
        defaultClientGrossisteShouldBeFound("adresse.specified=true");

        // Get all the clientGrossisteList where adresse is null
        defaultClientGrossisteShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse contains DEFAULT_ADRESSE
        defaultClientGrossisteShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the clientGrossisteList where adresse contains UPDATED_ADRESSE
        defaultClientGrossisteShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where adresse does not contain DEFAULT_ADRESSE
        defaultClientGrossisteShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the clientGrossisteList where adresse does not contain UPDATED_ADRESSE
        defaultClientGrossisteShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone equals to DEFAULT_TELEPHONE
        defaultClientGrossisteShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the clientGrossisteList where telephone equals to UPDATED_TELEPHONE
        defaultClientGrossisteShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone not equals to DEFAULT_TELEPHONE
        defaultClientGrossisteShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the clientGrossisteList where telephone not equals to UPDATED_TELEPHONE
        defaultClientGrossisteShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultClientGrossisteShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the clientGrossisteList where telephone equals to UPDATED_TELEPHONE
        defaultClientGrossisteShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone is not null
        defaultClientGrossisteShouldBeFound("telephone.specified=true");

        // Get all the clientGrossisteList where telephone is null
        defaultClientGrossisteShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone contains DEFAULT_TELEPHONE
        defaultClientGrossisteShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the clientGrossisteList where telephone contains UPDATED_TELEPHONE
        defaultClientGrossisteShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientGrossistesByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        clientGrossisteRepository.saveAndFlush(clientGrossiste);

        // Get all the clientGrossisteList where telephone does not contain DEFAULT_TELEPHONE
        defaultClientGrossisteShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the clientGrossisteList where telephone does not contain UPDATED_TELEPHONE
        defaultClientGrossisteShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientGrossisteShouldBeFound(String filter) throws Exception {
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientGrossiste.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));

        // Check, that the count call also returns 1
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientGrossisteShouldNotBeFound(String filter) throws Exception {
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingClientGrossiste() throws Exception {
        // Get the clientGrossiste
        restClientGrossisteMockMvc.perform(get("/api/client-grossistes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientGrossiste() throws Exception {
        // Initialize the database
        clientGrossisteService.save(clientGrossiste);

        int databaseSizeBeforeUpdate = clientGrossisteRepository.findAll().size();

        // Update the clientGrossiste
        ClientGrossiste updatedClientGrossiste = clientGrossisteRepository.findById(clientGrossiste.getId()).get();
        // Disconnect from session so that the updates on updatedClientGrossiste are not directly saved in db
        em.detach(updatedClientGrossiste);
        updatedClientGrossiste
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE);

        restClientGrossisteMockMvc.perform(put("/api/client-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientGrossiste)))
            .andExpect(status().isOk());

        // Validate the ClientGrossiste in the database
        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeUpdate);
        ClientGrossiste testClientGrossiste = clientGrossisteList.get(clientGrossisteList.size() - 1);
        assertThat(testClientGrossiste.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClientGrossiste.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testClientGrossiste.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingClientGrossiste() throws Exception {
        int databaseSizeBeforeUpdate = clientGrossisteRepository.findAll().size();

        // Create the ClientGrossiste

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientGrossisteMockMvc.perform(put("/api/client-grossistes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientGrossiste)))
            .andExpect(status().isBadRequest());

        // Validate the ClientGrossiste in the database
        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientGrossiste() throws Exception {
        // Initialize the database
        clientGrossisteService.save(clientGrossiste);

        int databaseSizeBeforeDelete = clientGrossisteRepository.findAll().size();

        // Delete the clientGrossiste
        restClientGrossisteMockMvc.perform(delete("/api/client-grossistes/{id}", clientGrossiste.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientGrossiste> clientGrossisteList = clientGrossisteRepository.findAll();
        assertThat(clientGrossisteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
