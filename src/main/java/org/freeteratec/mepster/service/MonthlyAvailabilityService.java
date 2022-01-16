package org.freeteratec.mepster.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.freeteratec.mepster.domain.MonthlyAvailability;
import org.freeteratec.mepster.repository.MonthlyAvailabilityRepository;
import org.freeteratec.mepster.service.dto.MonthlyAvailabilityDTO;
import org.freeteratec.mepster.service.mapper.MonthlyAvailabilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonthlyAvailability}.
 */
@Service
@Transactional
public class MonthlyAvailabilityService {

    private final Logger log = LoggerFactory.getLogger(MonthlyAvailabilityService.class);

    private final MonthlyAvailabilityRepository monthlyAvailabilityRepository;

    private final MonthlyAvailabilityMapper monthlyAvailabilityMapper;

    public MonthlyAvailabilityService(
        MonthlyAvailabilityRepository monthlyAvailabilityRepository,
        MonthlyAvailabilityMapper monthlyAvailabilityMapper
    ) {
        this.monthlyAvailabilityRepository = monthlyAvailabilityRepository;
        this.monthlyAvailabilityMapper = monthlyAvailabilityMapper;
    }

    /**
     * Save a monthlyAvailability.
     *
     * @param monthlyAvailabilityDTO the entity to save.
     * @return the persisted entity.
     */
    public MonthlyAvailabilityDTO save(MonthlyAvailabilityDTO monthlyAvailabilityDTO) {
        log.debug("Request to save MonthlyAvailability : {}", monthlyAvailabilityDTO);
        MonthlyAvailability monthlyAvailability = monthlyAvailabilityMapper.toEntity(monthlyAvailabilityDTO);
        monthlyAvailability = monthlyAvailabilityRepository.save(monthlyAvailability);
        return monthlyAvailabilityMapper.toDto(monthlyAvailability);
    }

    /**
     * Partially update a monthlyAvailability.
     *
     * @param monthlyAvailabilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MonthlyAvailabilityDTO> partialUpdate(MonthlyAvailabilityDTO monthlyAvailabilityDTO) {
        log.debug("Request to partially update MonthlyAvailability : {}", monthlyAvailabilityDTO);

        return monthlyAvailabilityRepository
            .findById(monthlyAvailabilityDTO.getId())
            .map(existingMonthlyAvailability -> {
                monthlyAvailabilityMapper.partialUpdate(existingMonthlyAvailability, monthlyAvailabilityDTO);

                return existingMonthlyAvailability;
            })
            .map(monthlyAvailabilityRepository::save)
            .map(monthlyAvailabilityMapper::toDto);
    }

    /**
     * Get all the monthlyAvailabilities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlyAvailabilityDTO> findAll() {
        log.debug("Request to get all MonthlyAvailabilities");
        return monthlyAvailabilityRepository
            .findAll()
            .stream()
            .map(monthlyAvailabilityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one monthlyAvailability by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonthlyAvailabilityDTO> findOne(Long id) {
        log.debug("Request to get MonthlyAvailability : {}", id);
        return monthlyAvailabilityRepository.findById(id).map(monthlyAvailabilityMapper::toDto);
    }

    /**
     * Delete the monthlyAvailability by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlyAvailability : {}", id);
        monthlyAvailabilityRepository.deleteById(id);
    }
}
