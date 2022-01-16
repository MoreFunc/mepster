package org.freeteratec.mepster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.freeteratec.mepster.domain.MonthlyAvailability} entity.
 */
public class MonthlyAvailabilityDTO implements Serializable {

    private Long id;

    private LocalDate yearmonth;

    @Min(value = 0)
    @Max(value = 100)
    private Integer percent;

    @NotNull
    private Boolean isActive;

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
        if (!(o instanceof MonthlyAvailabilityDTO)) {
            return false;
        }

        MonthlyAvailabilityDTO monthlyAvailabilityDTO = (MonthlyAvailabilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monthlyAvailabilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyAvailabilityDTO{" +
            "id=" + getId() +
            ", yearmonth='" + getYearmonth() + "'" +
            ", percent=" + getPercent() +
            ", isActive='" + getIsActive() + "'" +
            ", person=" + getPerson() +
            "}";
    }
}
