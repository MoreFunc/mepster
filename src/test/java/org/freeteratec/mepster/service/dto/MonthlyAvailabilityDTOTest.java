package org.freeteratec.mepster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyAvailabilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyAvailabilityDTO.class);
        MonthlyAvailabilityDTO monthlyAvailabilityDTO1 = new MonthlyAvailabilityDTO();
        monthlyAvailabilityDTO1.setId(1L);
        MonthlyAvailabilityDTO monthlyAvailabilityDTO2 = new MonthlyAvailabilityDTO();
        assertThat(monthlyAvailabilityDTO1).isNotEqualTo(monthlyAvailabilityDTO2);
        monthlyAvailabilityDTO2.setId(monthlyAvailabilityDTO1.getId());
        assertThat(monthlyAvailabilityDTO1).isEqualTo(monthlyAvailabilityDTO2);
        monthlyAvailabilityDTO2.setId(2L);
        assertThat(monthlyAvailabilityDTO1).isNotEqualTo(monthlyAvailabilityDTO2);
        monthlyAvailabilityDTO1.setId(null);
        assertThat(monthlyAvailabilityDTO1).isNotEqualTo(monthlyAvailabilityDTO2);
    }
}
