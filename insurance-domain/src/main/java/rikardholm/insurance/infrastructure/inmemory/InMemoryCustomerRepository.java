package rikardholm.insurance.infrastructure.inmemory;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.tryFind;

public class InMemoryCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public void save(Customer instance) {
        PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
        if (findBy(personalIdentifier).isPresent()) {
            throw new IllegalArgumentException("A customer with Personal Identifier=" + personalIdentifier + " already exists.");
        }

        customers.add(instance);
    }

    @Override
    public void delete(Customer instance) {
        Optional<? extends Customer> existing = findBy(instance.getPersonalIdentifier());

        if (existing.isPresent()) {
            customers.remove(existing.get());
        }
    }

    @Override
    public Optional<? extends Customer> findBy(final PersonalIdentifier personalIdentifier) {
        return tryFind(customers, new Predicate<Customer>() {
            @Override
            public boolean apply(Customer input) {
                return personalIdentifier.equals(input.getPersonalIdentifier());
            }
        });
    }
}
