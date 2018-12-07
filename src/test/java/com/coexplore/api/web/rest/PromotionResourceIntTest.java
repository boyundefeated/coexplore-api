package com.coexplore.api.web.rest;

import static com.coexplore.api.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.coexplore.api.CoexploreapiApp;
import com.coexplore.api.common.exception.DefaultExceptionAdvice;
import com.coexplore.api.domain.Promotion;
import com.coexplore.api.domain.enumeration.PromotionType;
import com.coexplore.api.repository.PromotionRepository;
import com.coexplore.api.service.PromotionService;
import com.coexplore.api.service.dto.PromotionDTO;
import com.coexplore.api.service.mapper.PromotionMapper;
/**
 * Test class for the PromotionResource REST controller.
 *
 * @see PromotionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoexploreapiApp.class)
public class PromotionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PromotionType DEFAULT_TYPE = PromotionType.PERCENT;
    private static final PromotionType UPDATED_TYPE = PromotionType.MONEY;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private DefaultExceptionAdvice defaultExceptionAdvice;

    @Autowired
    private EntityManager em;

    private MockMvc restPromotionMockMvc;

    private Promotion promotion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PromotionResource promotionResource = new PromotionResource(promotionService);
        this.restPromotionMockMvc = MockMvcBuilders.standaloneSetup(promotionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(defaultExceptionAdvice)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Promotion createEntity(EntityManager em) {
        Promotion promotion = new Promotion()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        return promotion;
    }

    @Before
    public void initTest() {
        promotion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPromotion() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);
        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isCreated());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate + 1);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPromotion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPromotion.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPromotion.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testPromotion.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPromotion.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPromotionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = promotionRepository.findAll().size();

        // Create the Promotion with an existing ID
        promotion.setId(1L);
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromotionMockMvc.perform(post("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPromotions() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get all the promotionList
        restPromotionMockMvc.perform(get("/api/promotions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(promotion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", promotion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(promotion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPromotion() throws Exception {
        // Get the promotion
        restPromotionMockMvc.perform(get("/api/promotions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Update the promotion
        Promotion updatedPromotion = promotionRepository.findById(promotion.getId()).get();
        // Disconnect from session so that the updates on updatedPromotion are not directly saved in db
        em.detach(updatedPromotion);
        updatedPromotion
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);
        PromotionDTO promotionDTO = promotionMapper.toDto(updatedPromotion);

        restPromotionMockMvc.perform(put("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isOk());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
        Promotion testPromotion = promotionList.get(promotionList.size() - 1);
        assertThat(testPromotion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPromotion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPromotion.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPromotion.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testPromotion.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPromotion.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPromotion() throws Exception {
        int databaseSizeBeforeUpdate = promotionRepository.findAll().size();

        // Create the Promotion
        PromotionDTO promotionDTO = promotionMapper.toDto(promotion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromotionMockMvc.perform(put("/api/promotions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(promotionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Promotion in the database
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePromotion() throws Exception {
        // Initialize the database
        promotionRepository.saveAndFlush(promotion);

        int databaseSizeBeforeDelete = promotionRepository.findAll().size();

        // Get the promotion
        restPromotionMockMvc.perform(delete("/api/promotions/{id}", promotion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Promotion> promotionList = promotionRepository.findAll();
        assertThat(promotionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Promotion.class);
        Promotion promotion1 = new Promotion();
        promotion1.setId(1L);
        Promotion promotion2 = new Promotion();
        promotion2.setId(promotion1.getId());
        assertThat(promotion1).isEqualTo(promotion2);
        promotion2.setId(2L);
        assertThat(promotion1).isNotEqualTo(promotion2);
        promotion1.setId(null);
        assertThat(promotion1).isNotEqualTo(promotion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PromotionDTO.class);
        PromotionDTO promotionDTO1 = new PromotionDTO();
        promotionDTO1.setId(1L);
        PromotionDTO promotionDTO2 = new PromotionDTO();
        assertThat(promotionDTO1).isNotEqualTo(promotionDTO2);
        promotionDTO2.setId(promotionDTO1.getId());
        assertThat(promotionDTO1).isEqualTo(promotionDTO2);
        promotionDTO2.setId(2L);
        assertThat(promotionDTO1).isNotEqualTo(promotionDTO2);
        promotionDTO1.setId(null);
        assertThat(promotionDTO1).isNotEqualTo(promotionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(promotionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(promotionMapper.fromId(null)).isNull();
    }
}
