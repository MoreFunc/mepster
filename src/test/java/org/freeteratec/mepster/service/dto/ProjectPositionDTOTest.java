package org.freeteratec.mepster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectPositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectPositionDTO.class);
        ProjectPositionDTO projectPositionDTO1 = new ProjectPositionDTO();
        projectPositionDTO1.setId(1L);
        ProjectPositionDTO projectPositionDTO2 = new ProjectPositionDTO();
        assertThat(projectPositionDTO1).isNotEqualTo(projectPositionDTO2);
        projectPositionDTO2.setId(projectPositionDTO1.getId());
        assertThat(projectPositionDTO1).isEqualTo(projectPositionDTO2);
        projectPositionDTO2.setId(2L);
        assertThat(projectPositionDTO1).isNotEqualTo(projectPositionDTO2);
        projectPositionDTO1.setId(null);
        assertThat(projectPositionDTO1).isNotEqualTo(projectPositionDTO2);
    }
}
