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
import org.freeteratec.mepster.domain.MonthlyAvailability;
import org.freeteratec.mepster.domain.Person;
import org.freeteratec.mepster.repository.MonthlyAvailabilityRepository;
import org.freeteratec.mepster.service.dto.MonthlyAvailabilityDTO;
import org.freeteratec.mepster.service.mapper.MonthlyAvailabilityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MonthlyAvailabilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MonthlyAvailabilityResourceIT {

    private static final LocalDate DEFAULT_YEARMONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEARMONTH = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERCENT = 0;
    private static final Integer UPDATED_PERCENT = 1;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/monthly-availabilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MonthlyAvailabilityRepository monthlyAvailabilityRepository;

    @Autowired
    private MonthlyAvailabilityMapper monthlyAvailabilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMonthlyAvailabilityMockMvc;

    private MonthlyAvailability monthlyAvailability;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyAvailability createEntity(EntityManager em) {
        MonthlyAvailability monthlyAvailability = new MonthlyAvailability()
            .yearmonth(DEFAULT_YEARMONTH)
            .percent(DEFAULT_PERCENT)
            .isActive(DEFAULT_IS_ACTIVE);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        monthlyAvailability.setPerson(person);
        return monthlyAvailability;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MonthlyAvailability createUpdatedEntity(EntityManager em) {
        MonthlyAvailability monthlyAvailability = new MonthlyAvailability()
            .yearmonth(UPDATED_YEARMONTH)
            .percent(UPDATED_PERCENT)
            .isActive(UPDATED_IS_ACTIVE);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        monthlyAvailability.setPerson(person);
        return monthlyAvailability;
    }

    @BeforeEach
    public void initTest() {
        monthlyAvailability = createEntity(em);
    }

    @Test
    @Transactional
    void createMonthlyAvailability() throws Exception {
        int databaseSizeBeforeCreate = monthlyAvailabilityRepository.findAll().size();
        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);
        restMonthlyAvailabilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeCreate + 1);
        MonthlyAvailability testMonthlyAvailability = monthlyAvailabilityList.get(monthlyAvailabilityList.size() - 1);
        assertThat(testMonthlyAvailability.getYearmonth()).isEqualTo(DEFAULT_YEARMONTH);
        assertThat(testMonthlyAvailability.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testMonthlyAvailability.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createMonthlyAvailabilityWithExistingId() throws Exception {
        // Create the MonthlyAvailability with an existing ID
        monthlyAvailability.setId(1L);
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        int databaseSizeBeforeCreate = monthlyAvailabilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonthlyAvailabilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = monthlyAvailabilityRepository.findAll().size();
        // set the field null
        monthlyAvailability.setIsActive(null);

        // Create the MonthlyAvailability, which fails.
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        restMonthlyAvailabilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMonthlyAvailabilities() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        // Get all the monthlyAvailabilityList
        restMonthlyAvailabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monthlyAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearmonth").value(hasItem(DEFAULT_YEARMONTH.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getMonthlyAvailability() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        // Get the monthlyAvailability
        restMonthlyAvailabilityMockMvc
            .perform(get(ENTITY_API_URL_ID, monthlyAvailability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monthlyAvailability.getId().intValue()))
            .andExpect(jsonPath("$.yearmonth").value(DEFAULT_YEARMONTH.toString()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMonthlyAvailability() throws Exception {
        // Get the monthlyAvailability
        restMonthlyAvailabilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMonthlyAvailability() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();

        // Update the monthlyAvailability
        MonthlyAvailability updatedMonthlyAvailability = monthlyAvailabilityRepository.findById(monthlyAvailability.getId()).get();
        // Disconnect from session so that the updates on updatedMonthlyAvailability are not directly saved in db
        em.detach(updatedMonthlyAvailability);
        updatedMonthlyAvailability.yearmonth(UPDATED_YEARMONTH).percent(UPDATED_PERCENT).isActive(UPDATED_IS_ACTIVE);
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(updatedMonthlyAvailability);

        restMonthlyAvailabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyAvailabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        MonthlyAvailability testMonthlyAvailability = monthlyAvailabilityList.get(monthlyAvailabilityList.size() - 1);
        assertThat(testMonthlyAvailability.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyAvailability.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testMonthlyAvailability.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, monthlyAvailabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMonthlyAvailabilityWithPatch() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();

        // Update the monthlyAvailability using partial update
        MonthlyAvailability partialUpdatedMonthlyAvailability = new MonthlyAvailability();
        partialUpdatedMonthlyAvailability.setId(monthlyAvailability.getId());

        partialUpdatedMonthlyAvailability.yearmonth(UPDATED_YEARMONTH);

        restMonthlyAvailabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyAvailability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyAvailability))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        MonthlyAvailability testMonthlyAvailability = monthlyAvailabilityList.get(monthlyAvailabilityList.size() - 1);
        assertThat(testMonthlyAvailability.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyAvailability.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testMonthlyAvailability.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateMonthlyAvailabilityWithPatch() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();

        // Update the monthlyAvailability using partial update
        MonthlyAvailability partialUpdatedMonthlyAvailability = new MonthlyAvailability();
        partialUpdatedMonthlyAvailability.setId(monthlyAvailability.getId());

        partialUpdatedMonthlyAvailability.yearmonth(UPDATED_YEARMONTH).percent(UPDATED_PERCENT).isActive(UPDATED_IS_ACTIVE);

        restMonthlyAvailabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMonthlyAvailability.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMonthlyAvailability))
            )
            .andExpect(status().isOk());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        MonthlyAvailability testMonthlyAvailability = monthlyAvailabilityList.get(monthlyAvailabilityList.size() - 1);
        assertThat(testMonthlyAvailability.getYearmonth()).isEqualTo(UPDATED_YEARMONTH);
        assertThat(testMonthlyAvailability.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testMonthlyAvailability.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, monthlyAvailabilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMonthlyAvailability() throws Exception {
        int databaseSizeBeforeUpdate = monthlyAvailabilityRepository.findAll().size();
        monthlyAvailability.setId(count.incrementAndGet());

        // Create the MonthlyAvailability
        MonthlyAvailabilityDTO monthlyAvailabilityDTO = monthlyAvailabilityMapper.toDto(monthlyAvailability);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMonthlyAvailabilityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(monthlyAvailabilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MonthlyAvailability in the database
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMonthlyAvailability() throws Exception {
        // Initialize the database
        monthlyAvailabilityRepository.saveAndFlush(monthlyAvailability);

        int databaseSizeBeforeDelete = monthlyAvailabilityRepository.findAll().size();

        // Delete the monthlyAvailability
        restMonthlyAvailabilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, monthlyAvailability.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MonthlyAvailability> monthlyAvailabilityList = monthlyAvailabilityRepository.findAll();
        assertThat(monthlyAvailabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
