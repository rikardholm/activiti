package rikardholm.insurance.domain.customer;

public interface Customer {
    PersonalIdentifier getPersonalIdentifier();
    Address getAddress();
}
