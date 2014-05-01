package rikardholm.insurance.service;

import static java.util.Objects.requireNonNull;

public class PersonalIdentifier {
    private String personalIdentifier;

    private PersonalIdentifier(String personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public String getPersonalIdentifier() {
        return personalIdentifier;
    }

    public static PersonalIdentifier of(String personalIdentifier) {
        return new PersonalIdentifier(requireNonNull(personalIdentifier));
    }

    @Override
    public String toString() {
        return personalIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonalIdentifier that = (PersonalIdentifier) o;

        if (!personalIdentifier.equals(that.personalIdentifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalIdentifier.hashCode();
    }
}
