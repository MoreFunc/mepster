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
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JsonIgnoreProperties(value = { "persons", "projects" }, allowSetters = true)
    private Organization organization;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPosition", "person" }, allowSetters = true)
    private Set<MonthlyProjectPositionAssignment> monthlyAssignments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Person firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Person lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNotes() {
        return this.notes;
    }

    public Person notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Person organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Set<MonthlyProjectPositionAssignment> getMonthlyAssignments() {
        return this.monthlyAssignments;
    }

    public void setMonthlyAssignments(Set<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignments) {
        if (this.monthlyAssignments != null) {
            this.monthlyAssignments.forEach(i -> i.setPerson(null));
        }
        if (monthlyProjectPositionAssignments != null) {
            monthlyProjectPositionAssignments.forEach(i -> i.setPerson(this));
        }
        this.monthlyAssignments = monthlyProjectPositionAssignments;
    }

    public Person monthlyAssignments(Set<MonthlyProjectPositionAssignment> monthlyProjectPositionAssignments) {
        this.setMonthlyAssignments(monthlyProjectPositionAssignments);
        return this;
    }

    public Person addMonthlyAssignments(MonthlyProjectPositionAssignment monthlyProjectPositionAssignment) {
        this.monthlyAssignments.add(monthlyProjectPositionAssignment);
        monthlyProjectPositionAssignment.setPerson(this);
        return this;
    }

    public Person removeMonthlyAssignments(MonthlyProjectPositionAssignment monthlyProjectPositionAssignment) {
        this.monthlyAssignments.remove(monthlyProjectPositionAssignment);
        monthlyProjectPositionAssignment.setPerson(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
