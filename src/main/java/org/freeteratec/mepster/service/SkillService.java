package org.freeteratec.mepster.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.freeteratec.mepster.domain.Skill;
import org.freeteratec.mepster.repository.SkillRepository;
import org.freeteratec.mepster.service.dto.SkillDTO;
import org.freeteratec.mepster.service.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
@Transactional
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save.
     * @return the persisted entity.
     */
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    /**
     * Partially update a skill.
     *
     * @param skillDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SkillDTO> partialUpdate(SkillDTO skillDTO) {
        log.debug("Request to partially update Skill : {}", skillDTO);

        return skillRepository
            .findById(skillDTO.getId())
            .map(existingSkill -> {
                skillMapper.partialUpdate(existingSkill, skillDTO);

                return existingSkill;
            })
            .map(skillRepository::save)
            .map(skillMapper::toDto);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SkillDTO> findAll() {
        log.debug("Request to get all Skills");
        return skillRepository.findAll().stream().map(skillMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one skill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SkillDTO> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id).map(skillMapper::toDto);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
