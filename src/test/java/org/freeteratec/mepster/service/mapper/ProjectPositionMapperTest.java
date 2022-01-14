package org.freeteratec.mepster.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectPositionMapperTest {

    private ProjectPositionMapper projectPositionMapper;

    @BeforeEach
    public void setUp() {
        projectPositionMapper = new ProjectPositionMapperImpl();
    }
}
