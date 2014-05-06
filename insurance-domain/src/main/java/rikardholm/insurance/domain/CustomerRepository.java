package rikardholm.insurance.domain;

import rikardholm.insurance.common.Optional;

public interface CustomerRepository extends Repository<Customer> {
    Optional<Customer> findBy(PersonalIdentifier personalIdentifier);
}
