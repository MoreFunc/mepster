package org.freeteratec.mepster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "skills", "project", "monthlyAssignments" }, allowSetters = true)
    private Set<ProjectPosition> projectPositions = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "roles", "organization", "monthlyAssignments", "monthlyAvailabilities" }, allowSetters = true)
    private Set<Person> persons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Skill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Skill title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Skill description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectPosition> getProjectPositions() {
        return this.projectPositions;
    }

    public void setProjectPositions(Set<ProjectPosition> projectPositions) {
        if (this.projectPositions != null) {
            this.projectPositions.forEach(i -> i.removeSkills(this));
        }
        if (projectPositions != null) {
            projectPositions.forEach(i -> i.addSkills(this));
        }
        this.projectPositions = projectPositions;
    }

    public Skill projectPositions(Set<ProjectPosition> projectPositions) {
        this.setProjectPositions(projectPositions);
        return this;
    }

    public Skill addProjectPositions(ProjectPosition projectPosition) {
        this.projectPositions.add(projectPosition);
        projectPosition.getSkills().add(this);
        return this;
    }

    public Skill removeProjectPositions(ProjectPosition projectPosition) {
        this.projectPositions.remove(projectPosition);
        projectPosition.getSkills().remove(this);
        return this;
    }

    public Set<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(Set<Person> people) {
        if (this.persons != null) {
            this.persons.forEach(i -> i.removeSkills(this));
        }
        if (people != null) {
            people.forEach(i -> i.addSkills(this));
        }
        this.persons = people;
    }

    public Skill persons(Set<Person> people) {
        this.setPersons(people);
        return this;
    }

    public Skill addPersons(Person person) {
        this.persons.add(person);
        person.getSkills().add(this);
        return this;
    }

    public Skill removePersons(Person person) {
        this.persons.remove(person);
        person.getSkills().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
