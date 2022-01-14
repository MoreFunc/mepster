package org.freeteratec.mepster.service.mapper;

import org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment;
import org.freeteratec.mepster.service.dto.MonthlyProjectPositionAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MonthlyProjectPositionAssignment} and its DTO {@link MonthlyProjectPositionAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectPositionMapper.class, PersonMapper.class })
public interface MonthlyProjectPositionAssignmentMapper
    extends EntityMapper<MonthlyProjectPositionAssignmentDTO, MonthlyProjectPositionAssignment> {
    @Mapping(target = "projectPosition", source = "projectPosition", qualifiedByName = "title")
    @Mapping(target = "person", source = "person", qualifiedByName = "lastname")
    MonthlyProjectPositionAssignmentDTO toDto(MonthlyProjectPositionAssignment s);
}
