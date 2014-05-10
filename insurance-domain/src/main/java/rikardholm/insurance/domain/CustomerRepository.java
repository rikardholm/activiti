package rikardholm.insurance.domain;

import com.google.common.base.Optional;

public interface CustomerRepository extends Repository<Customer> {
    Optional<Customer> findBy(PersonalIdentifier personalIdentifier);
}
