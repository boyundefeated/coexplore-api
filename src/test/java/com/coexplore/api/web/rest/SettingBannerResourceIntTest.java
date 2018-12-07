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
import com.coexplore.api.domain.SettingBanner;
import com.coexplore.api.repository.SettingBannerRepository;
import com.coexplore.api.service.SettingBannerService;
import com.coexplore.api.service.dto.SettingBannerDTO;
import com.coexplore.api.service.mapper.SettingBannerMapper;

/**
 * Test class for the SettingBannerResource REST controller.
 *
 * @see SettingBannerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoexploreapiApp.class)
public class SettingBannerResourceIntTest {

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private SettingBannerRepository settingBannerRepository;

    @Autowired
    private SettingBannerMapper settingBannerMapper;

    @Autowired
    private SettingBannerService settingBannerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private DefaultExceptionAdvice defaultExceptionAdvice;

    @Autowired
    private EntityManager em;

    private MockMvc restSettingBannerMockMvc;

    private SettingBanner settingBanner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettingBannerResource settingBannerResource = new SettingBannerResource(settingBannerService);
        this.restSettingBannerMockMvc = MockMvcBuilders.standaloneSetup(settingBannerResource)
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
    public static SettingBanner createEntity(EntityManager em) {
        SettingBanner settingBanner = new SettingBanner()
            .image(DEFAULT_IMAGE)
            .mainText(DEFAULT_MAIN_TEXT)
            .subText(DEFAULT_SUB_TEXT)
            .order(DEFAULT_ORDER);
        return settingBanner;
    }

    @Before
    public void initTest() {
        settingBanner = createEntity(em);
    }

    @Test
    @Transactional
    public void createSettingBanner() throws Exception {
        int databaseSizeBeforeCreate = settingBannerRepository.findAll().size();

        // Create the SettingBanner
        SettingBannerDTO settingBannerDTO = settingBannerMapper.toDto(settingBanner);
        restSettingBannerMockMvc.perform(post("/api/setting-banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingBannerDTO)))
            .andExpect(status().isCreated());

        // Validate the SettingBanner in the database
        List<SettingBanner> settingBannerList = settingBannerRepository.findAll();
        assertThat(settingBannerList).hasSize(databaseSizeBeforeCreate + 1);
        SettingBanner testSettingBanner = settingBannerList.get(settingBannerList.size() - 1);
        assertThat(testSettingBanner.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSettingBanner.getMainText()).isEqualTo(DEFAULT_MAIN_TEXT);
        assertThat(testSettingBanner.getSubText()).isEqualTo(DEFAULT_SUB_TEXT);
        assertThat(testSettingBanner.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createSettingBannerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingBannerRepository.findAll().size();

        // Create the SettingBanner with an existing ID
        settingBanner.setId(1L);
        SettingBannerDTO settingBannerDTO = settingBannerMapper.toDto(settingBanner);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingBannerMockMvc.perform(post("/api/setting-banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingBannerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingBanner in the database
        List<SettingBanner> settingBannerList = settingBannerRepository.findAll();
        assertThat(settingBannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSettingBanners() throws Exception {
        // Initialize the database
        settingBannerRepository.saveAndFlush(settingBanner);

        // Get all the settingBannerList
        restSettingBannerMockMvc.perform(get("/api/setting-banners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingBanner.getId().intValue())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].mainText").value(hasItem(DEFAULT_MAIN_TEXT.toString())))
            .andExpect(jsonPath("$.[*].subText").value(hasItem(DEFAULT_SUB_TEXT.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getSettingBanner() throws Exception {
        // Initialize the database
        settingBannerRepository.saveAndFlush(settingBanner);

        // Get the settingBanner
        restSettingBannerMockMvc.perform(get("/api/setting-banners/{id}", settingBanner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(settingBanner.getId().intValue()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.mainText").value(DEFAULT_MAIN_TEXT.toString()))
            .andExpect(jsonPath("$.subText").value(DEFAULT_SUB_TEXT.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingSettingBanner() throws Exception {
        // Get the settingBanner
        restSettingBannerMockMvc.perform(get("/api/setting-banners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSettingBanner() throws Exception {
        // Initialize the database
        settingBannerRepository.saveAndFlush(settingBanner);

        int databaseSizeBeforeUpdate = settingBannerRepository.findAll().size();

        // Update the settingBanner
        SettingBanner updatedSettingBanner = settingBannerRepository.findById(settingBanner.getId()).get();
        // Disconnect from session so that the updates on updatedSettingBanner are not directly saved in db
        em.detach(updatedSettingBanner);
        updatedSettingBanner
            .image(UPDATED_IMAGE)
            .mainText(UPDATED_MAIN_TEXT)
            .subText(UPDATED_SUB_TEXT)
            .order(UPDATED_ORDER);
        SettingBannerDTO settingBannerDTO = settingBannerMapper.toDto(updatedSettingBanner);

        restSettingBannerMockMvc.perform(put("/api/setting-banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingBannerDTO)))
            .andExpect(status().isOk());

        // Validate the SettingBanner in the database
        List<SettingBanner> settingBannerList = settingBannerRepository.findAll();
        assertThat(settingBannerList).hasSize(databaseSizeBeforeUpdate);
        SettingBanner testSettingBanner = settingBannerList.get(settingBannerList.size() - 1);
        assertThat(testSettingBanner.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSettingBanner.getMainText()).isEqualTo(UPDATED_MAIN_TEXT);
        assertThat(testSettingBanner.getSubText()).isEqualTo(UPDATED_SUB_TEXT);
        assertThat(testSettingBanner.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingSettingBanner() throws Exception {
        int databaseSizeBeforeUpdate = settingBannerRepository.findAll().size();

        // Create the SettingBanner
        SettingBannerDTO settingBannerDTO = settingBannerMapper.toDto(settingBanner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingBannerMockMvc.perform(put("/api/setting-banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingBannerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingBanner in the database
        List<SettingBanner> settingBannerList = settingBannerRepository.findAll();
        assertThat(settingBannerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSettingBanner() throws Exception {
        // Initialize the database
        settingBannerRepository.saveAndFlush(settingBanner);

        int databaseSizeBeforeDelete = settingBannerRepository.findAll().size();

        // Get the settingBanner
        restSettingBannerMockMvc.perform(delete("/api/setting-banners/{id}", settingBanner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SettingBanner> settingBannerList = settingBannerRepository.findAll();
        assertThat(settingBannerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingBanner.class);
        SettingBanner settingBanner1 = new SettingBanner();
        settingBanner1.setId(1L);
        SettingBanner settingBanner2 = new SettingBanner();
        settingBanner2.setId(settingBanner1.getId());
        assertThat(settingBanner1).isEqualTo(settingBanner2);
        settingBanner2.setId(2L);
        assertThat(settingBanner1).isNotEqualTo(settingBanner2);
        settingBanner1.setId(null);
        assertThat(settingBanner1).isNotEqualTo(settingBanner2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingBannerDTO.class);
        SettingBannerDTO settingBannerDTO1 = new SettingBannerDTO();
        settingBannerDTO1.setId(1L);
        SettingBannerDTO settingBannerDTO2 = new SettingBannerDTO();
        assertThat(settingBannerDTO1).isNotEqualTo(settingBannerDTO2);
        settingBannerDTO2.setId(settingBannerDTO1.getId());
        assertThat(settingBannerDTO1).isEqualTo(settingBannerDTO2);
        settingBannerDTO2.setId(2L);
        assertThat(settingBannerDTO1).isNotEqualTo(settingBannerDTO2);
        settingBannerDTO1.setId(null);
        assertThat(settingBannerDTO1).isNotEqualTo(settingBannerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settingBannerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settingBannerMapper.fromId(null)).isNull();
    }
}
