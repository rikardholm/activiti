package rikardholm.insurance.service.insurance;

import org.junit.Test;
import rikardholm.insurance.common.Optional;
import rikardholm.insurance.service.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static rikardholm.insurance.common.test.OptionalMatchers.hasValue;
import static rikardholm.insurance.common.test.OptionalMatchers.isAbsent;
import static rikardholm.insurance.service.insurance.Builders.aCustomer;

public abstract class AbstractCustomerRepositoryTest extends AbstractContractTest<CustomerRepository> {

    public static final String PERSONAL_IDENTIFIER_STRING = "560830-4028";
    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of(PERSONAL_IDENTIFIER_STRING);
    public static final Customer CUSTOMER = aCustomer()
            .withPersonalIdentifier(PERSONAL_IDENTIFIER)
            .build();
    private CustomerRepository customerRepository = getInstance();

    @Test
    public void findByPersonalIdentifier_should_return_absent_when_not_found() throws Exception {
        Optional<Customer> result = customerRepository.findBy(PERSONAL_IDENTIFIER);

        assertThat(result, isAbsent());
    }

    @Test
    public void findByPersonalIdentifier_should_find_existing_Customer() throws Exception {
        customerRepository.create(CUSTOMER);

        Optional<Customer> result = customerRepository.findBy(PERSONAL_IDENTIFIER);

        assertThat(result, hasValue(CUSTOMER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_should_refuse_new_Customer_with_existing_PersonalIdentifier() {
        customerRepository.create(CUSTOMER);

        PersonalIdentifier personalIdentifier = PersonalIdentifier.of(PERSONAL_IDENTIFIER_STRING);
        Customer customer = aCustomer()
                .withPersonalIdentifier(personalIdentifier)
                .build();

        customerRepository.create(customer);
    }
}
