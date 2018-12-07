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
import com.coexplore.api.domain.OccupiedRoom;
import com.coexplore.api.repository.OccupiedRoomRepository;
import com.coexplore.api.service.OccupiedRoomService;
import com.coexplore.api.service.dto.OccupiedRoomDTO;
import com.coexplore.api.service.mapper.OccupiedRoomMapper;

/**
 * Test class for the OccupiedRoomResource REST controller.
 *
 * @see OccupiedRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoexploreapiApp.class)
public class OccupiedRoomResourceIntTest {

    private static final Instant DEFAULT_CHECK_IN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_IN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CHECK_OUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_OUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OccupiedRoomRepository occupiedRoomRepository;

    @Autowired
    private OccupiedRoomMapper occupiedRoomMapper;

    @Autowired
    private OccupiedRoomService occupiedRoomService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private DefaultExceptionAdvice defaultExceptionAdvice;

    @Autowired
    private EntityManager em;

    private MockMvc restOccupiedRoomMockMvc;

    private OccupiedRoom occupiedRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccupiedRoomResource occupiedRoomResource = new OccupiedRoomResource(occupiedRoomService);
        this.restOccupiedRoomMockMvc = MockMvcBuilders.standaloneSetup(occupiedRoomResource)
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
    public static OccupiedRoom createEntity(EntityManager em) {
        OccupiedRoom occupiedRoom = new OccupiedRoom()
            .checkIn(DEFAULT_CHECK_IN)
            .checkOut(DEFAULT_CHECK_OUT);
        return occupiedRoom;
    }

    @Before
    public void initTest() {
        occupiedRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccupiedRoom() throws Exception {
        int databaseSizeBeforeCreate = occupiedRoomRepository.findAll().size();

        // Create the OccupiedRoom
        OccupiedRoomDTO occupiedRoomDTO = occupiedRoomMapper.toDto(occupiedRoom);
        restOccupiedRoomMockMvc.perform(post("/api/occupied-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occupiedRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the OccupiedRoom in the database
        List<OccupiedRoom> occupiedRoomList = occupiedRoomRepository.findAll();
        assertThat(occupiedRoomList).hasSize(databaseSizeBeforeCreate + 1);
        OccupiedRoom testOccupiedRoom = occupiedRoomList.get(occupiedRoomList.size() - 1);
        assertThat(testOccupiedRoom.getCheckIn()).isEqualTo(DEFAULT_CHECK_IN);
        assertThat(testOccupiedRoom.getCheckOut()).isEqualTo(DEFAULT_CHECK_OUT);
    }

    @Test
    @Transactional
    public void createOccupiedRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occupiedRoomRepository.findAll().size();

        // Create the OccupiedRoom with an existing ID
        occupiedRoom.setId(1L);
        OccupiedRoomDTO occupiedRoomDTO = occupiedRoomMapper.toDto(occupiedRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccupiedRoomMockMvc.perform(post("/api/occupied-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occupiedRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OccupiedRoom in the database
        List<OccupiedRoom> occupiedRoomList = occupiedRoomRepository.findAll();
        assertThat(occupiedRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOccupiedRooms() throws Exception {
        // Initialize the database
        occupiedRoomRepository.saveAndFlush(occupiedRoom);

        // Get all the occupiedRoomList
        restOccupiedRoomMockMvc.perform(get("/api/occupied-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occupiedRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkIn").value(hasItem(DEFAULT_CHECK_IN.toString())))
            .andExpect(jsonPath("$.[*].checkOut").value(hasItem(DEFAULT_CHECK_OUT.toString())));
    }
    
    @Test
    @Transactional
    public void getOccupiedRoom() throws Exception {
        // Initialize the database
        occupiedRoomRepository.saveAndFlush(occupiedRoom);

        // Get the occupiedRoom
        restOccupiedRoomMockMvc.perform(get("/api/occupied-rooms/{id}", occupiedRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occupiedRoom.getId().intValue()))
            .andExpect(jsonPath("$.checkIn").value(DEFAULT_CHECK_IN.toString()))
            .andExpect(jsonPath("$.checkOut").value(DEFAULT_CHECK_OUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOccupiedRoom() throws Exception {
        // Get the occupiedRoom
        restOccupiedRoomMockMvc.perform(get("/api/occupied-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccupiedRoom() throws Exception {
        // Initialize the database
        occupiedRoomRepository.saveAndFlush(occupiedRoom);

        int databaseSizeBeforeUpdate = occupiedRoomRepository.findAll().size();

        // Update the occupiedRoom
        OccupiedRoom updatedOccupiedRoom = occupiedRoomRepository.findById(occupiedRoom.getId()).get();
        // Disconnect from session so that the updates on updatedOccupiedRoom are not directly saved in db
        em.detach(updatedOccupiedRoom);
        updatedOccupiedRoom
            .checkIn(UPDATED_CHECK_IN)
            .checkOut(UPDATED_CHECK_OUT);
        OccupiedRoomDTO occupiedRoomDTO = occupiedRoomMapper.toDto(updatedOccupiedRoom);

        restOccupiedRoomMockMvc.perform(put("/api/occupied-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occupiedRoomDTO)))
            .andExpect(status().isOk());

        // Validate the OccupiedRoom in the database
        List<OccupiedRoom> occupiedRoomList = occupiedRoomRepository.findAll();
        assertThat(occupiedRoomList).hasSize(databaseSizeBeforeUpdate);
        OccupiedRoom testOccupiedRoom = occupiedRoomList.get(occupiedRoomList.size() - 1);
        assertThat(testOccupiedRoom.getCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testOccupiedRoom.getCheckOut()).isEqualTo(UPDATED_CHECK_OUT);
    }

    @Test
    @Transactional
    public void updateNonExistingOccupiedRoom() throws Exception {
        int databaseSizeBeforeUpdate = occupiedRoomRepository.findAll().size();

        // Create the OccupiedRoom
        OccupiedRoomDTO occupiedRoomDTO = occupiedRoomMapper.toDto(occupiedRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOccupiedRoomMockMvc.perform(put("/api/occupied-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occupiedRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OccupiedRoom in the database
        List<OccupiedRoom> occupiedRoomList = occupiedRoomRepository.findAll();
        assertThat(occupiedRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOccupiedRoom() throws Exception {
        // Initialize the database
        occupiedRoomRepository.saveAndFlush(occupiedRoom);

        int databaseSizeBeforeDelete = occupiedRoomRepository.findAll().size();

        // Get the occupiedRoom
        restOccupiedRoomMockMvc.perform(delete("/api/occupied-rooms/{id}", occupiedRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OccupiedRoom> occupiedRoomList = occupiedRoomRepository.findAll();
        assertThat(occupiedRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccupiedRoom.class);
        OccupiedRoom occupiedRoom1 = new OccupiedRoom();
        occupiedRoom1.setId(1L);
        OccupiedRoom occupiedRoom2 = new OccupiedRoom();
        occupiedRoom2.setId(occupiedRoom1.getId());
        assertThat(occupiedRoom1).isEqualTo(occupiedRoom2);
        occupiedRoom2.setId(2L);
        assertThat(occupiedRoom1).isNotEqualTo(occupiedRoom2);
        occupiedRoom1.setId(null);
        assertThat(occupiedRoom1).isNotEqualTo(occupiedRoom2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccupiedRoomDTO.class);
        OccupiedRoomDTO occupiedRoomDTO1 = new OccupiedRoomDTO();
        occupiedRoomDTO1.setId(1L);
        OccupiedRoomDTO occupiedRoomDTO2 = new OccupiedRoomDTO();
        assertThat(occupiedRoomDTO1).isNotEqualTo(occupiedRoomDTO2);
        occupiedRoomDTO2.setId(occupiedRoomDTO1.getId());
        assertThat(occupiedRoomDTO1).isEqualTo(occupiedRoomDTO2);
        occupiedRoomDTO2.setId(2L);
        assertThat(occupiedRoomDTO1).isNotEqualTo(occupiedRoomDTO2);
        occupiedRoomDTO1.setId(null);
        assertThat(occupiedRoomDTO1).isNotEqualTo(occupiedRoomDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(occupiedRoomMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(occupiedRoomMapper.fromId(null)).isNull();
    }
}
