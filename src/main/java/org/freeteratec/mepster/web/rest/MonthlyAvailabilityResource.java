package org.freeteratec.mepster.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.freeteratec.mepster.repository.MonthlyAvailabilityRepository;
import org.freeteratec.mepster.service.MonthlyAvailabilityService;
import org.freeteratec.mepster.service.dto.MonthlyAvailabilityDTO;
import org.freeteratec.mepster.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.freeteratec.mepster.domain.MonthlyAvailability}.
 */
@RestController
@RequestMapping("/api")
public class MonthlyAvailabilityResource {

    private final Logger log = LoggerFactory.getLogger(MonthlyAvailabilityResource.class);

    private static final String ENTITY_NAME = "monthlyAvailability";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonthlyAvailabilityService monthlyAvailabilityService;

    private final MonthlyAvailabilityRepository monthlyAvailabilityRepository;

    public MonthlyAvailabilityResource(
        MonthlyAvailabilityService monthlyAvailabilityService,
        MonthlyAvailabilityRepository monthlyAvailabilityRepository
    ) {
        this.monthlyAvailabilityService = monthlyAvailabilityService;
        this.monthlyAvailabilityRepository = monthlyAvailabilityRepository;
    }

    /**
     * {@code POST  /monthly-availabilities} : Create a new monthlyAvailability.
     *
     * @param monthlyAvailabilityDTO the monthlyAvailabilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monthlyAvailabilityDTO, or with status {@code 400 (Bad Request)} if the monthlyAvailability has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monthly-availabilities")
    public ResponseEntity<MonthlyAvailabilityDTO> createMonthlyAvailability(
        @Valid @RequestBody MonthlyAvailabilityDTO monthlyAvailabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MonthlyAvailability : {}", monthlyAvailabilityDTO);
        if (monthlyAvailabilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new monthlyAvailability cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MonthlyAvailabilityDTO result = monthlyAvailabilityService.save(monthlyAvailabilityDTO);
        return ResponseEntity
            .created(new URI("/api/monthly-availabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monthly-availabilities/:id} : Updates an existing monthlyAvailability.
     *
     * @param id the id of the monthlyAvailabilityDTO to save.
     * @param monthlyAvailabilityDTO the monthlyAvailabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyAvailabilityDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyAvailabilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monthlyAvailabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monthly-availabilities/{id}")
    public ResponseEntity<MonthlyAvailabilityDTO> updateMonthlyAvailability(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MonthlyAvailabilityDTO monthlyAvailabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MonthlyAvailability : {}, {}", id, monthlyAvailabilityDTO);
        if (monthlyAvailabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyAvailabilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyAvailabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MonthlyAvailabilityDTO result = monthlyAvailabilityService.save(monthlyAvailabilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monthlyAvailabilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /monthly-availabilities/:id} : Partial updates given fields of an existing monthlyAvailability, field will ignore if it is null
     *
     * @param id the id of the monthlyAvailabilityDTO to save.
     * @param monthlyAvailabilityDTO the monthlyAvailabilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monthlyAvailabilityDTO,
     * or with status {@code 400 (Bad Request)} if the monthlyAvailabilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the monthlyAvailabilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the monthlyAvailabilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/monthly-availabilities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MonthlyAvailabilityDTO> partialUpdateMonthlyAvailability(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MonthlyAvailabilityDTO monthlyAvailabilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MonthlyAvailability partially : {}, {}", id, monthlyAvailabilityDTO);
        if (monthlyAvailabilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, monthlyAvailabilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!monthlyAvailabilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MonthlyAvailabilityDTO> result = monthlyAvailabilityService.partialUpdate(monthlyAvailabilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, monthlyAvailabilityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /monthly-availabilities} : get all the monthlyAvailabilities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monthlyAvailabilities in body.
     */
    @GetMapping("/monthly-availabilities")
    public List<MonthlyAvailabilityDTO> getAllMonthlyAvailabilities() {
        log.debug("REST request to get all MonthlyAvailabilities");
        return monthlyAvailabilityService.findAll();
    }

    /**
     * {@code GET  /monthly-availabilities/:id} : get the "id" monthlyAvailability.
     *
     * @param id the id of the monthlyAvailabilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monthlyAvailabilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monthly-availabilities/{id}")
    public ResponseEntity<MonthlyAvailabilityDTO> getMonthlyAvailability(@PathVariable Long id) {
        log.debug("REST request to get MonthlyAvailability : {}", id);
        Optional<MonthlyAvailabilityDTO> monthlyAvailabilityDTO = monthlyAvailabilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monthlyAvailabilityDTO);
    }

    /**
     * {@code DELETE  /monthly-availabilities/:id} : delete the "id" monthlyAvailability.
     *
     * @param id the id of the monthlyAvailabilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monthly-availabilities/{id}")
    public ResponseEntity<Void> deleteMonthlyAvailability(@PathVariable Long id) {
        log.debug("REST request to delete MonthlyAvailability : {}", id);
        monthlyAvailabilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
