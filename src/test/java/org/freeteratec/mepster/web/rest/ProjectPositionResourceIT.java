package org.freeteratec.mepster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.freeteratec.mepster.IntegrationTest;
import org.freeteratec.mepster.domain.Project;
import org.freeteratec.mepster.domain.ProjectPosition;
import org.freeteratec.mepster.domain.Role;
import org.freeteratec.mepster.repository.ProjectPositionRepository;
import org.freeteratec.mepster.service.dto.ProjectPositionDTO;
import org.freeteratec.mepster.service.mapper.ProjectPositionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProjectPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectPositionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERCENT = 0;
    private static final Integer UPDATED_PERCENT = 1;

    private static final String ENTITY_API_URL = "/api/project-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectPositionRepository projectPositionRepository;

    @Autowired
    private ProjectPositionMapper projectPositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectPositionMockMvc;

    private ProjectPosition projectPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectPosition createEntity(EntityManager em) {
        ProjectPosition projectPosition = new ProjectPosition()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .percent(DEFAULT_PERCENT);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        projectPosition.setRole(role);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectPosition.setProject(project);
        return projectPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectPosition createUpdatedEntity(EntityManager em) {
        ProjectPosition projectPosition = new ProjectPosition()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .percent(UPDATED_PERCENT);
        // Add required entity
        Role role;
        if (TestUtil.findAll(em, Role.class).isEmpty()) {
            role = RoleResourceIT.createUpdatedEntity(em);
            em.persist(role);
            em.flush();
        } else {
            role = TestUtil.findAll(em, Role.class).get(0);
        }
        projectPosition.setRole(role);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        projectPosition.setProject(project);
        return projectPosition;
    }

    @BeforeEach
    public void initTest() {
        projectPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectPosition() throws Exception {
        int databaseSizeBeforeCreate = projectPositionRepository.findAll().size();
        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);
        restProjectPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectPosition testProjectPosition = projectPositionList.get(projectPositionList.size() - 1);
        assertThat(testProjectPosition.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProjectPosition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectPosition.getPercent()).isEqualTo(DEFAULT_PERCENT);
    }

    @Test
    @Transactional
    void createProjectPositionWithExistingId() throws Exception {
        // Create the ProjectPosition with an existing ID
        projectPosition.setId(1L);
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        int databaseSizeBeforeCreate = projectPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectPositionRepository.findAll().size();
        // set the field null
        projectPosition.setTitle(null);

        // Create the ProjectPosition, which fails.
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        restProjectPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectPositions() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        // Get all the projectPositionList
        restProjectPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)));
    }

    @Test
    @Transactional
    void getProjectPosition() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        // Get the projectPosition
        restProjectPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, projectPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectPosition.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT));
    }

    @Test
    @Transactional
    void getNonExistingProjectPosition() throws Exception {
        // Get the projectPosition
        restProjectPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectPosition() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();

        // Update the projectPosition
        ProjectPosition updatedProjectPosition = projectPositionRepository.findById(projectPosition.getId()).get();
        // Disconnect from session so that the updates on updatedProjectPosition are not directly saved in db
        em.detach(updatedProjectPosition);
        updatedProjectPosition.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).percent(UPDATED_PERCENT);
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(updatedProjectPosition);

        restProjectPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
        ProjectPosition testProjectPosition = projectPositionList.get(projectPositionList.size() - 1);
        assertThat(testProjectPosition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjectPosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectPosition.getPercent()).isEqualTo(UPDATED_PERCENT);
    }

    @Test
    @Transactional
    void putNonExistingProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectPositionWithPatch() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();

        // Update the projectPosition using partial update
        ProjectPosition partialUpdatedProjectPosition = new ProjectPosition();
        partialUpdatedProjectPosition.setId(projectPosition.getId());

        partialUpdatedProjectPosition.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restProjectPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectPosition))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
        ProjectPosition testProjectPosition = projectPositionList.get(projectPositionList.size() - 1);
        assertThat(testProjectPosition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjectPosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectPosition.getPercent()).isEqualTo(DEFAULT_PERCENT);
    }

    @Test
    @Transactional
    void fullUpdateProjectPositionWithPatch() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();

        // Update the projectPosition using partial update
        ProjectPosition partialUpdatedProjectPosition = new ProjectPosition();
        partialUpdatedProjectPosition.setId(projectPosition.getId());

        partialUpdatedProjectPosition.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).percent(UPDATED_PERCENT);

        restProjectPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectPosition))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
        ProjectPosition testProjectPosition = projectPositionList.get(projectPositionList.size() - 1);
        assertThat(testProjectPosition.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjectPosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectPosition.getPercent()).isEqualTo(UPDATED_PERCENT);
    }

    @Test
    @Transactional
    void patchNonExistingProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectPositionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectPosition() throws Exception {
        int databaseSizeBeforeUpdate = projectPositionRepository.findAll().size();
        projectPosition.setId(count.incrementAndGet());

        // Create the ProjectPosition
        ProjectPositionDTO projectPositionDTO = projectPositionMapper.toDto(projectPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectPosition in the database
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectPosition() throws Exception {
        // Initialize the database
        projectPositionRepository.saveAndFlush(projectPosition);

        int databaseSizeBeforeDelete = projectPositionRepository.findAll().size();

        // Delete the projectPosition
        restProjectPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectPosition> projectPositionList = projectPositionRepository.findAll();
        assertThat(projectPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
