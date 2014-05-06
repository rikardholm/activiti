package rikardholm.insurance.infrastructure.inmemory;

import com.google.common.base.Predicate;
import rikardholm.insurance.common.Optional;
import rikardholm.insurance.common.OptionalConverter;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.tryFind;

public class InMemoryCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public void create(Customer instance) {
        PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
        if (findBy(personalIdentifier).isPresent()) {
            throw new IllegalArgumentException("A customer with Personal Identifier=" + personalIdentifier + " already exists.");
        }

        customers.add(instance);
    }

    @Override
    public Optional<Customer> findBy(final PersonalIdentifier personalIdentifier) {
        return OptionalConverter.convert(tryFind(customers, new Predicate<Customer>() {
            @Override
            public boolean apply(Customer input) {
                return personalIdentifier.equals(input.getPersonalIdentifier());
            }
        }));
    }
}
