package org.freeteratec.mepster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MonthlyProjectPositionAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MonthlyProjectPositionAssignmentDTO.class);
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO1 = new MonthlyProjectPositionAssignmentDTO();
        monthlyProjectPositionAssignmentDTO1.setId(1L);
        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO2 = new MonthlyProjectPositionAssignmentDTO();
        assertThat(monthlyProjectPositionAssignmentDTO1).isNotEqualTo(monthlyProjectPositionAssignmentDTO2);
        monthlyProjectPositionAssignmentDTO2.setId(monthlyProjectPositionAssignmentDTO1.getId());
        assertThat(monthlyProjectPositionAssignmentDTO1).isEqualTo(monthlyProjectPositionAssignmentDTO2);
        monthlyProjectPositionAssignmentDTO2.setId(2L);
        assertThat(monthlyProjectPositionAssignmentDTO1).isNotEqualTo(monthlyProjectPositionAssignmentDTO2);
        monthlyProjectPositionAssignmentDTO1.setId(null);
        assertThat(monthlyProjectPositionAssignmentDTO1).isNotEqualTo(monthlyProjectPositionAssignmentDTO2);
    }
}
