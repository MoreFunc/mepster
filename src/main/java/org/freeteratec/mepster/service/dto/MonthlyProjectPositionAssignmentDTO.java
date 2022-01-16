package org.freeteratec.mepster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.freeteratec.mepster.domain.MonthlyProjectPositionAssignment} entity.
 */
public class MonthlyProjectPositionAssignmentDTO implements Serializable {

    private Long id;

    private LocalDate yearmonth;

    @Min(value = 0)
    @Max(value = 100)
    private Integer percent;

    @NotNull
    private Boolean isActive;

    private ProjectPositionDTO projectPosition;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(LocalDate yearmonth) {
        this.yearmonth = yearmonth;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ProjectPositionDTO getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(ProjectPositionDTO projectPosition) {
        this.projectPosition = projectPosition;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyProjectPositionAssignmentDTO)) {
            return false;
        }

        MonthlyProjectPositionAssignmentDTO monthlyProjectPositionAssignmentDTO = (MonthlyProjectPositionAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monthlyProjectPositionAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyProjectPositionAssignmentDTO{" +
            "id=" + getId() +
            ", yearmonth='" + getYearmonth() + "'" +
            ", percent=" + getPercent() +
            ", isActive='" + getIsActive() + "'" +
            ", projectPosition=" + getProjectPosition() +
            ", person=" + getPerson() +
            "}";
    }
}
