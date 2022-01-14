package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.ProjectPosition;
import org.freeteratec.mepster.service.dto.ProjectPositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectPosition} and its DTO {@link ProjectPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = { RoleMapper.class, ProjectMapper.class })
public interface ProjectPositionMapper extends EntityMapper<ProjectPositionDTO, ProjectPosition> {
    @Mapping(target = "role", source = "role", qualifiedByName = "title")
    @Mapping(target = "project", source = "project", qualifiedByName = "title")
    ProjectPositionDTO toDto(ProjectPosition s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectPositionDTO toDtoId(ProjectPosition projectPosition);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ProjectPositionDTO toDtoTitle(ProjectPosition projectPosition);
}
