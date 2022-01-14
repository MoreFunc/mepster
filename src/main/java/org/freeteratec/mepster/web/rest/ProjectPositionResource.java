package org.freeteratec.mepster.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.freeteratec.mepster.repository.ProjectPositionRepository;
import org.freeteratec.mepster.service.ProjectPositionService;
import org.freeteratec.mepster.service.dto.ProjectPositionDTO;
import org.freeteratec.mepster.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.freeteratec.mepster.domain.ProjectPosition}.
 */
@RestController
@RequestMapping("/api")
public class ProjectPositionResource {

    private final Logger log = LoggerFactory.getLogger(ProjectPositionResource.class);

    private static final String ENTITY_NAME = "projectPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectPositionService projectPositionService;

    private final ProjectPositionRepository projectPositionRepository;

    public ProjectPositionResource(ProjectPositionService projectPositionService, ProjectPositionRepository projectPositionRepository) {
        this.projectPositionService = projectPositionService;
        this.projectPositionRepository = projectPositionRepository;
    }

    /**
     * {@code POST  /project-positions} : Create a new projectPosition.
     *
     * @param projectPositionDTO the projectPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectPositionDTO, or with status {@code 400 (Bad Request)} if the projectPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-positions")
    public ResponseEntity<ProjectPositionDTO> createProjectPosition(@Valid @RequestBody ProjectPositionDTO projectPositionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectPosition : {}", projectPositionDTO);
        if (projectPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectPositionDTO result = projectPositionService.save(projectPositionDTO);
        return ResponseEntity
            .created(new URI("/api/project-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-positions/:id} : Updates an existing projectPosition.
     *
     * @param id the id of the projectPositionDTO to save.
     * @param projectPositionDTO the projectPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectPositionDTO,
     * or with status {@code 400 (Bad Request)} if the projectPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-positions/{id}")
    public ResponseEntity<ProjectPositionDTO> updateProjectPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectPositionDTO projectPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectPosition : {}, {}", id, projectPositionDTO);
        if (projectPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectPositionDTO result = projectPositionService.save(projectPositionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-positions/:id} : Partial updates given fields of an existing projectPosition, field will ignore if it is null
     *
     * @param id the id of the projectPositionDTO to save.
     * @param projectPositionDTO the projectPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectPositionDTO,
     * or with status {@code 400 (Bad Request)} if the projectPositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectPositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectPositionDTO> partialUpdateProjectPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectPositionDTO projectPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectPosition partially : {}, {}", id, projectPositionDTO);
        if (projectPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectPositionDTO> result = projectPositionService.partialUpdate(projectPositionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectPositionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-positions} : get all the projectPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectPositions in body.
     */
    @GetMapping("/project-positions")
    public List<ProjectPositionDTO> getAllProjectPositions() {
        log.debug("REST request to get all ProjectPositions");
        return projectPositionService.findAll();
    }

    /**
     * {@code GET  /project-positions/:id} : get the "id" projectPosition.
     *
     * @param id the id of the projectPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-positions/{id}")
    public ResponseEntity<ProjectPositionDTO> getProjectPosition(@PathVariable Long id) {
        log.debug("REST request to get ProjectPosition : {}", id);
        Optional<ProjectPositionDTO> projectPositionDTO = projectPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectPositionDTO);
    }

    /**
     * {@code DELETE  /project-positions/:id} : delete the "id" projectPosition.
     *
     * @param id the id of the projectPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-positions/{id}")
    public ResponseEntity<Void> deleteProjectPosition(@PathVariable Long id) {
        log.debug("REST request to delete ProjectPosition : {}", id);
        projectPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
