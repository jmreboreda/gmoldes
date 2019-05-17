/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmoldes.domain.person;

import gmoldes.domain.address.Address;

import java.time.LocalDate;


public class Person {

    private Integer personId;
    private String surnames;
    private String name;
    private String nifNie;
    private String numberAffiliationSocialSecurity;
    private LocalDate birthDate;
    private String civilState;
    private Address address;
    private Integer studyLevel;
    private String nationality;

    public Person() {
    }

    public Person(Integer personId,
                  String surnames,
                  String name,
                  String nifNie,
                  String numberAffiliationSocialSecurity,
                  LocalDate birthDate,
                  String civilState,
                  Address address,
                  Integer studyLevel,
                  String nationality) {

        this.personId = personId;
        this.surnames = surnames;
        this.name = name;
        this.nifNie = nifNie;
        this.numberAffiliationSocialSecurity = numberAffiliationSocialSecurity;
        this.birthDate = birthDate;
        this.civilState = civilState;
        this.address = address;
        this.studyLevel = studyLevel;
        this.nationality = nationality;
    }


    public Integer getPersonId() {
        return personId;
    }

    public String getSurnames() {
        return surnames;
    }

    public String getName() {
        return name;
    }

    public String getNifNie() {
        return nifNie;
    }

    public String getNumberAffiliationSocialSecurity() {
        return numberAffiliationSocialSecurity;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getCivilState() {
        return civilState;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getStudyLevel() {
        return studyLevel;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return toAlphabeticalName();
    }

    public String toAlphabeticalName(){
            return getSurnames() + ", " + getName();
    }

    public String toNaturalName(){

        return getName() + " " + getSurnames();
    }

    public static Person.PersonBuilder create() {
        return new Person.PersonBuilder();
    }

    public static class PersonBuilder {

        private Integer personId;
        private String surnames;
        private String name;
        private String nifNie;
        private String numberAffiliationSocialSecurity;
        private LocalDate birthDate;
        private String civilState;
        private Address address;
        private Integer studyLevel;
        private String nationality;

        public Person.PersonBuilder withPersonId(Integer personId) {
            this.personId = personId;
            return this;
        }

        public Person.PersonBuilder withSurnames(String surnames) {
            this.surnames = surnames;
            return this;
        }

        public Person.PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Person.PersonBuilder withNifNie(String nifNie) {
            this.nifNie = nifNie;
            return this;
        }

        public Person.PersonBuilder withNumberAffiliationSocialSecurity(String numberAffiliationSocialSecurity) {
            this.numberAffiliationSocialSecurity = numberAffiliationSocialSecurity;
            return this;
        }

        public Person.PersonBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Person.PersonBuilder withCivilState(String civilState) {
            this.civilState = civilState;
            return this;
        }

        public Person.PersonBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Person.PersonBuilder withStudyLevel(Integer studyLevel) {
            this.studyLevel = studyLevel;
            return this;
        }

        public Person.PersonBuilder withNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Person build() {
            return new Person(this.personId, this.surnames, this.name, this.nifNie, this.numberAffiliationSocialSecurity, this.birthDate, this.civilState,
                    this.address, this.studyLevel, this.nationality);
        }
    }
}