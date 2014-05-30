package rikardholm.insurance.domain.customer;

public interface CustomerRegistration {
    Customer register(PersonalIdentifier personalIdentifier, Address address);
}
