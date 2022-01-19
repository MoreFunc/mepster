package org.freeteratec.mepster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "title", length = 40, nullable = false)
    private String title;

    @OneToMany(mappedBy = "role")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "skills", "project", "monthlyAssignments" }, allowSetters = true)
    private Set<ProjectPosition> projectPositions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "roles", "organization", "monthlyAssignments", "monthlyAvailabilities" }, allowSetters = true)
    private Set<Person> persons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Role id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Role title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ProjectPosition> getProjectPositions() {
        return this.projectPositions;
    }

    public void setProjectPositions(Set<ProjectPosition> projectPositions) {
        if (this.projectPositions != null) {
            this.projectPositions.forEach(i -> i.setRole(null));
        }
        if (projectPositions != null) {
            projectPositions.forEach(i -> i.setRole(this));
        }
        this.projectPositions = projectPositions;
    }

    public Role projectPositions(Set<ProjectPosition> projectPositions) {
        this.setProjectPositions(projectPositions);
        return this;
    }

    public Role addProjectPosition(ProjectPosition projectPosition) {
        this.projectPositions.add(projectPosition);
        projectPosition.setRole(this);
        return this;
    }

    public Role removeProjectPosition(ProjectPosition projectPosition) {
        this.projectPositions.remove(projectPosition);
        projectPosition.setRole(null);
        return this;
    }

    public Set<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(Set<Person> people) {
        if (this.persons != null) {
            this.persons.forEach(i -> i.removeRoles(this));
        }
        if (people != null) {
            people.forEach(i -> i.addRoles(this));
        }
        this.persons = people;
    }

    public Role persons(Set<Person> people) {
        this.setPersons(people);
        return this;
    }

    public Role addPersons(Person person) {
        this.persons.add(person);
        person.getRoles().add(this);
        return this;
    }

    public Role removePersons(Person person) {
        this.persons.remove(person);
        person.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
