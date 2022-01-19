package org.freeteratec.mepster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.freeteratec.mepster.domain.ProjectPosition} entity.
 */
public class ProjectPositionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    private String title;

    @Lob
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Min(value = 0)
    @Max(value = 100)
    private Integer percent;

    private RoleDTO role;

    private Set<SkillDTO> skills = new HashSet<>();

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public Set<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectPositionDTO)) {
            return false;
        }

        ProjectPositionDTO projectPositionDTO = (ProjectPositionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectPositionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPositionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", percent=" + getPercent() +
            ", role=" + getRole() +
            ", skills=" + getSkills() +
            ", project=" + getProject() +
            "}";
    }
}
