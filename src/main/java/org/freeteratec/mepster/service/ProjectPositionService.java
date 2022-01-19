package org.freeteratec.mepster.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.freeteratec.mepster.domain.ProjectPosition;
import org.freeteratec.mepster.repository.ProjectPositionRepository;
import org.freeteratec.mepster.service.dto.ProjectPositionDTO;
import org.freeteratec.mepster.service.mapper.ProjectPositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectPosition}.
 */
@Service
@Transactional
public class ProjectPositionService {

    private final Logger log = LoggerFactory.getLogger(ProjectPositionService.class);

    private final ProjectPositionRepository projectPositionRepository;

    private final ProjectPositionMapper projectPositionMapper;

    public ProjectPositionService(ProjectPositionRepository projectPositionRepository, ProjectPositionMapper projectPositionMapper) {
        this.projectPositionRepository = projectPositionRepository;
        this.projectPositionMapper = projectPositionMapper;
    }

    /**
     * Save a projectPosition.
     *
     * @param projectPositionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectPositionDTO save(ProjectPositionDTO projectPositionDTO) {
        log.debug("Request to save ProjectPosition : {}", projectPositionDTO);
        ProjectPosition projectPosition = projectPositionMapper.toEntity(projectPositionDTO);
        projectPosition = projectPositionRepository.save(projectPosition);
        return projectPositionMapper.toDto(projectPosition);
    }

    /**
     * Partially update a projectPosition.
     *
     * @param projectPositionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProjectPositionDTO> partialUpdate(ProjectPositionDTO projectPositionDTO) {
        log.debug("Request to partially update ProjectPosition : {}", projectPositionDTO);

        return projectPositionRepository
            .findById(projectPositionDTO.getId())
            .map(existingProjectPosition -> {
                projectPositionMapper.partialUpdate(existingProjectPosition, projectPositionDTO);

                return existingProjectPosition;
            })
            .map(projectPositionRepository::save)
            .map(projectPositionMapper::toDto);
    }

    /**
     * Get all the projectPositions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectPositionDTO> findAll() {
        log.debug("Request to get all ProjectPositions");
        return projectPositionRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(projectPositionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the projectPositions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProjectPositionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return projectPositionRepository.findAllWithEagerRelationships(pageable).map(projectPositionMapper::toDto);
    }

    /**
     * Get one projectPosition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectPositionDTO> findOne(Long id) {
        log.debug("Request to get ProjectPosition : {}", id);
        return projectPositionRepository.findOneWithEagerRelationships(id).map(projectPositionMapper::toDto);
    }

    /**
     * Delete the projectPosition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectPosition : {}", id);
        projectPositionRepository.deleteById(id);
    }
}
