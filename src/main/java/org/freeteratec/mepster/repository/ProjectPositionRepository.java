package org.freeteratec.mepster.repository;

import org.freeteratec.mepster.domain.ProjectPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectPositionRepository extends JpaRepository<ProjectPosition, Long> {}
