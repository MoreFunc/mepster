package org.freeteratec.mepster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyProjectPositionAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyProjectPositionAssignment.class);
        MonthlyProjectPositionAssignment monthlyProjectPositionAssignment1 = new MonthlyProjectPositionAssignment();
        monthlyProjectPositionAssignment1.setId(1L);
        MonthlyProjectPositionAssignment monthlyProjectPositionAssignment2 = new MonthlyProjectPositionAssignment();
        monthlyProjectPositionAssignment2.setId(monthlyProjectPositionAssignment1.getId());
        assertThat(monthlyProjectPositionAssignment1).isEqualTo(monthlyProjectPositionAssignment2);
        monthlyProjectPositionAssignment2.setId(2L);
        assertThat(monthlyProjectPositionAssignment1).isNotEqualTo(monthlyProjectPositionAssignment2);
        monthlyProjectPositionAssignment1.setId(null);
        assertThat(monthlyProjectPositionAssignment1).isNotEqualTo(monthlyProjectPositionAssignment2);
    }
}
