package org.freeteratec.mepster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.freeteratec.mepster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectPosition.class);
        ProjectPosition projectPosition1 = new ProjectPosition();
        projectPosition1.setId(1L);
        ProjectPosition projectPosition2 = new ProjectPosition();
        projectPosition2.setId(projectPosition1.getId());
        assertThat(projectPosition1).isEqualTo(projectPosition2);
        projectPosition2.setId(2L);
        assertThat(projectPosition1).isNotEqualTo(projectPosition2);
        projectPosition1.setId(null);
        assertThat(projectPosition1).isNotEqualTo(projectPosition2);
    }
}
