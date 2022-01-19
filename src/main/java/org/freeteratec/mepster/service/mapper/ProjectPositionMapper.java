package org.freeteratec.mepster.service.mapper;

import java.util.Set;
import org.freeteratec.mepster.domain.ProjectPosition;
import org.freeteratec.mepster.service.dto.ProjectPositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectPosition} and its DTO {@link ProjectPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class, SkillMapper.class, ProjectMapper.class })
public interface ProjectPositionMapper extends EntityMapper<ProjectPositionDTO, ProjectPosition> {
    @Mapping(target = "role", source = "role", qualifiedByName = "title")
    @Mapping(target = "skills", source = "skills", qualifiedByName = "titleSet")
    @Mapping(target = "project", source = "project", qualifiedByName = "title")
    ProjectPositionDTO toDto(ProjectPosition s);

    @Mapping(target = "removeSkills", ignore = true)
    ProjectPosition toEntity(ProjectPositionDTO projectPositionDTO);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ProjectPositionDTO toDtoTitle(ProjectPosition projectPosition);
}
