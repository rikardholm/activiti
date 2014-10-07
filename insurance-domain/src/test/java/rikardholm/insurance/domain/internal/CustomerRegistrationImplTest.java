package rikardholm.insurance.domain.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.*;
import rikardholm.insurance.infrastructure.h2.H2CustomerRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isAbsent;
import static rikardholm.insurance.common.test.hamcrest.OptionalMatchers.isPresent;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasAddress;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasPersonalIdentifier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/META-INF/insurance/spring/domain-context.xml",
        InMemoryDatabaseTestExecutionListener.IN_MEMORY_DATASOURCE})
@InMemoryDatabase
public class CustomerRegistrationImplTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("450806-3402");
    public static final Address ADDRESS = Address.of("Blahagatan");

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerRepository customerRepository;

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