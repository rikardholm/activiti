package rikardholm.insurance.domain.customer;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.common.Repository;

public interface CustomerRepository extends Repository<Customer> {
    Optional<? extends Customer> findBy(PersonalIdentifier personalIdentifier);
}
