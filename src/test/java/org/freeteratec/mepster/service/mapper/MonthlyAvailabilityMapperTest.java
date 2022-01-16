package org.freeteratec.mepster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MonthlyAvailabilityMapperTest {

    private MonthlyAvailabilityMapper monthlyAvailabilityMapper;

    @BeforeEach
    public void setUp() {
        monthlyAvailabilityMapper = new MonthlyAvailabilityMapperImpl();
    }
}
