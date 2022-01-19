package org.freeteratec.mepster.service.mapper;

import java.util.Set;
import org.freeteratec.mepster.domain.Skill;
import org.freeteratec.mepster.service.dto.SkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Skill} and its DTO {@link SkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillMapper extends EntityMapper<SkillDTO, Skill> {
    @Named("titleSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    Set<SkillDTO> toDtoTitleSet(Set<Skill> skill);
}
