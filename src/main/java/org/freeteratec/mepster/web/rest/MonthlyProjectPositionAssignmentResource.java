package org.freeteratec.mepster.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.freeteratec.mepster.repository.MonthlyProjectPositionAssignmentRepository;
import org.freeteratec.mepster.service.MonthlyProjectPositionAssignmentService;
import org.freeteratec.mepster.service.dto.MonthlyProjectPositionAssignmentDTO;
import org.freeteratec.mepster.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment}.
 */
@RestController
@RequestMapping("/api")
public class MonthlyProjectPositionAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyProjectPositionAssignmentResource.class);

    private static final String ENTITY_NAME = "monthlyProjectPositionAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlyProjectPositionAssignmentService monthlyProjectPositionAssignmentService;

    private final MonthlyProjectPositionAssignmentRepository monthlyProjectPositionAssignmentRepository;

    public MonthlyProjectPositionAssignmentResource(
        MonthlyProjectPositionAssignmentService monthlyProjectPositionAssignmentService,
        MonthlyProjectPositionAssignmentRepository monthlyProjectPositionAssignmentRepository
    ) {
        this.monthlyProjectPositionAssignmentService = monthlyProjectPositionAssignmentService;
        this.monthlyProjectPositionAssignmentRepository = monthlyProjectPositionAssignmentRepository;
    }

    /**
     * {@code POST  /monthly-project-position-assignments} : Create a new monthlyProjectPositionAssignment.
     *
     * @param monthlyProjectPositionAssignmentDTO the monthlyProjectPositionAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlyProjectPositionAssignmentDTO, or with status {@code 400 (Bad Request)} if the monthlyProjectPositionAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-project-position-assignments")
    public ResponseEntity<MonthlyProjectPositionAssignmentDTO> createMonthlyProjectPositionAssignment(
        @Valid @RequestBody MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MonthlyProjectPositionAssignment : {}", monthlyProjectPositionAssignmentDTO);
        if (monthlyProjectPositionAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyProjectPositionAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyProjectPositionAssignmentDTO result = monthlyProjectPositionAssignmentService.save(monthlyProjectPositionAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/monthly-project-position-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monthly-project-position-assignments/:id} : Updates an existing monthlyProjectPositionAssignment.
     *
     * @param id the id of the monthlyProjectPositionAssignmentDTO to save.
     * @param monthlyProjectPositionAssignmentDTO the monthlyProjectPositionAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyProjectPositionAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyProjectPositionAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlyProjectPositionAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-project-position-assignments/{id}")
    public ResponseEntity<MonthlyProjectPositionAssignmentDTO> updateMonthlyProjectPositionAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MonthlyProjectPositionAssignment : {}, {}", id, monthlyProjectPositionAssignmentDTO);
        if (monthlyProjectPositionAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyProjectPositionAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyProjectPositionAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MonthlyProjectPositionAssignmentDTO result = monthlyProjectPositionAssignmentService.save(monthlyProjectPositionAssignmentDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    monthlyProjectPositionAssignmentDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /monthly-project-position-assignments/:id} : Partial updates given fields of an existing monthlyProjectPositionAssignment, field will ignore if it is null
     *
     * @param id the id of the monthlyProjectPositionAssignmentDTO to save.
     * @param monthlyProjectPositionAssignmentDTO the monthlyProjectPositionAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyProjectPositionAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyProjectPositionAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monthlyProjectPositionAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monthlyProjectPositionAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monthly-project-position-assignments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MonthlyProjectPositionAssignmentDTO> partialUpdateMonthlyProjectPositionAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update MonthlyProjectPositionAssignment partially : {}, {}",
            id,
            monthlyProjectPositionAssignmentDTO
        );
        if (monthlyProjectPositionAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyProjectPositionAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyProjectPositionAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonthlyProjectPositionAssignmentDTO> result = monthlyProjectPositionAssignmentService.partialUpdate(
            monthlyProjectPositionAssignmentDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monthlyProjectPositionAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /monthly-project-position-assignments} : get all the monthlyProjectPositionAssignments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlyProjectPositionAssignments in body.
     */
    @GetMapping("/monthly-project-position-assignments")
    public List<MonthlyProjectPositionAssignmentDTO> getAllMonthlyProjectPositionAssignments() {
        log.debug("REST request to get all MonthlyProjectPositionAssignments");
        return monthlyProjectPositionAssignmentService.findAll();
    }

    /**
     * {@code GET  /monthly-project-position-assignments/:id} : get the "id" monthlyProjectPositionAssignment.
     *
     * @param id the id of the monthlyProjectPositionAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyProjectPositionAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-project-position-assignments/{id}")
    public ResponseEntity<MonthlyProjectPositionAssignmentDTO> getMonthlyProjectPositionAssignment(@PathVariable Long id) {
        log.debug("REST request to get MonthlyProjectPositionAssignment : {}", id);
        Optional<MonthlyProjectPositionAssignmentDTO> monthlyProjectPositionAssignmentDTO = monthlyProjectPositionAssignmentService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(monthlyProjectPositionAssignmentDTO);
    }

    /**
     * {@code DELETE  /monthly-project-position-assignments/:id} : delete the "id" monthlyProjectPositionAssignment.
     *
     * @param id the id of the monthlyProjectPositionAssignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-project-position-assignments/{id}")
    public ResponseEntity<Void> deleteMonthlyProjectPositionAssignment(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyProjectPositionAssignment : {}", id);
        monthlyProjectPositionAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
