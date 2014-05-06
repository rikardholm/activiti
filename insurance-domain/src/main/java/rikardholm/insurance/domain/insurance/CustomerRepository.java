package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;

public interface CustomerRepository extends Repository<Customer> {
    Optional<Customer> findBy(PersonalIdentifier personalIdentifier);
}
