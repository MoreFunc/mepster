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

    @Column(name = "type")
    private String type;

    @Column(name = "lead")
    private String lead;

    @Size(max = 30)
    @Pattern(regexp = "^[0-9 +-]*$")
    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    @Column(name = "email", length = 30)
    private String email;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPosition", "person" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPosition", "person" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "persons", "projects", "subOrganizations", "parentOrganization" }, allowSetters = true)
    private Organization organization;

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPosition", "person" }, allowSetters = true)
    private Set<MonthlyProjectPositionAssignment> monthlyAssignments = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "person" }, allowSetters = true)
    private Set<MonthlyAvailability> monthlyAvailabilities = new HashSet<>();

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

    public String getType() {
        return this.type;
    }

    public Person type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLead() {
        return this.lead;
    }

    public Person lead(String lead) {
        this.setLead(lead);
        return this;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Person email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setPerson(null));
        }
        if (skills != null) {
            skills.forEach(i -> i.setPerson(this));
        }
        this.skills = skills;
    }

    public Person skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public Person addSkills(Skill skill) {
        this.skills.add(skill);
        skill.setPerson(this);
        return this;
    }

    public Person removeSkills(Skill skill) {
        this.skills.remove(skill);
        skill.setPerson(null);
        return this;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.setPerson(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setPerson(this));
        }
        this.roles = roles;
    }

    public Person roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Person addRoles(Role role) {
        this.roles.add(role);
        role.setPerson(this);
        return this;
    }

    public Person removeRoles(Role role) {
        this.roles.remove(role);
        role.setPerson(null);
        return this;
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

    public Set<MonthlyAvailability> getMonthlyAvailabilities() {
        return this.monthlyAvailabilities;
    }

    public void setMonthlyAvailabilities(Set<MonthlyAvailability> monthlyAvailabilities) {
        if (this.monthlyAvailabilities != null) {
            this.monthlyAvailabilities.forEach(i -> i.setPerson(null));
        }
        if (monthlyAvailabilities != null) {
            monthlyAvailabilities.forEach(i -> i.setPerson(this));
        }
        this.monthlyAvailabilities = monthlyAvailabilities;
    }

    public Person monthlyAvailabilities(Set<MonthlyAvailability> monthlyAvailabilities) {
        this.setMonthlyAvailabilities(monthlyAvailabilities);
        return this;
    }

    public Person addMonthlyAvailabilities(MonthlyAvailability monthlyAvailability) {
        this.monthlyAvailabilities.add(monthlyAvailability);
        monthlyAvailability.setPerson(this);
        return this;
    }

    public Person removeMonthlyAvailabilities(MonthlyAvailability monthlyAvailability) {
        this.monthlyAvailabilities.remove(monthlyAvailability);
        monthlyAvailability.setPerson(null);
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
            ", type='" + getType() + "'" +
            ", lead='" + getLead() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
