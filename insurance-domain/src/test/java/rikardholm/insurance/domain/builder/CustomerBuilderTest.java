package rikardholm.insurance.domain.builder;

import org.junit.Test;
import rikardholm.insurance.domain.Address;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasPersonalIdentifier;

public class CustomerBuilderTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("450617-3028");

    @Test
    public void withPersonalIdentifier_sets_personal_identifier() throws Exception {
        Customer result = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(PERSONAL_IDENTIFIER)
                .withAddress(Address.of("Testskogsvägen 1L"))
                .build();

        assertThat(result, hasPersonalIdentifier(equalTo(PERSONAL_IDENTIFIER)));
    }

    @Test(expected = NullPointerException.class)
    public void refuses_null_PersonalIdentifier() throws Exception {
        CustomerBuilder.aCustomer().withPersonalIdentifier(null);
    }


    @Test(expected = NullPointerException.class)
    public void refuses_null_Address() throws Exception {
        CustomerBuilder.aCustomer().withPersonalIdentifier(PersonalIdentifier.of("121212-1212")).withAddress(null);
    }
}
