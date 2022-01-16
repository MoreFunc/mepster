package org.freeteratec.mepster.repository;

import org.freeteratec.mepster.domain.MonthlyAvailability;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MonthlyAvailability entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonthlyAvailabilityRepository extends JpaRepository<MonthlyAvailability, Long> {}
