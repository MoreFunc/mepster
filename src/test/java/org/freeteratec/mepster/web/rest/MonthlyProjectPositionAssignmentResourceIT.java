package org.freeteratec.mepster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.freeteratec.mepster.IntegrationTest;
import org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment;
import org.freeteratec.mepster.domain.Person;
import org.freeteratec.mepster.domain.ProjectPosition;
import org.freeteratec.mepster.repository.MonthlyProjectPositionAssignmentRepository;
import org.freeteratec.mepster.service.dto.MonthlyProjectPositionAssignmentDTO;
import org.freeteratec.mepster.service.mapper.MonthlyProjectPositionAssignmentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MonthlyProjectPositionAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonthlyProjectPositionAssignmentResourceIT {

    private static final LocalDate DEFAULT_YEARMONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEARMONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERCENT = 0;
    private static final Integer UPDATED_PERCENT = 1;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/monthly-project-position-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MonthlyProjectPositionAssignmentRepository monthlyProjectPositionAssignmentRepository;

    @Autowired
    private MonthlyProjectPositionAssignmentMapper monthlyProjectPositionAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlyProjectPositionAssignmentMockMvc;

    private MonthlyProjectPositionAssignment monthlyProjectPositionAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyProjectPositionAssignment createEntity(EntityManager em) {
        MonthlyProjectPositionAssignment monthlyProjectPositionAssignment = new MonthlyProjectPositionAssignment()
            .yearmonth(DEFAULT_YEARMONTH)
            .percent(DEFAULT_PERCENT)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ProjectPosition projectPosition;
        if (TestUtil.findAll(em, ProjectPosition.class).isEmpty()) {
            projectPosition = ProjectPositionResourceIT.createEntity(em);
            em.persist(projectPosition);
            em.flush();
        } else {
            projectPosition = TestUtil.findAll(em, ProjectPosition.class).get(0);
        }
        monthlyProjectPositionAssignment.setProjectPosition(projectPosition);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        monthlyProjectPositionAssignment.setPerson(person);
        return monthlyProjectPositionAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyProjectPositionAssignment createUpdatedEntity(EntityManager em) {
        MonthlyProjectPositionAssignment monthlyProjectPositionAssignment = new MonthlyProjectPositionAssignment()
            .yearmonth(UPDATED_YEARMONTH)
            .percent(UPDATED_PERCENT)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ProjectPosition projectPosition;
        if (TestUtil.findAll(em, ProjectPosition.class).isEmpty()) {
            projectPosition = ProjectPositionResourceIT.createUpdatedEntity(em);
            em.persist(projectPosition);
            em.flush();
        } else {
            projectPosition = TestUtil.findAll(em, ProjectPosition.class).get(0);
        }
        monthlyProjectPositionAssignment.setProjectPosition(projectPosition);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        monthlyProjectPositionAssignment.setPerson(person);
        return monthlyProjectPositionAssignment;
    }

    @BeforeEach
    public void initTest() {
        monthlyProjectPositionAssignment = createEntity(em);
    }

    @Test
    @Transactional
    void createMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeCreate = monthlyProjectPositionAssignmentRepository.findAll().size();
        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyProjectPositionAssignment testMonthlyProjectPositionAssignment = monthlyProjectPositionAssignmentList.get(
            monthlyProjectPositionAssignmentList.size() - 1
        );
        assertThat(testMonthlyProjectPositionAssignment.getYearmonth()).isEqualTo(DEFAULT_YEARMONTH);
        assertThat(testMonthlyProjectPositionAssignment.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testMonthlyProjectPositionAssignment.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createMonthlyProjectPositionAssignmentWithExistingId() throws Exception {
        // Create the MonthlyProjectPositionAssignment with an existing ID
        monthlyProjectPositionAssignment.setId(1L);
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        int databaseSizeBeforeCreate = monthlyProjectPositionAssignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyProjectPositionAssignmentRepository.findAll().size();
        // set the field null
        monthlyProjectPositionAssignment.setActive(null);

        // Create the MonthlyProjectPositionAssignment, which fails.
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMonthlyProjectPositionAssignments() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        // Get all the monthlyProjectPositionAssignmentList
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyProjectPositionAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearmonth").value(hasItem(DEFAULT_YEARMONTH.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getMonthlyProjectPositionAssignment() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        // Get the monthlyProjectPositionAssignment
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, monthlyProjectPositionAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyProjectPositionAssignment.getId().intValue()))
            .andExpect(jsonPath("$.yearmonth").value(DEFAULT_YEARMONTH.toString()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMonthlyProjectPositionAssignment() throws Exception {
        // Get the monthlyProjectPositionAssignment
        restMonthlyProjectPositionAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMonthlyProjectPositionAssignment() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();

        // Update the monthlyProjectPositionAssignment
        MonthlyProjectPositionAssignment updatedMonthlyProjectPositionAssignment = monthlyProjectPositionAssignmentRepository
            .findById(monthlyProjectPositionAssignment.getId())
            .get();
        // Disconnect from session so that the updates on updatedMonthlyProjectPositionAssignment are not directly saved in db
        em.detach(updatedMonthlyProjectPositionAssignment);
        updatedMonthlyProjectPositionAssignment.yearmonth(UPDATED_YEARMONTH).percent(UPDATED_PERCENT).active(UPDATED_ACTIVE);
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            updatedMonthlyProjectPositionAssignment
        );

        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyProjectPositionAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
        MonthlyProjectPositionAssignment testMonthlyProjectPositionAssignment = monthlyProjectPositionAssignmentList.get(
            monthlyProjectPositionAssignmentList.size() - 1
        );
        assertThat(testMonthlyProjectPositionAssignment.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyProjectPositionAssignment.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testMonthlyProjectPositionAssignment.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyProjectPositionAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonthlyProjectPositionAssignmentWithPatch() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();

        // Update the monthlyProjectPositionAssignment using partial update
        MonthlyProjectPositionAssignment partialUpdatedMonthlyProjectPositionAssignment = new MonthlyProjectPositionAssignment();
        partialUpdatedMonthlyProjectPositionAssignment.setId(monthlyProjectPositionAssignment.getId());

        partialUpdatedMonthlyProjectPositionAssignment.yearmonth(UPDATED_YEARMONTH).percent(UPDATED_PERCENT);

        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyProjectPositionAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyProjectPositionAssignment))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
        MonthlyProjectPositionAssignment testMonthlyProjectPositionAssignment = monthlyProjectPositionAssignmentList.get(
            monthlyProjectPositionAssignmentList.size() - 1
        );
        assertThat(testMonthlyProjectPositionAssignment.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyProjectPositionAssignment.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testMonthlyProjectPositionAssignment.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateMonthlyProjectPositionAssignmentWithPatch() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();

        // Update the monthlyProjectPositionAssignment using partial update
        MonthlyProjectPositionAssignment partialUpdatedMonthlyProjectPositionAssignment = new MonthlyProjectPositionAssignment();
        partialUpdatedMonthlyProjectPositionAssignment.setId(monthlyProjectPositionAssignment.getId());

        partialUpdatedMonthlyProjectPositionAssignment.yearmonth(UPDATED_YEARMONTH).percent(UPDATED_PERCENT).active(UPDATED_ACTIVE);

        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyProjectPositionAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyProjectPositionAssignment))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
        MonthlyProjectPositionAssignment testMonthlyProjectPositionAssignment = monthlyProjectPositionAssignmentList.get(
            monthlyProjectPositionAssignmentList.size() - 1
        );
        assertThat(testMonthlyProjectPositionAssignment.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyProjectPositionAssignment.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testMonthlyProjectPositionAssignment.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monthlyProjectPositionAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonthlyProjectPositionAssignment() throws Exception {
        int databaseSizeBeforeUpdate = monthlyProjectPositionAssignmentRepository.findAll().size();
        monthlyProjectPositionAssignment.setId(count.incrementAndGet());

        // Create the MonthlyProjectPositionAssignment
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentMapper.toDto(
            monthlyProjectPositionAssignment
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyProjectPositionAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyProjectPositionAssignment in the database
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonthlyProjectPositionAssignment() throws Exception {
        // Initialize the database
        monthlyProjectPositionAssignmentRepository.saveAndFlush(monthlyProjectPositionAssignment);

        int databaseSizeBeforeDelete = monthlyProjectPositionAssignmentRepository.findAll().size();

        // Delete the monthlyProjectPositionAssignment
        restMonthlyProjectPositionAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, monthlyProjectPositionAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignmentList = monthlyProjectPositionAssignmentRepository.findAll();
        assertThat(monthlyProjectPositionAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
