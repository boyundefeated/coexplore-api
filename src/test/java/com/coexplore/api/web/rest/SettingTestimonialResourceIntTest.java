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
import com.coexplore.api.domain.SettingTestimonial;
import com.coexplore.api.repository.SettingTestimonialRepository;
import com.coexplore.api.service.SettingTestimonialService;
import com.coexplore.api.service.dto.SettingTestimonialDTO;
import com.coexplore.api.service.mapper.SettingTestimonialMapper;

/**
 * Test class for the SettingTestimonialResource REST controller.
 *
 * @see SettingTestimonialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoexploreapiApp.class)
public class SettingTestimonialResourceIntTest {

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private SettingTestimonialRepository settingTestimonialRepository;

    @Autowired
    private SettingTestimonialMapper settingTestimonialMapper;

    @Autowired
    private SettingTestimonialService settingTestimonialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private DefaultExceptionAdvice defaultExceptionAdvice;

    @Autowired
    private EntityManager em;

    private MockMvc restSettingTestimonialMockMvc;

    private SettingTestimonial settingTestimonial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettingTestimonialResource settingTestimonialResource = new SettingTestimonialResource(settingTestimonialService);
        this.restSettingTestimonialMockMvc = MockMvcBuilders.standaloneSetup(settingTestimonialResource)
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
    public static SettingTestimonial createEntity(EntityManager em) {
        SettingTestimonial settingTestimonial = new SettingTestimonial()
            .image(DEFAULT_IMAGE)
            .comment(DEFAULT_COMMENT)
            .order(DEFAULT_ORDER);
        return settingTestimonial;
    }

    @Before
    public void initTest() {
        settingTestimonial = createEntity(em);
    }

    @Test
    @Transactional
    public void createSettingTestimonial() throws Exception {
        int databaseSizeBeforeCreate = settingTestimonialRepository.findAll().size();

        // Create the SettingTestimonial
        SettingTestimonialDTO settingTestimonialDTO = settingTestimonialMapper.toDto(settingTestimonial);
        restSettingTestimonialMockMvc.perform(post("/api/setting-testimonials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingTestimonialDTO)))
            .andExpect(status().isCreated());

        // Validate the SettingTestimonial in the database
        List<SettingTestimonial> settingTestimonialList = settingTestimonialRepository.findAll();
        assertThat(settingTestimonialList).hasSize(databaseSizeBeforeCreate + 1);
        SettingTestimonial testSettingTestimonial = settingTestimonialList.get(settingTestimonialList.size() - 1);
        assertThat(testSettingTestimonial.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSettingTestimonial.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSettingTestimonial.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createSettingTestimonialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingTestimonialRepository.findAll().size();

        // Create the SettingTestimonial with an existing ID
        settingTestimonial.setId(1L);
        SettingTestimonialDTO settingTestimonialDTO = settingTestimonialMapper.toDto(settingTestimonial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingTestimonialMockMvc.perform(post("/api/setting-testimonials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingTestimonialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingTestimonial in the database
        List<SettingTestimonial> settingTestimonialList = settingTestimonialRepository.findAll();
        assertThat(settingTestimonialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSettingTestimonials() throws Exception {
        // Initialize the database
        settingTestimonialRepository.saveAndFlush(settingTestimonial);

        // Get all the settingTestimonialList
        restSettingTestimonialMockMvc.perform(get("/api/setting-testimonials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingTestimonial.getId().intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getSettingTestimonial() throws Exception {
        // Initialize the database
        settingTestimonialRepository.saveAndFlush(settingTestimonial);

        // Get the settingTestimonial
        restSettingTestimonialMockMvc.perform(get("/api/setting-testimonials/{id}", settingTestimonial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(settingTestimonial.getId().intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingSettingTestimonial() throws Exception {
        // Get the settingTestimonial
        restSettingTestimonialMockMvc.perform(get("/api/setting-testimonials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSettingTestimonial() throws Exception {
        // Initialize the database
        settingTestimonialRepository.saveAndFlush(settingTestimonial);

        int databaseSizeBeforeUpdate = settingTestimonialRepository.findAll().size();

        // Update the settingTestimonial
        SettingTestimonial updatedSettingTestimonial = settingTestimonialRepository.findById(settingTestimonial.getId()).get();
        // Disconnect from session so that the updates on updatedSettingTestimonial are not directly saved in db
        em.detach(updatedSettingTestimonial);
        updatedSettingTestimonial
            .image(UPDATED_IMAGE)
            .comment(UPDATED_COMMENT)
            .order(UPDATED_ORDER);
        SettingTestimonialDTO settingTestimonialDTO = settingTestimonialMapper.toDto(updatedSettingTestimonial);

        restSettingTestimonialMockMvc.perform(put("/api/setting-testimonials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingTestimonialDTO)))
            .andExpect(status().isOk());

        // Validate the SettingTestimonial in the database
        List<SettingTestimonial> settingTestimonialList = settingTestimonialRepository.findAll();
        assertThat(settingTestimonialList).hasSize(databaseSizeBeforeUpdate);
        SettingTestimonial testSettingTestimonial = settingTestimonialList.get(settingTestimonialList.size() - 1);
        assertThat(testSettingTestimonial.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSettingTestimonial.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSettingTestimonial.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingSettingTestimonial() throws Exception {
        int databaseSizeBeforeUpdate = settingTestimonialRepository.findAll().size();

        // Create the SettingTestimonial
        SettingTestimonialDTO settingTestimonialDTO = settingTestimonialMapper.toDto(settingTestimonial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingTestimonialMockMvc.perform(put("/api/setting-testimonials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingTestimonialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingTestimonial in the database
        List<SettingTestimonial> settingTestimonialList = settingTestimonialRepository.findAll();
        assertThat(settingTestimonialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSettingTestimonial() throws Exception {
        // Initialize the database
        settingTestimonialRepository.saveAndFlush(settingTestimonial);

        int databaseSizeBeforeDelete = settingTestimonialRepository.findAll().size();

        // Get the settingTestimonial
        restSettingTestimonialMockMvc.perform(delete("/api/setting-testimonials/{id}", settingTestimonial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SettingTestimonial> settingTestimonialList = settingTestimonialRepository.findAll();
        assertThat(settingTestimonialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingTestimonial.class);
        SettingTestimonial settingTestimonial1 = new SettingTestimonial();
        settingTestimonial1.setId(1L);
        SettingTestimonial settingTestimonial2 = new SettingTestimonial();
        settingTestimonial2.setId(settingTestimonial1.getId());
        assertThat(settingTestimonial1).isEqualTo(settingTestimonial2);
        settingTestimonial2.setId(2L);
        assertThat(settingTestimonial1).isNotEqualTo(settingTestimonial2);
        settingTestimonial1.setId(null);
        assertThat(settingTestimonial1).isNotEqualTo(settingTestimonial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingTestimonialDTO.class);
        SettingTestimonialDTO settingTestimonialDTO1 = new SettingTestimonialDTO();
        settingTestimonialDTO1.setId(1L);
        SettingTestimonialDTO settingTestimonialDTO2 = new SettingTestimonialDTO();
        assertThat(settingTestimonialDTO1).isNotEqualTo(settingTestimonialDTO2);
        settingTestimonialDTO2.setId(settingTestimonialDTO1.getId());
        assertThat(settingTestimonialDTO1).isEqualTo(settingTestimonialDTO2);
        settingTestimonialDTO2.setId(2L);
        assertThat(settingTestimonialDTO1).isNotEqualTo(settingTestimonialDTO2);
        settingTestimonialDTO1.setId(null);
        assertThat(settingTestimonialDTO1).isNotEqualTo(settingTestimonialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settingTestimonialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settingTestimonialMapper.fromId(null)).isNull();
    }
}
