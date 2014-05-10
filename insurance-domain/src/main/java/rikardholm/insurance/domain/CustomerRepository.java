package rikardholm.insurance.domain;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.common.Repository;

public interface CustomerRepository extends Repository<Customer> {
    Optional<Customer> findBy(PersonalIdentifier personalIdentifier);
}
