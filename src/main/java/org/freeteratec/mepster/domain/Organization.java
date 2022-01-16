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
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 50)
    @Column(name = "street", length = 50)
    private String street;

    @Size(max = 5)
    @Column(name = "number", length = 5)
    private String number;

    @Size(max = 20)
    @Column(name = "city", length = 20)
    private String city;

    @Size(max = 5)
    @Pattern(regexp = "^[0-9]{5}$")
    @Column(name = "zipcode", length = 5)
    private String zipcode;

    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z-]*$")
    @Column(name = "country", length = 20)
    private String country;

    @Size(max = 20)
    @Pattern(regexp = "^[0-9 +-]*$")
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    @Column(name = "email", length = 30)
    private String email;

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "organization", "monthlyAssignments" }, allowSetters = true)
    private Set<Person> persons = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projectPositions", "organization" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Organization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return this.street;
    }

    public Organization street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return this.number;
    }

    public Organization number(String number) {
        this.setNumber(number);
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return this.city;
    }

    public Organization city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public Organization zipcode(String zipcode) {
        this.setZipcode(zipcode);
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return this.country;
    }

    public Organization country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return this.phone;
    }

    public Organization phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Organization email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(Set<Person> people) {
        if (this.persons != null) {
            this.persons.forEach(i -> i.setOrganization(null));
        }
        if (people != null) {
            people.forEach(i -> i.setOrganization(this));
        }
        this.persons = people;
    }

    public Organization persons(Set<Person> people) {
        this.setPersons(people);
        return this;
    }

    public Organization addPersons(Person person) {
        this.persons.add(person);
        person.setOrganization(this);
        return this;
    }

    public Organization removePersons(Person person) {
        this.persons.remove(person);
        person.setOrganization(null);
        return this;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setOrganization(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setOrganization(this));
        }
        this.projects = projects;
    }

    public Organization projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Organization addProjects(Project project) {
        this.projects.add(project);
        project.setOrganization(this);
        return this;
    }

    public Organization removeProjects(Project project) {
        this.projects.remove(project);
        project.setOrganization(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", street='" + getStreet() + "'" +
            ", number='" + getNumber() + "'" +
            ", city='" + getCity() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", country='" + getCountry() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
