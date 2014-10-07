package rikardholm.insurance.domain.insurance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rikardholm.insurance.common.test.database.InMemoryDatabase;
import rikardholm.insurance.common.test.database.InMemoryDatabaseTestExecutionListener;
import rikardholm.insurance.domain.customer.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.domain.matchers.InsuranceMatchers.hasCustomer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/META-INF/insurance/spring/domain-context.xml",
        InMemoryDatabaseTestExecutionListener.IN_MEMORY_DATASOURCE})
@InMemoryDatabase
public class InsuranceRegistrationTest {

    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("23434"))
            .withAddress(Address.of("edsdfsfd"))
            .build();

    @Autowired
    private InsuranceRegistration insuranceRegistration;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private CustomerRegistration customerRegistration;

    private Insurance insurance;

    @Before
    public void setUp() throws Exception {
        customerRegistration.register(CUSTOMER.getPersonalIdentifier(),CUSTOMER.getAddress());
    }

    @Test
    public void should_return_a_created_Insurance() throws Exception {
        insurance = insuranceRegistration.register(CUSTOMER);

        assertThat(insurance, hasCustomer(equalTo(CUSTOMER)));
    }

    @Test
    public void should_store_Insurance_in_repository() throws Exception {
        insurance = insuranceRegistration.register(CUSTOMER);

        List<? extends Insurance> insurances = insuranceRepository.findBy(CUSTOMER);

        assertThat(insurances, hasSize(1));
        assertThat(insurances, contains(insurance));
    }

    @Test
    public void should_generate_different_InsuranceNumbers() throws Exception {
        Insurance first = insuranceRegistration.register(CUSTOMER);
        Insurance second = insuranceRegistration.register(CUSTOMER);
        Insurance third = insuranceRegistration.register(CUSTOMER);

        assertThat(first.getInsuranceNumber(), not(isOneOf(second.getInsuranceNumber(), third.getInsuranceNumber())));
        assertThat(second.getInsuranceNumber(), is(not(equalTo(third.getInsuranceNumber()))));
    }
}