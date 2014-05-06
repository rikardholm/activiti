package rikardholm.insurance.service.insurance;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.service.PersonalIdentifier;

public interface CustomerRepository extends Repository<Customer> {
    Optional<Customer> findBy(PersonalIdentifier personalIdentifier);
}
