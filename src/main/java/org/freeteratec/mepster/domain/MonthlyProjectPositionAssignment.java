package org.freeteratec.mepster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MonthlyProjectPositionAssignment.
 */
@Entity
@Table(name = "monthly_position_assignment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonthlyProjectPositionAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "yearmonth")
    private LocalDate yearmonth;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "percent")
    private Integer percent;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "role", "skills", "project", "monthlyAssignments" }, allowSetters = true)
    private ProjectPosition projectPosition;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "skills", "organization", "monthlyAssignments" }, allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MonthlyProjectPositionAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getYearmonth() {
        return this.yearmonth;
    }

    public MonthlyProjectPositionAssignment yearmonth(LocalDate yearmonth) {
        this.setYearmonth(yearmonth);
        return this;
    }

    public void setYearmonth(LocalDate yearmonth) {
        this.yearmonth = yearmonth;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public MonthlyProjectPositionAssignment percent(Integer percent) {
        this.setPercent(percent);
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Boolean getActive() {
        return this.active;
    }

    public MonthlyProjectPositionAssignment active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProjectPosition getProjectPosition() {
        return this.projectPosition;
    }

    public void setProjectPosition(ProjectPosition projectPosition) {
        this.projectPosition = projectPosition;
    }

    public MonthlyProjectPositionAssignment projectPosition(ProjectPosition projectPosition) {
        this.setProjectPosition(projectPosition);
        return this;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public MonthlyProjectPositionAssignment person(Person person) {
        this.setPerson(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonthlyProjectPositionAssignment)) {
            return false;
        }
        return id != null && id.equals(((MonthlyProjectPositionAssignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonthlyProjectPositionAssignment{" +
            "id=" + getId() +
            ", yearmonth='" + getYearmonth() + "'" +
            ", percent=" + getPercent() +
            ", active='" + getActive() + "'" +
            "}";
    }
}
