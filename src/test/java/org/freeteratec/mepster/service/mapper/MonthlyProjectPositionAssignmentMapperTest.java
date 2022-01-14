package org.freeteratec.mepster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonthlyProjectPositionAssignmentMapperTest {

    private MonthlyProjectPositionAssignmentMapper monthlyProjectPositionAssignmentMapper;

    @BeforeEach
    public void setUp() {
        monthlyProjectPositionAssignmentMapper = new MonthlyProjectPositionAssignmentMapperImpl();
    }
}
