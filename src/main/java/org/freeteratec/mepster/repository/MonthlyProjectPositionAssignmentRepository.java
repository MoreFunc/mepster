package org.freeteratec.mepster.repository;

import org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MonthlyProjectPositionAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyProjectPositionAssignmentRepository extends JpaRepository<MonthlyProjectPositionAssignment, Long> {}
