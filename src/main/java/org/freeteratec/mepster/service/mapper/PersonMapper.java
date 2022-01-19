package org.freeteratec.mepster.service.mapper;

import java.util.Set;
import org.freeteratec.mepster.domain.Person;
import org.freeteratec.mepster.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = { SkillMapper.class, RoleMapper.class, OrganizationMapper.class })
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "skills", source = "skills", qualifiedByName = "titleSet")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "titleSet")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "name")
    PersonDTO toDto(Person s);

    @Mapping(target = "removeSkills", ignore = true)
    @Mapping(target = "removeRoles", ignore = true)
    Person toEntity(PersonDTO personDTO);

    @Named("lastname")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastname", source = "lastname")
    PersonDTO toDtoLastname(Person person);
}
