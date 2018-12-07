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
import com.coexplore.api.domain.SettingContent;
import com.coexplore.api.repository.SettingContentRepository;
import com.coexplore.api.service.SettingContentService;
import com.coexplore.api.service.dto.SettingContentDTO;
import com.coexplore.api.service.mapper.SettingContentMapper;

/**
 * Test class for the SettingContentResource REST controller.
 *
 * @see SettingContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoexploreapiApp.class)
public class SettingContentResourceIntTest {

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private SettingContentRepository settingContentRepository;

    @Autowired
    private SettingContentMapper settingContentMapper;

    @Autowired
    private SettingContentService settingContentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private DefaultExceptionAdvice defaultExceptionAdvice;

    @Autowired
    private EntityManager em;

    private MockMvc restSettingContentMockMvc;

    private SettingContent settingContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettingContentResource settingContentResource = new SettingContentResource(settingContentService);
        this.restSettingContentMockMvc = MockMvcBuilders.standaloneSetup(settingContentResource)
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
    public static SettingContent createEntity(EntityManager em) {
        SettingContent settingContent = new SettingContent()
            .item(DEFAULT_ITEM)
            .content(DEFAULT_CONTENT);
        return settingContent;
    }

    @Before
    public void initTest() {
        settingContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSettingContent() throws Exception {
        int databaseSizeBeforeCreate = settingContentRepository.findAll().size();

        // Create the SettingContent
        SettingContentDTO settingContentDTO = settingContentMapper.toDto(settingContent);
        restSettingContentMockMvc.perform(post("/api/setting-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingContentDTO)))
            .andExpect(status().isCreated());

        // Validate the SettingContent in the database
        List<SettingContent> settingContentList = settingContentRepository.findAll();
        assertThat(settingContentList).hasSize(databaseSizeBeforeCreate + 1);
        SettingContent testSettingContent = settingContentList.get(settingContentList.size() - 1);
        assertThat(testSettingContent.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testSettingContent.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createSettingContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingContentRepository.findAll().size();

        // Create the SettingContent with an existing ID
        settingContent.setId(1L);
        SettingContentDTO settingContentDTO = settingContentMapper.toDto(settingContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingContentMockMvc.perform(post("/api/setting-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingContent in the database
        List<SettingContent> settingContentList = settingContentRepository.findAll();
        assertThat(settingContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSettingContents() throws Exception {
        // Initialize the database
        settingContentRepository.saveAndFlush(settingContent);

        // Get all the settingContentList
        restSettingContentMockMvc.perform(get("/api/setting-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }
    
    @Test
    @Transactional
    public void getSettingContent() throws Exception {
        // Initialize the database
        settingContentRepository.saveAndFlush(settingContent);

        // Get the settingContent
        restSettingContentMockMvc.perform(get("/api/setting-contents/{id}", settingContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(settingContent.getId().intValue()))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSettingContent() throws Exception {
        // Get the settingContent
        restSettingContentMockMvc.perform(get("/api/setting-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSettingContent() throws Exception {
        // Initialize the database
        settingContentRepository.saveAndFlush(settingContent);

        int databaseSizeBeforeUpdate = settingContentRepository.findAll().size();

        // Update the settingContent
        SettingContent updatedSettingContent = settingContentRepository.findById(settingContent.getId()).get();
        // Disconnect from session so that the updates on updatedSettingContent are not directly saved in db
        em.detach(updatedSettingContent);
        updatedSettingContent
            .item(UPDATED_ITEM)
            .content(UPDATED_CONTENT);
        SettingContentDTO settingContentDTO = settingContentMapper.toDto(updatedSettingContent);

        restSettingContentMockMvc.perform(put("/api/setting-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingContentDTO)))
            .andExpect(status().isOk());

        // Validate the SettingContent in the database
        List<SettingContent> settingContentList = settingContentRepository.findAll();
        assertThat(settingContentList).hasSize(databaseSizeBeforeUpdate);
        SettingContent testSettingContent = settingContentList.get(settingContentList.size() - 1);
        assertThat(testSettingContent.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testSettingContent.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingSettingContent() throws Exception {
        int databaseSizeBeforeUpdate = settingContentRepository.findAll().size();

        // Create the SettingContent
        SettingContentDTO settingContentDTO = settingContentMapper.toDto(settingContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingContentMockMvc.perform(put("/api/setting-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SettingContent in the database
        List<SettingContent> settingContentList = settingContentRepository.findAll();
        assertThat(settingContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSettingContent() throws Exception {
        // Initialize the database
        settingContentRepository.saveAndFlush(settingContent);

        int databaseSizeBeforeDelete = settingContentRepository.findAll().size();

        // Get the settingContent
        restSettingContentMockMvc.perform(delete("/api/setting-contents/{id}", settingContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SettingContent> settingContentList = settingContentRepository.findAll();
        assertThat(settingContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingContent.class);
        SettingContent settingContent1 = new SettingContent();
        settingContent1.setId(1L);
        SettingContent settingContent2 = new SettingContent();
        settingContent2.setId(settingContent1.getId());
        assertThat(settingContent1).isEqualTo(settingContent2);
        settingContent2.setId(2L);
        assertThat(settingContent1).isNotEqualTo(settingContent2);
        settingContent1.setId(null);
        assertThat(settingContent1).isNotEqualTo(settingContent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingContentDTO.class);
        SettingContentDTO settingContentDTO1 = new SettingContentDTO();
        settingContentDTO1.setId(1L);
        SettingContentDTO settingContentDTO2 = new SettingContentDTO();
        assertThat(settingContentDTO1).isNotEqualTo(settingContentDTO2);
        settingContentDTO2.setId(settingContentDTO1.getId());
        assertThat(settingContentDTO1).isEqualTo(settingContentDTO2);
        settingContentDTO2.setId(2L);
        assertThat(settingContentDTO1).isNotEqualTo(settingContentDTO2);
        settingContentDTO1.setId(null);
        assertThat(settingContentDTO1).isNotEqualTo(settingContentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settingContentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settingContentMapper.fromId(null)).isNull();
    }
}
