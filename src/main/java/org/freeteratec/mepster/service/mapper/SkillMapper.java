package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.Skill;
import org.freeteratec.mepster.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectPositionMapper.class, PersonMapper.class })
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
    @Mapping(target = "projectPosition", source = "projectPosition", qualifiedByName = "id")
    @Mapping(target = "person", source = "person", qualifiedByName = "id")
    SkillDTO toDto(Skill s);
}
