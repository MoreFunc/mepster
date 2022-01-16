package org.freeteratec.mepster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyAvailabilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyAvailability.class);
        MonthlyAvailability monthlyAvailability1 = new MonthlyAvailability();
        monthlyAvailability1.setId(1L);
        MonthlyAvailability monthlyAvailability2 = new MonthlyAvailability();
        monthlyAvailability2.setId(monthlyAvailability1.getId());
        assertThat(monthlyAvailability1).isEqualTo(monthlyAvailability2);
        monthlyAvailability2.setId(2L);
        assertThat(monthlyAvailability1).isNotEqualTo(monthlyAvailability2);
        monthlyAvailability1.setId(null);
        assertThat(monthlyAvailability1).isNotEqualTo(monthlyAvailability2);
    }
}
