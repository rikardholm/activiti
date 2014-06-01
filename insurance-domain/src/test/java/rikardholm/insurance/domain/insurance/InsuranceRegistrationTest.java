package rikardholm.insurance.domain.insurance;

import org.junit.Test;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerBuilder;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.internal.InsuranceNumberGenerator;
import rikardholm.insurance.domain.internal.InsuranceRegistrationImpl;
import rikardholm.insurance.infrastructure.inmemory.InMemoryInsuranceRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rikardholm.insurance.domain.matchers.InsuranceMatchers.hasCustomer;

public class InsuranceRegistrationTest {

    public static final Customer CUSTOMER = CustomerBuilder.aCustomer()
            .withPersonalIdentifier(PersonalIdentifier.of("23434"))
            .withAddress(Address.of("edsdfsfd"))
            .build();
    private final InsuranceRepository insuranceRepository = new InMemoryInsuranceRepository();
    private final InsuranceRegistration insuranceRegistration = new InsuranceRegistrationImpl(insuranceRepository, new InsuranceNumberGenerator());
    private Insurance insurance;

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