package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.MonthlyAvailability;
import org.freeteratec.mepster.service.dto.MonthlyAvailabilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonthlyAvailability} and its DTO {@link MonthlyAvailabilityDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonMapper.class })
public interface MonthlyAvailabilityMapper extends EntityMapper<MonthlyAvailabilityDTO, MonthlyAvailability> {
    @Mapping(target = "person", source = "person", qualifiedByName = "lastname")
    MonthlyAvailabilityDTO toDto(MonthlyAvailability s);
}
