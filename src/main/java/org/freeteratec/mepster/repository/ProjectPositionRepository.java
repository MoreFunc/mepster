package org.freeteratec.mepster.repository;

import java.util.List;
import java.util.Optional;
import org.freeteratec.mepster.domain.ProjectPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectPosition entity.
 */
@Repository
public interface ProjectPositionRepository extends JpaRepository<ProjectPosition, Long> {
    @Query(
        value = "select distinct projectPosition from ProjectPosition projectPosition left join fetch projectPosition.skills",
        countQuery = "select count(distinct projectPosition) from ProjectPosition projectPosition"
    )
    Page<ProjectPosition> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct projectPosition from ProjectPosition projectPosition left join fetch projectPosition.skills")
    List<ProjectPosition> findAllWithEagerRelationships();

    @Query(
        "select projectPosition from ProjectPosition projectPosition left join fetch projectPosition.skills where projectPosition.id =:id"
    )
    Optional<ProjectPosition> findOneWithEagerRelationships(@Param("id") Long id);
}
