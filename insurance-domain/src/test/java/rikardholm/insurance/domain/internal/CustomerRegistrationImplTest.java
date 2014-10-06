package rikardholm.insurance.domain.internal;

import org.junit.Test;
import rikardholm.insurance.domain.customer.*;
import rikardholm.insurance.infrastructure.inmemory.InMemoryCustomerRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isAbsent;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isPresent;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasAddress;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasPersonalIdentifier;

public class CustomerRegistrationImplTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("450806-3402");
    public static final Address ADDRESS = Address.of("Blahagatan");
    private final CustomerRepository customerRepository = new InMemoryCustomerRepository();
    private final CustomerRegistration customerRegistration = new CustomerRegistrationImpl(customerRepository);

    @Test
    public void should_return_a_Customer_with_the_personal_identifier_and_address() throws Exception {
        Customer result = customerRegistration.register(PERSONAL_IDENTIFIER, ADDRESS);

        assertThat(result, hasPersonalIdentifier(equalTo(PERSONAL_IDENTIFIER)));
        assertThat(result, hasAddress(equalTo(ADDRESS)));
    }

    @Test
    public void should_store_a_customer_in_the_CustomerRepository() {
        assertThat(customerRepository.findBy(PERSONAL_IDENTIFIER), isAbsent());

        customerRegistration.register(PERSONAL_IDENTIFIER, ADDRESS);

        assertThat(customerRepository.findBy(PERSONAL_IDENTIFIER), isPresent());
    }
}