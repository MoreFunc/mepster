package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.Project;
import org.freeteratec.mepster.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganizationMapper.class })
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "name")
    ProjectDTO toDto(Project s);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ProjectDTO toDtoTitle(Project project);
}
