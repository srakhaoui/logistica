package com.logistica.web.rest;

import com.logistica.LogisticaApp;
import com.logistica.domain.ClientGrossiste;
import com.logistica.domain.GasoilAchatGros;
import com.logistica.domain.GasoilVenteGros;
import com.logistica.domain.Societe;
import com.logistica.domain.enumeration.UniteGasoilGros;
import com.logistica.repository.GasoilVenteGrosRepository;
import com.logistica.service.GasoilVenteGrosService;
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
 * Integration tests for the {@link GasoilVenteGrosResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class GasoilVenteGrosResourceIT {

    private static final Float DEFAULT_PRIX_VENTE_UNITAIRE = 0F;
    private static final Float UPDATED_PRIX_VENTE_UNITAIRE = 1F;

    private static final Float DEFAULT_QUANTITE = 0F;
    private static final Float UPDATED_QUANTITE = 1F;

    private static final Float DEFAULT_PRIX_VENTE_TOTAL = 0F;
    private static final Float UPDATED_PRIX_VENTE_TOTAL = 1F;

    private static final Float DEFAULT_MARGE_GLOBALE = 1F;
    private static final Float UPDATED_MARGE_GLOBALE = 2F;

    private static final Float DEFAULT_TAUX_MARGE = 1F;
    private static final Float UPDATED_TAUX_MARGE = 2F;

    private static final UniteGasoilGros DEFAULT_UNITE_GASOIL_GROS = UniteGasoilGros.TONNE;
    private static final UniteGasoilGros UPDATED_UNITE_GASOIL_GROS = UniteGasoilGros.TONNE;

    @Autowired
    private GasoilVenteGrosRepository gasoilVenteGrosRepository;

    @Autowired
    private GasoilVenteGrosService gasoilVenteGrosService;

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

    private MockMvc restGasoilVenteGrosMockMvc;

    private GasoilVenteGros gasoilVenteGros;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GasoilVenteGrosResource gasoilVenteGrosResource = new GasoilVenteGrosResource(gasoilVenteGrosService);
        this.restGasoilVenteGrosMockMvc = MockMvcBuilders.standaloneSetup(gasoilVenteGrosResource)
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
    public static GasoilVenteGros createEntity(EntityManager em) {
        GasoilVenteGros gasoilVenteGros = new GasoilVenteGros()
            .prixVenteUnitaire(DEFAULT_PRIX_VENTE_UNITAIRE)
            .quantite(DEFAULT_QUANTITE)
            .prixVenteTotal(DEFAULT_PRIX_VENTE_TOTAL)
            .margeGlobale(DEFAULT_MARGE_GLOBALE)
            .tauxMarge(DEFAULT_TAUX_MARGE)
            .uniteGasoilGros(DEFAULT_UNITE_GASOIL_GROS);
        // Add required entity
        Societe societe;
        if (TestUtil.findAll(em, Societe.class).isEmpty()) {
            societe = SocieteResourceIT.createEntity(em);
            em.persist(societe);
            em.flush();
        } else {
            societe = TestUtil.findAll(em, Societe.class).get(0);
        }
        gasoilVenteGros.setSocieteFacturation(societe);
        // Add required entity
        ClientGrossiste clientGrossiste;
        if (TestUtil.findAll(em, ClientGrossiste.class).isEmpty()) {
            clientGrossiste = ClientGrossisteResourceIT.createEntity(em);
            em.persist(clientGrossiste);
            em.flush();
        } else {
            clientGrossiste = TestUtil.findAll(em, ClientGrossiste.class).get(0);
        }
        gasoilVenteGros.setClient(clientGrossiste);
        // Add required entity
        GasoilAchatGros gasoilAchatGros;
        if (TestUtil.findAll(em, GasoilAchatGros.class).isEmpty()) {
            gasoilAchatGros = GasoilAchatGrosResourceIT.createEntity(em);
            em.persist(gasoilAchatGros);
            em.flush();
        } else {
            gasoilAchatGros = TestUtil.findAll(em, GasoilAchatGros.class).get(0);
        }
        gasoilVenteGros.setAchatGasoil(gasoilAchatGros);
        return gasoilVenteGros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GasoilVenteGros createUpdatedEntity(EntityManager em) {
        GasoilVenteGros gasoilVenteGros = new GasoilVenteGros()
            .prixVenteUnitaire(UPDATED_PRIX_VENTE_UNITAIRE)
            .quantite(UPDATED_QUANTITE)
            .prixVenteTotal(UPDATED_PRIX_VENTE_TOTAL)
            .margeGlobale(UPDATED_MARGE_GLOBALE)
            .tauxMarge(UPDATED_TAUX_MARGE)
            .uniteGasoilGros(UPDATED_UNITE_GASOIL_GROS);
        // Add required entity
        Societe societe;
        if (TestUtil.findAll(em, Societe.class).isEmpty()) {
            societe = SocieteResourceIT.createUpdatedEntity(em);
            em.persist(societe);
            em.flush();
        } else {
            societe = TestUtil.findAll(em, Societe.class).get(0);
        }
        gasoilVenteGros.setSocieteFacturation(societe);
        // Add required entity
        ClientGrossiste clientGrossiste;
        if (TestUtil.findAll(em, ClientGrossiste.class).isEmpty()) {
            clientGrossiste = ClientGrossisteResourceIT.createUpdatedEntity(em);
            em.persist(clientGrossiste);
            em.flush();
        } else {
            clientGrossiste = TestUtil.findAll(em, ClientGrossiste.class).get(0);
        }
        gasoilVenteGros.setClient(clientGrossiste);
        // Add required entity
        GasoilAchatGros gasoilAchatGros;
        if (TestUtil.findAll(em, GasoilAchatGros.class).isEmpty()) {
            gasoilAchatGros = GasoilAchatGrosResourceIT.createUpdatedEntity(em);
            em.persist(gasoilAchatGros);
            em.flush();
        } else {
            gasoilAchatGros = TestUtil.findAll(em, GasoilAchatGros.class).get(0);
        }
        gasoilVenteGros.setAchatGasoil(gasoilAchatGros);
        return gasoilVenteGros;
    }

    @BeforeEach
    public void initTest() {
        gasoilVenteGros = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasoilVenteGros() throws Exception {
        int databaseSizeBeforeCreate = gasoilVenteGrosRepository.findAll().size();

        // Create the GasoilVenteGros
        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isCreated());

        // Validate the GasoilVenteGros in the database
        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeCreate + 1);
        GasoilVenteGros testGasoilVenteGros = gasoilVenteGrosList.get(gasoilVenteGrosList.size() - 1);
        assertThat(testGasoilVenteGros.getPrixVenteUnitaire()).isEqualTo(DEFAULT_PRIX_VENTE_UNITAIRE);
        assertThat(testGasoilVenteGros.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testGasoilVenteGros.getPrixVenteTotal()).isEqualTo(DEFAULT_PRIX_VENTE_TOTAL);
        assertThat(testGasoilVenteGros.getMargeGlobale()).isEqualTo(DEFAULT_MARGE_GLOBALE);
        assertThat(testGasoilVenteGros.getTauxMarge()).isEqualTo(DEFAULT_TAUX_MARGE);
        assertThat(testGasoilVenteGros.getUniteGasoilGros()).isEqualTo(DEFAULT_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void createGasoilVenteGrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gasoilVenteGrosRepository.findAll().size();

        // Create the GasoilVenteGros with an existing ID
        gasoilVenteGros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilVenteGros in the database
        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrixVenteUnitaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setPrixVenteUnitaire(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setQuantite(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrixVenteTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setPrixVenteTotal(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMargeGlobaleIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setMargeGlobale(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxMargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setTauxMarge(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUniteGasoilGrosIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasoilVenteGrosRepository.findAll().size();
        // set the field null
        gasoilVenteGros.setUniteGasoilGros(null);

        // Create the GasoilVenteGros, which fails.

        restGasoilVenteGrosMockMvc.perform(post("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGasoilVenteGros() throws Exception {
        // Initialize the database
        gasoilVenteGrosRepository.saveAndFlush(gasoilVenteGros);

        // Get all the gasoilVenteGrosList
        restGasoilVenteGrosMockMvc.perform(get("/api/gasoil-vente-gros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasoilVenteGros.getId().intValue())))
            .andExpect(jsonPath("$.[*].prixVenteUnitaire").value(hasItem(DEFAULT_PRIX_VENTE_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixVenteTotal").value(hasItem(DEFAULT_PRIX_VENTE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].margeGlobale").value(hasItem(DEFAULT_MARGE_GLOBALE.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxMarge").value(hasItem(DEFAULT_TAUX_MARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniteGasoilGros").value(hasItem(DEFAULT_UNITE_GASOIL_GROS.toString())));
    }

    @Test
    @Transactional
    public void getGasoilVenteGros() throws Exception {
        // Initialize the database
        gasoilVenteGrosRepository.saveAndFlush(gasoilVenteGros);

        // Get the gasoilVenteGros
        restGasoilVenteGrosMockMvc.perform(get("/api/gasoil-vente-gros/{id}", gasoilVenteGros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasoilVenteGros.getId().intValue()))
            .andExpect(jsonPath("$.prixVenteUnitaire").value(DEFAULT_PRIX_VENTE_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixVenteTotal").value(DEFAULT_PRIX_VENTE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.margeGlobale").value(DEFAULT_MARGE_GLOBALE.doubleValue()))
            .andExpect(jsonPath("$.tauxMarge").value(DEFAULT_TAUX_MARGE.doubleValue()))
            .andExpect(jsonPath("$.uniteGasoilGros").value(DEFAULT_UNITE_GASOIL_GROS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGasoilVenteGros() throws Exception {
        // Get the gasoilVenteGros
        restGasoilVenteGrosMockMvc.perform(get("/api/gasoil-vente-gros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasoilVenteGros() throws Exception {
        // Initialize the database
        gasoilVenteGrosService.save(gasoilVenteGros);

        int databaseSizeBeforeUpdate = gasoilVenteGrosRepository.findAll().size();

        // Update the gasoilVenteGros
        GasoilVenteGros updatedGasoilVenteGros = gasoilVenteGrosRepository.findById(gasoilVenteGros.getId()).get();
        // Disconnect from session so that the updates on updatedGasoilVenteGros are not directly saved in db
        em.detach(updatedGasoilVenteGros);
        updatedGasoilVenteGros
            .prixVenteUnitaire(UPDATED_PRIX_VENTE_UNITAIRE)
            .quantite(UPDATED_QUANTITE)
            .prixVenteTotal(UPDATED_PRIX_VENTE_TOTAL)
            .margeGlobale(UPDATED_MARGE_GLOBALE)
            .tauxMarge(UPDATED_TAUX_MARGE)
            .uniteGasoilGros(UPDATED_UNITE_GASOIL_GROS);

        restGasoilVenteGrosMockMvc.perform(put("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGasoilVenteGros)))
            .andExpect(status().isOk());

        // Validate the GasoilVenteGros in the database
        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeUpdate);
        GasoilVenteGros testGasoilVenteGros = gasoilVenteGrosList.get(gasoilVenteGrosList.size() - 1);
        assertThat(testGasoilVenteGros.getPrixVenteUnitaire()).isEqualTo(UPDATED_PRIX_VENTE_UNITAIRE);
        assertThat(testGasoilVenteGros.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testGasoilVenteGros.getPrixVenteTotal()).isEqualTo(UPDATED_PRIX_VENTE_TOTAL);
        assertThat(testGasoilVenteGros.getMargeGlobale()).isEqualTo(UPDATED_MARGE_GLOBALE);
        assertThat(testGasoilVenteGros.getTauxMarge()).isEqualTo(UPDATED_TAUX_MARGE);
        assertThat(testGasoilVenteGros.getUniteGasoilGros()).isEqualTo(UPDATED_UNITE_GASOIL_GROS);
    }

    @Test
    @Transactional
    public void updateNonExistingGasoilVenteGros() throws Exception {
        int databaseSizeBeforeUpdate = gasoilVenteGrosRepository.findAll().size();

        // Create the GasoilVenteGros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasoilVenteGrosMockMvc.perform(put("/api/gasoil-vente-gros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasoilVenteGros)))
            .andExpect(status().isBadRequest());

        // Validate the GasoilVenteGros in the database
        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasoilVenteGros() throws Exception {
        // Initialize the database
        gasoilVenteGrosService.save(gasoilVenteGros);

        int databaseSizeBeforeDelete = gasoilVenteGrosRepository.findAll().size();

        // Delete the gasoilVenteGros
        restGasoilVenteGrosMockMvc.perform(delete("/api/gasoil-vente-gros/{id}", gasoilVenteGros.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GasoilVenteGros> gasoilVenteGrosList = gasoilVenteGrosRepository.findAll();
        assertThat(gasoilVenteGrosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
