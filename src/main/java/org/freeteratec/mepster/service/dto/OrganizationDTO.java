package org.freeteratec.mepster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.freeteratec.mepster.domain.Organization} entity.
 */
public class OrganizationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @Size(max = 50)
    private String street;

    @Size(max = 10)
    private String number;

    @Size(max = 20)
    @Pattern(regexp = "^[A-Z][A-Za-z/-]*$")
    private String city;

    @Size(max = 8)
    @Pattern(regexp = "^[0-9]{5}$")
    private String zipcode;

    @Size(max = 20)
    @Pattern(regexp = "^[A-Za-z-]*$")
    private String country;

    @Size(max = 30)
    @Pattern(regexp = "^[0-9 +-]*$")
    private String phoneNumber;

    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String email;

    @Size(max = 50)
    @Pattern(regexp = "^https?:\\/\\/[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String website;

    @Lob
    private String notes;

    private OrganizationDTO parentOrganization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OrganizationDTO getParentOrganization() {
        return parentOrganization;
    }

    public void setParentOrganization(OrganizationDTO parentOrganization) {
        this.parentOrganization = parentOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationDTO)) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", street='" + getStreet() + "'" +
            ", number='" + getNumber() + "'" +
            ", city='" + getCity() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", country='" + getCountry() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", notes='" + getNotes() + "'" +
            ", parentOrganization=" + getParentOrganization() +
            "}";
    }
}
