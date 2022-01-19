package org.freeteratec.mepster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A ProjectPosition.
 */
@Entity
@Table(name = "project_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "percent")
    private Integer percent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectPositions", "persons" }, allowSetters = true)
    private Role role;

    @ManyToMany
    @JoinTable(
        name = "rel_project_position__skills",
        joinColumns = @JoinColumn(name = "project_position_id"),
        inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPositions", "persons" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "projectPositions", "organization" }, allowSetters = true)
    private Project project;

    @OneToMany(mappedBy = "projectPosition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPosition", "person" }, allowSetters = true)
    private Set<MonthlyProjectPositionAssignment> monthlyAssignments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProjectPosition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public ProjectPosition title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public ProjectPosition description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public ProjectPosition startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public ProjectPosition endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public ProjectPosition percent(Integer percent) {
        this.setPercent(percent);
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ProjectPosition role(Role role) {
        this.setRole(role);
        return this;
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public ProjectPosition skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public ProjectPosition addSkills(Skill skill) {
        this.skills.add(skill);
        skill.getProjectPositions().add(this);
        return this;
    }

    public ProjectPosition removeSkills(Skill skill) {
        this.skills.remove(skill);
        skill.getProjectPositions().remove(this);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ProjectPosition project(Project project) {
        this.setProject(project);
        return this;
    }

    public Set<MonthlyProjectPositionAssignment> getMonthlyAssignments() {
        return this.monthlyAssignments;
    }

    public void setMonthlyAssignments(Set<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignments) {
        if (this.monthlyAssignments != null) {
            this.monthlyAssignments.forEach(i -> i.setProjectPosition(null));
        }
        if (monthlyProjectPositionAssignments != null) {
            monthlyProjectPositionAssignments.forEach(i -> i.setProjectPosition(this));
        }
        this.monthlyAssignments = monthlyProjectPositionAssignments;
    }

    public ProjectPosition monthlyAssignments(Set<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignments) {
        this.setMonthlyAssignments(monthlyProjectPositionAssignments);
        return this;
    }

    public ProjectPosition addMonthlyAssignments(MonthlyProjectPositionAssignment monthlyProjectPositionAssignment) {
        this.monthlyAssignments.add(monthlyProjectPositionAssignment);
        monthlyProjectPositionAssignment.setProjectPosition(this);
        return this;
    }

    public ProjectPosition removeMonthlyAssignments(MonthlyProjectPositionAssignment monthlyProjectPositionAssignment) {
        this.monthlyAssignments.remove(monthlyProjectPositionAssignment);
        monthlyProjectPositionAssignment.setProjectPosition(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectPosition)) {
            return false;
        }
        return id != null && id.equals(((ProjectPosition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPosition{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", percent=" + getPercent() +
            "}";
    }
}
