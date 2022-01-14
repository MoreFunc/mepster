package org.freeteratec.mepster.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment;
import org.freeteratec.mepster.repository.MonthlyProjectPositionAssignmentRepository;
import org.freeteratec.mepster.service.dto.MonthlyProjectPositionAssignmentDTO;
import org.freeteratec.mepster.service.mapper.MonthlyProjectPositionAssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MonthlyProjectPositionAssignment}.
 */
@Service
@Transactional
public class MonthlyProjectPositionAssignmentService {

    private final Logger log = LoggerFactory.getLogger(MonthlyProjectPositionAssignmentService.class);

    private final MonthlyProjectPositionAssignmentRepository monthlyProjectPositionAssignmentRepository;

    private final MonthlyProjectPositionAssignmentMapper monthlyProjectPositionAssignmentMapper;

    public MonthlyProjectPositionAssignmentService(
        MonthlyProjectPositionAssignmentRepository monthlyProjectPositionAssignmentRepository,
        MonthlyProjectPositionAssignmentMapper monthlyProjectPositionAssignmentMapper
    ) {
        this.monthlyProjectPositionAssignmentRepository = monthlyProjectPositionAssignmentRepository;
        this.monthlyProjectPositionAssignmentMapper = monthlyProjectPositionAssignmentMapper;
    }

    /**
     * Save a monthlyProjectPositionAssignment.
     *
     * @param monthlyProjectPositionAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public MonthlyProjectPositionAssignmentDTO save(MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO) {
        log.debug("Request to save MonthlyProjectPositionAssignment : {}", monthlyProjectPositionAssignmentDTO);
        MonthlyProjectPositionAssignment monthlyProjectPositionAssignment = monthlyProjectPositionAssignmentMapper.toEntity(
            monthlyProjectPositionAssignmentDTO
        );
        monthlyProjectPositionAssignment = monthlyProjectPositionAssignmentRepository.save(monthlyProjectPositionAssignment);
        return monthlyProjectPositionAssignmentMapper.toDto(monthlyProjectPositionAssignment);
    }

    /**
     * Partially update a monthlyProjectPositionAssignment.
     *
     * @param monthlyProjectPositionAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MonthlyProjectPositionAssignmentDTO> partialUpdate(
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO
    ) {
        log.debug("Request to partially update MonthlyProjectPositionAssignment : {}", monthlyProjectPositionAssignmentDTO);

        return monthlyProjectPositionAssignmentRepository
            .findById(monthlyProjectPositionAssignmentDTO.getId())
            .map(existingMonthlyProjectPositionAssignment -> {
                monthlyProjectPositionAssignmentMapper.partialUpdate(
                    existingMonthlyProjectPositionAssignment,
                    monthlyProjectPositionAssignmentDTO
                );

                return existingMonthlyProjectPositionAssignment;
            })
            .map(monthlyProjectPositionAssignmentRepository::save)
            .map(monthlyProjectPositionAssignmentMapper::toDto);
    }

    /**
     * Get all the monthlyProjectPositionAssignments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlyProjectPositionAssignmentDTO> findAll() {
        log.debug("Request to get all MonthlyProjectPositionAssignments");
        return monthlyProjectPositionAssignmentRepository
            .findAll()
            .stream()
            .map(monthlyProjectPositionAssignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one monthlyProjectPositionAssignment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MonthlyProjectPositionAssignmentDTO> findOne(Long id) {
        log.debug("Request to get MonthlyProjectPositionAssignment : {}", id);
        return monthlyProjectPositionAssignmentRepository.findById(id).map(monthlyProjectPositionAssignmentMapper::toDto);
    }

    /**
     * Delete the monthlyProjectPositionAssignment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MonthlyProjectPositionAssignment : {}", id);
        monthlyProjectPositionAssignmentRepository.deleteById(id);
    }
}
