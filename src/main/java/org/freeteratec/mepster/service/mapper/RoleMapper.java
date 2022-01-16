package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.Role;
import org.freeteratec.mepster.service.dto.RoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonMapper.class })
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "person", source = "person", qualifiedByName = "id")
    RoleDTO toDto(Role s);

    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    RoleDTO toDtoTitle(Role role);
}
