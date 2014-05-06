package rikardholm.insurance.domain.insurance;

import org.junit.Test;
import rikardholm.insurance.domain.PersonalIdentifier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static rikardholm.insurance.domain.insurance.Builders.aCustomer;
import static rikardholm.insurance.domain.matchers.CustomerMatchers.hasPersonalIdentifier;

public class CustomerBuilderTest {

    public static final PersonalIdentifier PERSONAL_IDENTIFIER = PersonalIdentifier.of("450617-3028");

    @Test
    public void withPersonalIdentifier_sets_personal_identifier() throws Exception {
        Customer result = aCustomer()
                .withPersonalIdentifier(PERSONAL_IDENTIFIER)
                .build();

        assertThat(result, hasPersonalIdentifier(equalTo(PERSONAL_IDENTIFIER)));
    }

    @Test(expected = NullPointerException.class)
    public void refuses_nulls() throws Exception {
        aCustomer().withPersonalIdentifier(null).build();
    }
}
