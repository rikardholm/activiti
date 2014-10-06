package rikardholm.insurance.infrastructure.h2;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public class h2CustomerRepository implements CustomerRepository {
    private static final Logger log = LoggerFactory.getLogger(h2CustomerRepository.class);
    private final CustomerMapper customerMapper;

    public h2CustomerRepository(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public Optional<? extends Customer> findBy(PersonalIdentifier personalIdentifier) {
        return Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));
    }

    @Override
    public void save(Customer instance) {
        checkCustomerDoesNotExist(instance);
        log.info("Saving customer {}", instance.getPersonalIdentifier());
        customerMapper.insert(instance);
    }

    private void checkCustomerDoesNotExist(Customer instance) {
        PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
        Optional<? extends Customer> existing = Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Personal Identifier already exists: " + personalIdentifier);
        }
    }

    @Override
    public void delete(Customer instance) {
        customerMapper.delete(instance);
    }
}
