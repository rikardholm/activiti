package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;

import static java.util.Objects.requireNonNull;

public class CustomerImpl implements Customer {
    private final PersonalIdentifier personalIdentifier;

    public CustomerImpl(PersonalIdentifier personalIdentifier) {
        this.personalIdentifier = requireNonNull(personalIdentifier);
    }

    @Override
    public PersonalIdentifier getPersonalIdentifier() {
        return personalIdentifier;
    }

    @Override
    public String toString() {
        return "Customer " + personalIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerImpl customer = (CustomerImpl) o;

        if (!personalIdentifier.equals(customer.personalIdentifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalIdentifier.hashCode();
    }
}